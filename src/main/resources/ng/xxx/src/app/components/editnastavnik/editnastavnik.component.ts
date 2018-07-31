import { Component, OnInit } from '@angular/core';
import { Nastavnik } from '../../entities/nastavnik';
import { NastavnikService } from '../../services/nastavnik.service';
import { Router, ActivatedRoute } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-editnastavnik',
  templateUrl: './editnastavnik.component.html',
  styleUrls: ['./editnastavnik.component.css']
})
export class EditnastavnikComponent implements OnInit {

  nastavnik: Nastavnik;


  constructor(private route: ActivatedRoute,
              private router: Router,
              private location: Location,
              private nastavnikService: NastavnikService) {
    this.nastavnik = new Nastavnik();
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.nastavnikService.pronadjiNastavnikaPoId(id).subscribe(a => this.nastavnik = a);
  }

  editNastavnik() {
    this.nastavnikService.izmeniNastavnika(this.nastavnik)
    .subscribe((nastavnik: Nastavnik) =>  {
      alert('Nastavnik ' + nastavnik.ime + ' ' + nastavnik.prezime + ' je uspešno izmenjen!');
      this.router.navigate(['/nastavnici']);
    });
  }

  goBack() {
    this.location.back();
  }

}
