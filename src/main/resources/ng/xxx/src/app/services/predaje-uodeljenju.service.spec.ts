import { TestBed, inject } from '@angular/core/testing';

import { PredajeUOdeljenjuService } from './predaje-uodeljenju.service';

describe('PredajeUOdeljenjuService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PredajeUOdeljenjuService]
    });
  });

  it('should be created', inject([PredajeUOdeljenjuService], (service: PredajeUOdeljenjuService) => {
    expect(service).toBeTruthy();
  }));
});
