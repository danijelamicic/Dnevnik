import { Component, OnInit } from '@angular/core';
import { Roditelj } from '../../entities/roditelj';
import { RoditeljService } from '../../services/roditelj.service';
import { ActivatedRoute, Router } from '@angular/router';
import { Location } from '@angular/common';

@Component({
  selector: 'app-editroditelj',
  templateUrl: './editroditelj.component.html',
  styleUrls: ['./editroditelj.component.css']
})
export class EditroditeljComponent implements OnInit {

  roditelj: Roditelj;


  constructor(private route: ActivatedRoute,
              private router: Router,
              private location: Location,
              private roditeljService: RoditeljService) {
    this.roditelj = new Roditelj();
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.roditeljService.pronadjiRoditeljaPoId(id).subscribe(a => this.roditelj = a);
  }

  editRoditelj() {
    this.roditeljService.izmeniRoditelja(this.roditelj)
    .subscribe((roditelj: Roditelj) =>  {
      alert('Roditelj ' + roditelj.ime + ' ' + roditelj.prezime + ' je uspešno izmenjen!');
      this.router.navigate(['/roditelji']);
    });
  }

  goBack() {
    this.location.back();
  }

}
