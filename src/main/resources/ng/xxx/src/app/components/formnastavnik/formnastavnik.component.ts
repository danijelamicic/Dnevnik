import { Component, OnInit, Input } from '@angular/core';
import { Nastavnik } from '../../entities/nastavnik';
import { AuthService } from '../../services/auth-service.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-formnastavnik',
  templateUrl: './formnastavnik.component.html',
  styleUrls: ['./formnastavnik.component.css']
})
export class FormnastavnikComponent implements OnInit {

  @Input() nastavnik: Nastavnik;
  activeRoute: string;

  constructor(private authService: AuthService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.url.subscribe(x =>  this.activeRoute = x[0].path);
  }
}

