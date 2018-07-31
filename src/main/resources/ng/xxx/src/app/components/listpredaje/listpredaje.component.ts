import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Predaje } from '../../entities/predaje';
import { Observable, Subject } from 'rxjs';
import { PredajeService } from '../../services/predaje.service';
import { AuthService } from '../../services/auth-service.service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-listpredaje',
  templateUrl: './listpredaje.component.html',
  styleUrls: ['./listpredaje.component.css']
})
export class ListpredajeComponent implements OnInit, AfterViewInit {
  predaju$: Observable<Predaje[]>;
  private searchTerm = new Subject<string>();

  constructor(private predajeService: PredajeService, private authService: AuthService) { }

  search(term: string) {
    this.searchTerm.next(term);
  }

  ngOnInit() {
    this.predaju$ = this.searchTerm.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap((term: string) => this.predajeService.searchPredaje(term))
    );
  }

  ngAfterViewInit(): void {
    this.search('');
  }

}

