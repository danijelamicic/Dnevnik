import { Component, OnInit } from '@angular/core';
import { Roditelj } from '../../entities/roditelj';
import { Location } from '@angular/common';
import { RoditeljService } from '../../services/roditelj.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-addroditelj',
  templateUrl: './addroditelj.component.html',
  styleUrls: ['./addroditelj.component.css']
})
export class AddroditeljComponent implements OnInit {

  roditelj: Roditelj;

  constructor(private router: Router,
    private location: Location,
    private roditeljService: RoditeljService, ) {
    this.roditelj = new Roditelj();
  }

  ngOnInit() {

  }

  dodajNovogRoditelja(ime: string, prezime: string, korisnickoIme: string, email: string, lozinka: string) {
    this.roditelj.ime = ime;
    this.roditelj.prezime = prezime;
    this.roditelj.korisnickoIme = korisnickoIme;
    this.roditelj.email = email;
    this.roditelj.lozinka = lozinka;


    this.roditeljService.dodajNovogRoditelja(this.roditelj)
      .subscribe((roditelj: Roditelj) => {
        alert('Roditelj ' + roditelj.ime + ' ' + roditelj.prezime + ' je uspešno dodat!');
        this.router.navigate(['/roditelji']);
      });
  }

  goBack() {
    this.location.back();
  }

}

