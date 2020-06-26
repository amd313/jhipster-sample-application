import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICategory, Category } from 'app/shared/model/category.model';
import { CategoryService } from './category.service';
import { IService } from 'app/shared/model/service.model';
import { ServiceService } from 'app/entities/service/service.service';

@Component({
  selector: 'jhi-category-update',
  templateUrl: './category-update.component.html',
})
export class CategoryUpdateComponent implements OnInit {
  isSaving = false;
  services: IService[] = [];

  editForm = this.fb.group({
    id: [],
    name: [],
    description: [],
    services: [],
  });

  constructor(
    protected categoryService: CategoryService,
    protected serviceService: ServiceService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ category }) => {
      this.updateForm(category);

      this.serviceService.query().subscribe((res: HttpResponse<IService[]>) => (this.services = res.body || []));
    });
  }

  updateForm(category: ICategory): void {
    this.editForm.patchValue({
      id: category.id,
      name: category.name,
      description: category.description,
      services: category.services,
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const category = this.createFromForm();
    if (category.id !== undefined) {
      this.subscribeToSaveResponse(this.categoryService.update(category));
    } else {
      this.subscribeToSaveResponse(this.categoryService.create(category));
    }
  }

  private createFromForm(): ICategory {
    return {
      ...new Category(),
      id: this.editForm.get(['id'])!.value,
      name: this.editForm.get(['name'])!.value,
      description: this.editForm.get(['description'])!.value,
      services: this.editForm.get(['services'])!.value,
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICategory>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IService): any {
    return item.id;
  }

  getSelected(selectedVals: IService[], option: IService): IService {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
