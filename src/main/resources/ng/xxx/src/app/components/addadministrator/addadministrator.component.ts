import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { Administrator } from '../../entities/administrator';
import { AdministratorService } from '../../services/administrator.service';
import { EKorisnikUloga } from '../../enum/ekorisnik-uloga.enum';

@Component({
  selector: 'app-addadministrator',
  templateUrl: './addadministrator.component.html',
  styleUrls: ['./addadministrator.component.css']
})
export class AddadministratorComponent implements OnInit {

  administrator: Administrator;

  constructor(private router: Router,
    private location: Location,
    private administratorService: AdministratorService, ) {
    this.administrator = new Administrator();
  }

  ngOnInit() {

  }

  dodajNovogAdministratora(ime: string, prezime: string, korisnickoIme: string, email: string, lozinka: string) {
    this.administrator.ime = ime;
    this.administrator.prezime = prezime;
    this.administrator.korisnickoIme = korisnickoIme;
    this.administrator.email = email;
    this.administrator.lozinka = lozinka;


    this.administratorService.dodajNovogAdministratora(this.administrator)
      .subscribe((administrator: Administrator) => {
        alert('Administrator ' + administrator.ime + ' ' + administrator.prezime + ' je uspešno dodat!');
        this.router.navigate(['/admini']);
      });
  }

  goBack() {
    this.location.back();
  }

}
