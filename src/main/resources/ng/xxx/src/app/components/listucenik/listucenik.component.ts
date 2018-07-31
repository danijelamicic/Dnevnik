import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Ucenik } from '../../entities/ucenik';
import { UcenikService } from '../../services/ucenik.service';
import { Observable, Subject } from 'rxjs';
import { AuthService } from '../../services/auth-service.service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-listucenik',
  templateUrl: './listucenik.component.html',
  styleUrls: ['./listucenik.component.css']
})
export class ListucenikComponent implements OnInit, AfterViewInit {

  ucenici$: Observable<Ucenik[]>;
  private searchTerm = new Subject<string>();

  constructor(private router: Router, private ucenikService: UcenikService, private authService: AuthService) { }

  search(term: string) {
    this.searchTerm.next(term);
  }

  ngOnInit() {
    this.ucenici$ = this.searchTerm.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap((term: string) => this.ucenikService.searchUcenici(term))
    );
  }

  ngAfterViewInit(): void {
    this.search('');
  }
  pronadjiSveUcenike(): void {
    this.ucenikService.pronadjiSveUcenike()
        .subscribe(a => this.ucenici$ = this.searchTerm.pipe(
          debounceTime(500),
          distinctUntilChanged(),
          switchMap((term: string) => this.ucenikService.searchUcenici(term))
        ));
  }
  delete(id: number) {
    this.ucenikService.obrisiUcenikaPoId(id).subscribe(
    _ => this.pronadjiSveUcenike()),
    this.router.navigate(['/ucenici/']);
  }


}
