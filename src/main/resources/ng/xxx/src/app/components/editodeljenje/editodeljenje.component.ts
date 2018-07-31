import { Component, OnInit } from '@angular/core';
import { Location } from '@angular/common';
import { Odeljenje } from '../../entities/odeljenje';
import { OdeljenjeService } from '../../services/odeljenje.service';
import { ActivatedRoute, Router } from '@angular/router';

@Component({
  selector: 'app-editodeljenje',
  templateUrl: './editodeljenje.component.html',
  styleUrls: ['./editodeljenje.component.css']
})
export class EditodeljenjeComponent implements OnInit {

  odeljenje: Odeljenje;


  constructor(private route: ActivatedRoute,
              private router: Router,
              private location: Location,
              private odeljenjeService: OdeljenjeService) {
    this.odeljenje = new Odeljenje();
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.odeljenjeService.pronadjiOdeljenjePoId(id).subscribe(a => this.odeljenje = a);
  }

  editOdeljenje() {
    this.odeljenjeService.izmeniOdeljenje(this.odeljenje)
    .subscribe((odeljenje: Odeljenje) =>  {
      alert('Odeljenje ' + odeljenje.razred.vrednost + ' - ' + odeljenje.vrednost + ' je uspešno izmenjen!');
      this.router.navigate(['/odeljenja']);
    });
  }
  goBack() {
    this.location.back();
  }

}

