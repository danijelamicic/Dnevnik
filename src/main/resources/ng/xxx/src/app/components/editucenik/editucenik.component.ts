import { Component, OnInit } from '@angular/core';
import { Ucenik } from '../../entities/ucenik';
import { UcenikService } from '../../services/ucenik.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-editucenik',
  templateUrl: './editucenik.component.html',
  styleUrls: ['./editucenik.component.css']
})
export class EditucenikComponent implements OnInit {

  ucenik: Ucenik;


  constructor(private route: ActivatedRoute,
              private router: Router,
              private location: Location,
              private ucenikService: UcenikService) {
    this.ucenik = new Ucenik();
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.ucenikService.pronadjiUcenikaPoId(id).subscribe(a => this.ucenik = a);
  }

  editUcenik() {
    this.ucenikService.izmeniUcenika(this.ucenik)
    .subscribe((ucenik: Ucenik) =>  {
      alert('Ucenik ' + ucenik.ime + ' ' + ucenik.prezime + ' je uspešno izmenjen!');
      this.router.navigate(['/ucenici']);
    });
  }

  goBack() {
    this.location.back();
  }

}

