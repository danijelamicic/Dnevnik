import { Component, OnInit } from '@angular/core';
import { Ucenik } from '../../entities/ucenik';
import { Router } from '@angular/router';
import { UcenikService } from '../../services/ucenik.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-adducenik',
  templateUrl: './adducenik.component.html',
  styleUrls: ['./adducenik.component.css']
})
export class AdducenikComponent implements OnInit {

  ucenik: Ucenik;

  constructor(private router: Router,
    private location: Location,
    private ucenikService: UcenikService, ) {
    this.ucenik = new Ucenik();
  }

  ngOnInit() {

  }

  dodajNovogUcenika(ime: string, prezime: string, korisnickoIme: string, email: string, lozinka: string) {
    this.ucenik.ime = ime;
    this.ucenik.prezime = prezime;
    this.ucenik.korisnickoIme = korisnickoIme;
    this.ucenik.email = email;
    this.ucenik.lozinka = lozinka;


    this.ucenikService.dodajNovogUcenika(this.ucenik)
      .subscribe((ucenik: Ucenik) => {
        alert('Ucenik ' + ucenik.ime + ' ' + ucenik.prezime + ' je uspešno dodat!');
        this.router.navigate(['/ucenici']);
      });
  }

  goBack() {
    this.location.back();
  }

}

