import { Component, OnInit } from '@angular/core';
import { Odeljenje } from '../../entities/odeljenje';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { OdeljenjeService } from '../../services/odeljenje.service';
import { Razred } from '../../entities/razred';

@Component({
  selector: 'app-addodeljenje',
  templateUrl: './addodeljenje.component.html',
  styleUrls: ['./addodeljenje.component.css']
})
export class AddodeljenjeComponent implements OnInit {

  odeljenje: Odeljenje;

  constructor(private router: Router,
    private location: Location,
    private odeljenjeService: OdeljenjeService, ) {
    this.odeljenje = new Odeljenje();
  }

  ngOnInit() {

  }

  dodajNovoOdeljenje(marko: number, vrednost: Number ) {
    this.odeljenje.razred.idRazred = marko;
    this.odeljenje.vrednost = vrednost;



    this.odeljenjeService.dodajNovoOdeljenje(this.odeljenje)
      .subscribe((odeljenje: Odeljenje) => {
        alert('Odeljenje ' + odeljenje.razred + ' - ' + odeljenje.vrednost + ' je uspešno dodato!');
        this.router.navigate(['/odeljenja']);
      });
  }

  goBack() {
    this.location.back();
  }

}

