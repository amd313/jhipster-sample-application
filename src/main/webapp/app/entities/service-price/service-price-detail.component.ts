import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IServicePrice } from 'app/shared/model/service-price.model';

@Component({
  selector: 'jhi-service-price-detail',
  templateUrl: './service-price-detail.component.html',
})
export class ServicePriceDetailComponent implements OnInit {
  servicePrice: IServicePrice | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ servicePrice }) => (this.servicePrice = servicePrice));
  }

  previousState(): void {
    window.history.back();
  }
}
