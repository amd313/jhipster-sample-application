import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Subscription } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';
import { NgbModal } from '@ng-bootstrap/ng-bootstrap';

import { IServicePrice } from 'app/shared/model/service-price.model';
import { ServicePriceService } from './service-price.service';
import { ServicePriceDeleteDialogComponent } from './service-price-delete-dialog.component';

@Component({
  selector: 'jhi-service-price',
  templateUrl: './service-price.component.html',
})
export class ServicePriceComponent implements OnInit, OnDestroy {
  servicePrices?: IServicePrice[];
  eventSubscriber?: Subscription;

  constructor(
    protected servicePriceService: ServicePriceService,
    protected eventManager: JhiEventManager,
    protected modalService: NgbModal
  ) {}

  loadAll(): void {
    this.servicePriceService.query().subscribe((res: HttpResponse<IServicePrice[]>) => (this.servicePrices = res.body || []));
  }

  ngOnInit(): void {
    this.loadAll();
    this.registerChangeInServicePrices();
  }

  ngOnDestroy(): void {
    if (this.eventSubscriber) {
      this.eventManager.destroy(this.eventSubscriber);
    }
  }

  trackId(index: number, item: IServicePrice): number {
    // eslint-disable-next-line @typescript-eslint/no-unnecessary-type-assertion
    return item.id!;
  }

  registerChangeInServicePrices(): void {
    this.eventSubscriber = this.eventManager.subscribe('servicePriceListModification', () => this.loadAll());
  }

  delete(servicePrice: IServicePrice): void {
    const modalRef = this.modalService.open(ServicePriceDeleteDialogComponent, { size: 'lg', backdrop: 'static' });
    modalRef.componentInstance.servicePrice = servicePrice;
  }
}
