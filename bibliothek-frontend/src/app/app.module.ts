import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SistemaComponent } from './sistema/sistema.component';
import { NavbarComponent } from './home/navbar/navbar.component';
import { NavbarSistemaComponent } from './sistema/navbar-sistema/navbar-sistema.component';
import { EmDestaqueComponent } from './sistema/em-destaque/em-destaque.component';
import { ListaLivrosComponent } from './sistema/lista-livros/lista-livros.component';

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    LoginComponent,
    SistemaComponent,
    NavbarComponent,
    NavbarSistemaComponent,
    EmDestaqueComponent,
    ListaLivrosComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule],
  providers: [],
  bootstrap: [AppComponent],
})
export class AppModule {}
