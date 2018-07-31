import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { debounceTime, distinctUntilChanged, switchMap, tap } from 'rxjs/operators';

import { AdministratorService } from '../../services/administrator.service';
import { Administrator } from '../../entities/administrator';
import { AuthService } from '../../services/auth-service.service';
import { Router } from '@angular/router';


@Component({
  selector: 'app-listadministrator',
  templateUrl: './listadministrator.component.html',
  styleUrls: ['./listadministrator.component.css']
})
export class ListadministratorComponent implements OnInit, AfterViewInit {

  administratori$: Observable<Administrator[]>;
  private searchTerm = new Subject<string>();

  constructor(private router: Router, private administratorService: AdministratorService, private authService: AuthService) { }

  search(term: string) {
    this.searchTerm.next(term);
  }

  ngOnInit() {
    this.administratori$ = this.searchTerm.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap((term: string) => this.administratorService.searchAdministratori(term))
    );
  }

  ngAfterViewInit(): void {
    this.search('');
  }
  pronadjiSveAdmine(): void {
    this.administratorService.pronadjiSveAdmine()
        .subscribe(a => this.administratori$ = this.searchTerm.pipe(
          debounceTime(500),
          distinctUntilChanged(),
          switchMap((term: string) => this.administratorService.searchAdministratori(term))
        ));
  }
  delete(id: number) {
    this.administratorService.obrisiAdministratoraPoId(id).subscribe(
    _ => this.pronadjiSveAdmine()),
    this.router.navigate(['/admini/']);
  }

}
