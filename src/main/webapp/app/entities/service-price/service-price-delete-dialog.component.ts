import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IServicePrice } from 'app/shared/model/service-price.model';
import { ServicePriceService } from './service-price.service';

@Component({
  templateUrl: './service-price-delete-dialog.component.html',
})
export class ServicePriceDeleteDialogComponent {
  servicePrice?: IServicePrice;

  constructor(
    protected servicePriceService: ServicePriceService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.servicePriceService.delete(id).subscribe(() => {
      this.eventManager.broadcast('servicePriceListModification');
      this.activeModal.close();
    });
  }
}
