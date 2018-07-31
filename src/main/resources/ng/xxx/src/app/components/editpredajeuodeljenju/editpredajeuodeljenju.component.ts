import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { PredajeUOdeljenju } from '../../entities/predaje-uodeljenju';
import { ActivatedRoute, Router } from '@angular/router';
import { PredajeUOdeljenjuService } from '../../services/predaje-uodeljenju.service';


@Component({
  selector: 'app-editpredajeuodeljenju',
  templateUrl: './editpredajeuodeljenju.component.html',
  styleUrls: ['./editpredajeuodeljenju.component.css']
})
export class EditpredajeuodeljenjuComponent implements OnInit {

  predajeuodeljenju: PredajeUOdeljenju;


  constructor(private route: ActivatedRoute,
              private router: Router,
              private location: Location,
              private predajeuodeljenjuService: PredajeUOdeljenjuService) {
    this.predajeuodeljenju = new PredajeUOdeljenju();
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.predajeuodeljenjuService.pronadjiPredajeUOdeljenjuPoId(id).subscribe(a => this.predajeuodeljenju = a);
  }

  editPredajeUOdeljenju() {
    this.predajeuodeljenjuService.izmeniPredajeUOdeljenju(this.predajeuodeljenju)
    .subscribe((predajeuodeljenju: PredajeUOdeljenju) =>  {
      alert('PredajeUOdeljenju ' + predajeuodeljenju.idPredajeUOdeljenju + ' je uspešno izmenjen!');
      this.router.navigate(['/predajeuodeljenju']);
    });
  }

  goBack() {
    this.location.back();
  }

}
