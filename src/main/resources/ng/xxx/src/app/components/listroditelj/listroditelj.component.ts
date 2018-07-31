import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Roditelj } from '../../entities/roditelj';
import { Observable, Subject } from 'rxjs';
import { RoditeljService } from '../../services/roditelj.service';
import { AuthService } from '../../services/auth-service.service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { Router } from '@angular/router';

@Component({
  selector: 'app-listroditelj',
  templateUrl: './listroditelj.component.html',
  styleUrls: ['./listroditelj.component.css']
})
export class ListroditeljComponent implements OnInit, AfterViewInit {

  roditelji$: Observable<Roditelj[]>;
  private searchTerm = new Subject<string>();

  constructor(private router: Router, private roditeljService: RoditeljService, private authService: AuthService) { }

  search(term: string) {
    this.searchTerm.next(term);
  }

  ngOnInit() {
    this.roditelji$ = this.searchTerm.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap((term: string) => this.roditeljService.searchRoditelji(term))
    );
  }

  ngAfterViewInit(): void {
    this.search('');
  }
  pronadjiSveRoditelje(): void {
    this.roditeljService.pronadjiSveRoditelje()
        .subscribe(a => this.roditelji$ = this.searchTerm.pipe(
          debounceTime(500),
          distinctUntilChanged(),
          switchMap((term: string) => this.roditeljService.searchRoditelji(term))
        ));
  }
  delete(id: number) {
    this.roditeljService.obrisiRoditeljaPoId(id).subscribe(
    _ => this.pronadjiSveRoditelje()),
    this.router.navigate(['/roditelji']);
  }


}
