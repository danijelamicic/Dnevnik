import { Component, OnInit, AfterViewInit } from '@angular/core';
import { AuthService } from '../../services/auth-service.service';
import { UcenikService } from '../../services/ucenik.service';
import { Ocena } from '../../entities/ocena';
import { OcenaService } from '../../services/ocena.service';
import { Observable, Subject } from 'rxjs';
import { Router } from '@angular/router';
import { OcenaPredmetDTO } from '../../entities/ocena-predmet-dto';

@Component({
  selector: 'app-pogleducenik',
  templateUrl: './pogleducenik.component.html',
  styleUrls: ['./pogleducenik.component.css']
})
export class PogleducenikComponent implements OnInit, AfterViewInit {

  ocPr: OcenaPredmetDTO[];
  private searchTerm = new Subject<string>();

  constructor(
    private router: Router,
    private ucenikService: UcenikService,
    private authService: AuthService,
    private ocenaService: OcenaService
  ) {}

  ngAfterViewInit(): void {
    this.search('');
  }

  search(term: string) {
    this.searchTerm.next(term);
  }

   ngOnInit() {
    this.pronadjiOcenePoPredmetuPoIdUcenika();
  }
  pronadjiOcenePoPredmetuPoIdUcenika() {
     this.ucenikService.pronadjiOcenePoPredmetuPoIdUcenika(localStorage.getItem('id'))
     .subscribe(a => this.ocPr = a);

  }
}
