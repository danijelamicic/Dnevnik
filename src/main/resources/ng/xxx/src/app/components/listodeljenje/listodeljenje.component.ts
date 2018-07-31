import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Odeljenje } from '../../entities/odeljenje';
import { Subject, Observable } from 'rxjs';
import { OdeljenjeService } from '../../services/odeljenje.service';
import { AuthService } from '../../services/auth-service.service';
import { debounceTime, distinctUntilChanged, switchMap } from 'rxjs/operators';

@Component({
  selector: 'app-listodeljenje',
  templateUrl: './listodeljenje.component.html',
  styleUrls: ['./listodeljenje.component.css']
})
export class ListodeljenjeComponent implements OnInit, AfterViewInit {

  odeljenja$: Observable<Odeljenje[]>;
  private searchTerm = new Subject<string>();

  constructor(private odeljenjeService: OdeljenjeService, private authService: AuthService) { }

  search(term: string) {
    this.searchTerm.next(term);
  }

  ngOnInit() {
    this.odeljenja$ = this.searchTerm.pipe(
      debounceTime(500),
      distinctUntilChanged(),
      switchMap((term: string) => this.odeljenjeService.searchOdeljenje(term))
    );
  }

  ngAfterViewInit(): void {
    this.search('');
  }

}

