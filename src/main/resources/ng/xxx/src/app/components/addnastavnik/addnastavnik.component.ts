import { Component, OnInit } from '@angular/core';
import { Nastavnik } from '../../entities/nastavnik';
import { Router } from '@angular/router';
import { NastavnikService } from '../../services/nastavnik.service';
import { Location } from '@angular/common';

@Component({
  selector: 'app-addnastavnik',
  templateUrl: './addnastavnik.component.html',
  styleUrls: ['./addnastavnik.component.css']
})
export class AddnastavnikComponent implements OnInit {

  nastavnik: Nastavnik;

  constructor(private router: Router,
    private location: Location,
    private nastavnikService: NastavnikService, ) {
    this.nastavnik = new Nastavnik();
  }

  ngOnInit() {

  }

  dodajNovogNastavnika(ime: string, prezime: string, korisnickoIme: string, email: string, lozinka: string, zvanje: string) {
    this.nastavnik.ime = ime;
    this.nastavnik.prezime = prezime;
    this.nastavnik.korisnickoIme = korisnickoIme;
    this.nastavnik.email = email;
    this.nastavnik.lozinka = lozinka;
    this.nastavnik.zvanje = zvanje;


    this.nastavnikService.dodajNovogNastavnika(this.nastavnik)
      .subscribe((nastavnik: Nastavnik) => {
        alert('Nastavnik ' + nastavnik.ime + ' ' + nastavnik.prezime + ' je uspešno dodat!');
        this.router.navigate(['/nastavnici']);
      });
  }

  goBack() {
    this.location.back();
  }

}
