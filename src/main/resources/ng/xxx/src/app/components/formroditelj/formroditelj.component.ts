import { Component, OnInit, Input } from '@angular/core';
import { Roditelj } from '../../entities/roditelj';
import { AuthService } from '../../services/auth-service.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-formroditelj',
  templateUrl: './formroditelj.component.html',
  styleUrls: ['./formroditelj.component.css']
})
export class FormroditeljComponent implements OnInit {

  @Input() roditelj: Roditelj;
  activeRoute: string;

  constructor(private authService: AuthService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.url.subscribe(x =>  this.activeRoute = x[0].path);
  }
}

