import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager, JhiParseLinks } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IService } from 'app/shared/model/service.model';

import { ITEMS_PER_PAGE } from 'app/shared/constants/pagination.constants';
import { ServiceService } from './service.service';
import { ServiceDeleteDialogComponent } from './service-delete-dialog.component';

@Component({
  selector: 'jhi-service',
  templateUrl: './service.component.html',
})
export class ServiceComponent implements OnInit, OnDestroy {
  services: IService[];
  eventSubscriber?: Subscription;
  itemsPerPage: number;
  links: any;
  page: number;
  predicate: string;
  ascending: boolean;

  constructor(
    protected serviceService: ServiceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal,
    protected parseLinks: JhiParseLinks
  ) {
    this.services = [];
    this.itemsPerPage = ITEMS_PER_PAGE;
    this.page = 0;
    this.links = {
      last: 0,
    };
    this.predicate = 'id';
    this.ascending = true;
  }

  loadAll(): void {
    this.serviceService
      .query({
        page: this.page,
        size: this.itemsPerPage,
        sort: this.sort(),
      })
      .subscribe((res: HttpResponse<IService[]>) => this.paginateServices(res.body, res.headers));
  }

  reset(): void {
    this.page = 0;
    this.services = [];
    this.loadAll();
  }

  loadPage(page: number): void {
    this.page = page;
    this.loadAll();
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInServices();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IService): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInServices(): void {
    this.eventSubscriber = this.eventManager.subscribe('serviceListModification', () => this.reset());
  }

  delete(service: IService): void {
    const modalRef = this.modalService.open(ServiceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.service = service;
  }

  sort(): string[] {
    const result = [this.predicate + ',' + (this.ascending ? 'asc' : 'desc')];
    if (this.predicate !== 'id') {
      result.push('id');
    }
    return result;
  }

  protected paginateServices(data: IService[] | null, headers: HttpHeaders): void {
    const headersLink = headers.get('link');
    this.links = this.parseLinks.parse(headersLink ? headersLink : '');
    if (data) {
      for (let i = 0; i < data.length; i++) {
        this.services.push(data[i]);
      }
    }
  }
}
