import { TestBed, inject } from '@angular/core/testing';

import { PredmetURazreduService } from './predmet-urazredu.service';

describe('PredmetURazreduService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [PredmetURazreduService]
    });
  });

  it('should be created', inject([PredmetURazreduService], (service: PredmetURazreduService) => {
    expect(service).toBeTruthy();
  }));
});
