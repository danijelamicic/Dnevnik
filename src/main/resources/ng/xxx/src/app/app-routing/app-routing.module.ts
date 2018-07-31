import { NgModule } from '@angular/core';
import { RouterModule, Route } from '@angular/router';
import { ListadministratorComponent } from '../components/listadministrator/listadministrator.component';
import { LoginComponent } from '../components/login/login.component';
import { AddadministratorComponent } from '../components/addadministrator/addadministrator.component';
import { EditadministratorComponent } from '../components/editadministrator/editadministrator.component';
import { AdminFilterService } from '../services/filter/admin-filter.service';
import { ListnastavnikComponent } from '../components/listnastavnik/listnastavnik.component';
import { AddnastavnikComponent } from '../components/addnastavnik/addnastavnik.component';
import { EditnastavnikComponent } from '../components/editnastavnik/editnastavnik.component';
import { EditucenikComponent } from '../components/editucenik/editucenik.component';
import { ListucenikComponent } from '../components/listucenik/listucenik.component';
import { AdducenikComponent } from '../components/adducenik/adducenik.component';
import { EditroditeljComponent } from '../components/editroditelj/editroditelj.component';
import { AddroditeljComponent } from '../components/addroditelj/addroditelj.component';
import { ListroditeljComponent } from '../components/listroditelj/listroditelj.component';
import { DashboardComponent } from '../components/dashboard/dashboard.component';
import { EditodeljenjeComponent } from '../components/editodeljenje/editodeljenje.component';
import { ListodeljenjeComponent } from '../components/listodeljenje/listodeljenje.component';
import { AddodeljenjeComponent } from '../components/addodeljenje/addodeljenje.component';
import { ListpredajeComponent } from '../components/listpredaje/listpredaje.component';
import { ListpredajeuodeljenjuComponent } from '../components/listpredajeuodeljenju/listpredajeuodeljenju.component';
import { AddpredajeuodeljenjuComponent } from '../components/addpredajeuodeljenju/addpredajeuodeljenju.component';
import { EditpredajeuodeljenjuComponent } from '../components/editpredajeuodeljenju/editpredajeuodeljenju.component';
import { PogleducenikComponent } from '../components/pogleducenik/pogleducenik.component';
import { UcenikFilterService } from '../services/filter/ucenik-filter.service';
import { PogledroditeljComponent } from '../components/pogledroditelj/pogledroditelj.component';
import { KorisnikFilterService } from '../services/filter/korisnik-filter.service';

const routes: Route[] = [

  { path: 'admini', component: ListadministratorComponent, canActivate : [AdminFilterService]},
  { path: 'delete/:id', component: ListadministratorComponent, canActivate : [AdminFilterService]},
  { path: 'login', component: LoginComponent},
  { path: 'add', component: AddadministratorComponent, canActivate : [AdminFilterService]},
  { path: 'edit/:id', component: EditadministratorComponent, canActivate : [AdminFilterService]},
  { path: 'nastavnici', component: ListnastavnikComponent, canActivate : [AdminFilterService]},
  { path: 'addnastavnik', component: AddnastavnikComponent, canActivate : [AdminFilterService]},
  { path: 'editnastavnik/:id', component: EditnastavnikComponent, canActivate : [AdminFilterService]},
  { path: 'ucenici', component: ListucenikComponent, canActivate : [AdminFilterService]},
  { path: 'adducenik', component: AdducenikComponent, canActivate : [AdminFilterService]},
  { path: 'editucenik/:id', component: EditucenikComponent, canActivate : [AdminFilterService]},
  { path: 'roditelji', component: ListroditeljComponent, canActivate : [AdminFilterService]},
  { path: 'addroditelj', component: AddroditeljComponent, canActivate : [AdminFilterService]},
  { path: 'editroditelj/:id', component: EditroditeljComponent, canActivate : [AdminFilterService]},
  { path: 'odeljenja', component: ListodeljenjeComponent, canActivate : [AdminFilterService]},
  { path: 'addodeljenje', component: AddodeljenjeComponent, canActivate : [AdminFilterService]},
  { path: 'editodeljenje/:id', component: EditodeljenjeComponent, canActivate : [AdminFilterService]},
  { path: 'predajeuodeljenju', component: ListpredajeuodeljenjuComponent, canActivate : [AdminFilterService]},
  { path: 'addpredajeuodeljenju', component: AddpredajeuodeljenjuComponent, canActivate : [AdminFilterService]},
  { path: 'editpredajeuodeljenju/:id', component: EditpredajeuodeljenjuComponent, canActivate : [AdminFilterService]},
  { path: 'predaje', component: ListpredajeComponent, canActivate : [AdminFilterService]},
  { path: 'ocenest', component: PogleducenikComponent, canActivate : [UcenikFilterService]},
  { path: 'decaroditelja', component: PogledroditeljComponent, canActivate : [KorisnikFilterService]},
  { path: 'dashboard', component: DashboardComponent },
  { path: '', redirectTo: '/login', pathMatch: 'full'}
];


@NgModule({
  imports: [
    RouterModule.forRoot(routes)
  ],
  exports: [
    RouterModule
  ],
  declarations: []
})
export class AppRoutingModule { }
