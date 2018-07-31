import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { AdministratorService } from '../../services/administrator.service';
import { Administrator } from '../../entities/administrator';
import { Location } from '@angular/common';

@Component({
  selector: 'app-editadministrator',
  templateUrl: './editadministrator.component.html',
  styleUrls: ['./editadministrator.component.css']
})
export class EditadministratorComponent implements OnInit {

  administrator: Administrator;


  constructor(private route: ActivatedRoute,
              private router: Router,
              private location: Location,
              private administratorService: AdministratorService) {
    this.administrator = new Administrator();
  }

  ngOnInit() {
    const id = +this.route.snapshot.paramMap.get('id');
    this.administratorService.pronadjiAdminaPoId(id).subscribe(a => this.administrator = a);
  }

  editAdministrator() {
    this.administratorService.izmeniAdministratora(this.administrator)
    .subscribe((administrator: Administrator) =>  {
      alert('Administrator ' + administrator.ime + ' ' + administrator.prezime + ' je uspešno izmenjen!');
      this.router.navigate(['/admini']);
    });
  }

  goBack() {
    this.location.back();
  }

}
