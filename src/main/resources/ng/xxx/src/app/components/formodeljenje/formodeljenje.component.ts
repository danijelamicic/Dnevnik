import { Component, OnInit, Input } from '@angular/core';
import { Odeljenje } from '../../entities/odeljenje';
import { AuthService } from '../../services/auth-service.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-formodeljenje',
  templateUrl: './formodeljenje.component.html',
  styleUrls: ['./formodeljenje.component.css']
})
export class FormodeljenjeComponent implements OnInit {

  @Input() odeljenje: Odeljenje;
  activeRoute: string;

  constructor(private authService: AuthService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.url.subscribe(x =>  this.activeRoute = x[0].path);
  }
}
