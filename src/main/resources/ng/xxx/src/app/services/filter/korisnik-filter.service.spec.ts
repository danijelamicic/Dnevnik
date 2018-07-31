import { TestBed, inject } from '@angular/core/testing';

import { KorisnikFilterService } from './korisnik-filter.service';

describe('KorisnikFilterService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [KorisnikFilterService]
    });
  });

  it('should be created', inject([KorisnikFilterService], (service: KorisnikFilterService) => {
    expect(service).toBeTruthy();
  }));
});
