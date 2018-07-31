import { Component, OnInit } from '@angular/core';
import { PredajeUOdeljenjuService } from '../../services/predaje-uodeljenju.service';
import { PredajeUOdeljenju } from '../../entities/predaje-uodeljenju';
import { Router } from '@angular/router';
import { Location } from '@angular/common';
import { Predaje } from '../../entities/predaje';
import { Odeljenje } from '../../entities/odeljenje';

@Component({
  selector: 'app-addpredajeuodeljenju',
  templateUrl: './addpredajeuodeljenju.component.html',
  styleUrls: ['./addpredajeuodeljenju.component.css']
})
export class AddpredajeuodeljenjuComponent implements OnInit {

  predajeuodeljenju: PredajeUOdeljenju;

  constructor(private router: Router,
    private location: Location,
    private predajeuodeljenjuService: PredajeUOdeljenjuService, ) {
    this.predajeuodeljenju = new PredajeUOdeljenju();
  }

  ngOnInit() {

  }

  dodajNoviPredajeUOdeljenju(idPredajeUOdeljenju: number, predaje: Predaje, odeljenje: Odeljenje) {
    this.predajeuodeljenju.idPredajeUOdeljenju = idPredajeUOdeljenju;
    this.predajeuodeljenju.predaje = predaje;
    this.predajeuodeljenju.odeljenje = odeljenje;



    this.predajeuodeljenjuService.dodajNoviPredajeUOdeljenju(this.predajeuodeljenju)
      .subscribe((predajeuodeljenju: PredajeUOdeljenju) => {
        alert('PredajeUOdeljenju ' + predajeuodeljenju.idPredajeUOdeljenju + ' je uspešno dodat!');
        this.router.navigate(['/predajeuodeljenju']);
      });
  }

  goBack() {
    this.location.back();
  }

}
