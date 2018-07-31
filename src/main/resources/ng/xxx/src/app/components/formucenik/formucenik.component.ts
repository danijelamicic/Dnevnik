import { Component, OnInit, Input } from '@angular/core';
import { Ucenik } from '../../entities/ucenik';
import { AuthService } from '../../services/auth-service.service';
import { ActivatedRoute } from '@angular/router';

@Component({
  selector: 'app-formucenik',
  templateUrl: './formucenik.component.html',
  styleUrls: ['./formucenik.component.css']
})
export class FormucenikComponent implements OnInit {

  @Input() ucenik: Ucenik;
  activeRoute: string;

  constructor(private authService: AuthService,
              private activatedRoute: ActivatedRoute) { }

  ngOnInit() {
    this.activatedRoute.url.subscribe(x =>  this.activeRoute = x[0].path);
  }
}

