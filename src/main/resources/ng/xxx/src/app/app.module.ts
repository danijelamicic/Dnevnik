import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';

import { AppComponent } from './app.component';
import { ListadministratorComponent } from './components/listadministrator/listadministrator.component';
import { FormadministratorComponent } from './components/formadministrator/formadministrator.component';
import { AppRoutingModule } from './app-routing/app-routing.module';
import { AdministratorService } from './services/administrator.service';
import { MessageService } from './services/message.service';
import { MessageListComponent } from './components/message-list/message-list.component';
import { LoginComponent } from './components/login/login.component';
import { EditadministratorComponent } from './components/editadministrator/editadministrator.component';
import { AddadministratorComponent } from './components/addadministrator/addadministrator.component';
import { AddnastavnikComponent } from './components/addnastavnik/addnastavnik.component';
import { EditnastavnikComponent } from './components/editnastavnik/editnastavnik.component';
import { FormnastavnikComponent } from './components/formnastavnik/formnastavnik.component';
import { ListnastavnikComponent } from './components/listnastavnik/listnastavnik.component';
import { AdducenikComponent } from './components/adducenik/adducenik.component';
import { EditucenikComponent } from './components/editucenik/editucenik.component';
import { ListucenikComponent } from './components/listucenik/listucenik.component';
import { FormucenikComponent } from './components/formucenik/formucenik.component';
import { AddroditeljComponent } from './components/addroditelj/addroditelj.component';
import { EditroditeljComponent } from './components/editroditelj/editroditelj.component';
import { FormroditeljComponent } from './components/formroditelj/formroditelj.component';
import { ListroditeljComponent } from './components/listroditelj/listroditelj.component';
import { DashboardComponent } from './components/dashboard/dashboard.component';
import { AddodeljenjeComponent } from './components/addodeljenje/addodeljenje.component';
import { EditodeljenjeComponent } from './components/editodeljenje/editodeljenje.component';
import { FormodeljenjeComponent } from './components/formodeljenje/formodeljenje.component';
import { ListodeljenjeComponent } from './components/listodeljenje/listodeljenje.component';
import { AddpredajeComponent } from './components/addpredaje/addpredaje.component';
import { EditpredajeComponent } from './components/editpredaje/editpredaje.component';
import { FormpredajeComponent } from './components/formpredaje/formpredaje.component';
import { ListpredajeComponent } from './components/listpredaje/listpredaje.component';

import { AddpredajeuodeljenjuComponent } from './components/addpredajeuodeljenju/addpredajeuodeljenju.component';
import { EditpredajeuodeljenjuComponent } from './components/editpredajeuodeljenju/editpredajeuodeljenju.component';
import { FormpredajeuodeljenjuComponent } from './components/formpredajeuodeljenju/formpredajeuodeljenju.component';
import { ListpredajeuodeljenjuComponent } from './components/listpredajeuodeljenju/listpredajeuodeljenju.component';
import { PogleducenikComponent } from './components/pogleducenik/pogleducenik.component';
import { PogledroditeljComponent } from './components/pogledroditelj/pogledroditelj.component';



@NgModule({
  declarations: [
    AppComponent,
    ListadministratorComponent,
    FormadministratorComponent,
    MessageListComponent,
    LoginComponent,
    EditadministratorComponent,
    AddadministratorComponent,
    AddnastavnikComponent,
    EditnastavnikComponent,
    FormnastavnikComponent,
    ListnastavnikComponent,
    AdducenikComponent,
    EditucenikComponent,
    ListucenikComponent,
    FormucenikComponent,
    AddroditeljComponent,
    EditroditeljComponent,
    FormroditeljComponent,
    ListroditeljComponent,
    DashboardComponent,
    AddodeljenjeComponent,
    EditodeljenjeComponent,
    FormodeljenjeComponent,
    ListodeljenjeComponent,
    AddpredajeComponent,
    EditpredajeComponent,
    FormpredajeComponent,
    ListpredajeComponent,
    AddpredajeuodeljenjuComponent,
    EditpredajeuodeljenjuComponent,
    FormpredajeuodeljenjuComponent,
    ListpredajeuodeljenjuComponent,
    PogleducenikComponent,
    PogledroditeljComponent
  ],
  imports: [

    BrowserModule,
    FormsModule,
    HttpClientModule,
    AppRoutingModule
  ],
  providers: [AdministratorService,
  MessageService],
  bootstrap: [AppComponent]
})
export class AppModule { }
