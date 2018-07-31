import { Component, OnInit, AfterViewInit } from '@angular/core';
import { AuthService } from '../../services/auth-service.service';
import { NastavnikService } from '../../services/nastavnik.service';
import { Observable, Subject } from 'rxjs';
import { Nastavnik } from '../../entities/nastavnik';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-listnastavnik',
  templateUrl: './listnastavnik.component.html',
  styleUrls: ['./listnastavnik.component.css']
})
export class ListnastavnikComponent implements OnInit, AfterViewInit {

  nastavnici$: Observable<Nastavnik[]>;
  private searchTerm = new Subject<string>();

  constructor(private router: Router, private nastavnikService: NastavnikService, private authService: AuthService) { }

  search(term: string) {
    this.searchTerm.next(term);
  }

  ngOnInit() {
    this.nastavnici$ = this.searchTerm.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap((term: string) => this.nastavnikService.searchNastavnici(term))
    );
  }

  ngAfterViewInit(): void {
    this.search('');
  }
  pronadjiSveNastavnike(): void {
    this.nastavnikService.pronadjiSveNastavnike()
        .subscribe(a => this.nastavnici$ = this.searchTerm.pipe(
          debounceTime(500),
          distinctUntilChanged(),
          switchMap((term: string) => this.nastavnikService.searchNastavnici(term))
        ));
  }
  delete(id: number) {
    this.nastavnikService.obrisiNastavnikaPoId(id).subscribe(
    _ => this.pronadjiSveNastavnike()),
    this.router.navigate(['/nastavnici']);
  }


}

