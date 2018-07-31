import { TestBed, inject } from '@angular/core/testing';

import { RazredService } from './razred.service';

describe('RazredService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [RazredService]
    });
  });

  it('should be created', inject([RazredService], (service: RazredService) => {
    expect(service).toBeTruthy();
  }));
});
