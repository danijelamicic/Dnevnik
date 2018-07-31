import { Component, OnInit, AfterViewInit } from '@angular/core';
import { Router } from '@angular/router';
import { RoditeljService } from '../../services/roditelj.service';
import { AuthService } from '../../services/auth-service.service';
import { UcenikService } from '../../services/ucenik.service';
import { Ucenik } from '../../entities/ucenik';
import { Subject } from 'rxjs';

@Component({
  selector: 'app-pogledroditelj',
  templateUrl: './pogledroditelj.component.html',
  styleUrls: ['./pogledroditelj.component.css']
})
export class PogledroditeljComponent implements OnInit, AfterViewInit {

  ucenici: Ucenik[];
  private searchTerm = new Subject<string>();

  constructor(
    private router: Router,
    private roditeljService: RoditeljService,
    private authService: AuthService,
    private ucenikService: UcenikService
  ) {}
  ngAfterViewInit(): void {
    this.search('');
  }
  search(term: string) {
    this.searchTerm.next(term);
  }

  ngOnInit() {
    this.pronadjiDecuRoditelja();
  }
  pronadjiDecuRoditelja() {
    this.roditeljService.pronadjiDecuRoditelja(localStorage.getItem('id'))
    .subscribe(a => this.ucenici = a);

 }

}
