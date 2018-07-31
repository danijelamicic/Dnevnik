import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Observable, Subject } from 'rxjs';
import { AuthService } from '../../services/auth-service.service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';
import { PredajeUOdeljenju } from '../../entities/predaje-uodeljenju';
import { PredajeUOdeljenjuService } from '../../services/predaje-uodeljenju.service';

@Component({
  selector: 'app-listpredajeuodeljenju',
  templateUrl: './listpredajeuodeljenju.component.html',
  styleUrls: ['./listpredajeuodeljenju.component.css']
})
export class ListpredajeuodeljenjuComponent implements OnInit, AfterViewInit {

  predajeuodeljenjima$: Observable<PredajeUOdeljenju[]>;
  private searchTerm = new Subject<string>();

  constructor(private predajeuodeljenjuService: PredajeUOdeljenjuService, private authService: AuthService) { }

  search(term: string) {
    this.searchTerm.next(term);
  }

  ngOnInit() {
    this.predajeuodeljenjima$ = this.searchTerm.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap((term: string) => this.predajeuodeljenjuService.searchPredajeUOdeljenju(term))
    );
  }

  ngAfterViewInit(): void {
    this.search('');
  }

}

