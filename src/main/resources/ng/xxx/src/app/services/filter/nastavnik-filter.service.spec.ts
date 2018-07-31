import { TestBed, inject } from '@angular/core/testing';

import { NastavnikFilterService } from './nastavnik-filter.service';

describe('NastavnikFilterService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [NastavnikFilterService]
    });
  });

  it('should be created', inject([NastavnikFilterService], (service: NastavnikFilterService) => {
    expect(service).toBeTruthy();
  }));
});
