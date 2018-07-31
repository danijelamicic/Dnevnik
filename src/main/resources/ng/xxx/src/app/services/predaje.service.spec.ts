import { TestBed, inject } from '@angular/core/testing';

import { PredajeService } from './predaje.service';

describe('PredajeService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PredajeService]
    });
  });

  it('should be created', inject([PredajeService], (service: PredajeService) => {
    expect(service).toBeTruthy();
  }));
});
