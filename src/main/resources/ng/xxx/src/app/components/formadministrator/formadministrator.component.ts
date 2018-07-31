import { Component, OnInit, Input } from '@angular/core';
import { Administrator } from '../../entities/administrator';
import { AuthService } from '../../services/auth-service.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-formadministrator',
  templateUrl: './formadministrator.component.html',
  styleUrls: ['./formadministrator.component.css']
})
export class FormadministratorComponent implements OnInit {

  @Input() administrator: Administrator;
  activeRoute: string;

  constructor(private authService: AuthService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.url.subscribe(x =>  this.activeRoute = x[0].path);
  }
}
