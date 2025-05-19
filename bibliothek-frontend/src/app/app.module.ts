import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './features/login/login.component';
import { SistemaComponent } from './sistema/sistema.component';
import { NavbarSistemaComponent } from './sistema/navbar-sistema/navbar-sistema.component';
import { EmDestaqueComponent } from './sistema/em-destaque/em-destaque.component';
import { ListaLivrosComponent } from './sistema/lista-livros/lista-livros.component';
import { DetalhesLivroComponent } from './sistema/detalhes-livro/detalhes-livro.component';
import { LivrosViewComponent } from './sistema/livros-view/livros-view.component';
import { BarraBuscaComponent } from './sistema/barra-busca/barra-busca.component';
import { BuscaLivrosComponent } from './sistema/busca-livros/busca-livros.component';
import { TodosOsLivrosComponent } from './sistema/todos-os-livros/todos-os-livros.component';
import { FooterComponent } from './layout/footer/footer.component';
import { CadastrarLivrosComponent } from './sistema/cadastrar-livros/cadastrar-livros.component';
import { CadastrarUsuarioComponent } from './sistema/cadastrar-usuario/cadastrar-usuario.component';
import { PerfilUsuarioComponent } from './sistema/perfil-usuario/perfil-usuario.component';
import { DatePipe } from '@angular/common';
import { EmprestimosComponent } from './sistema/emprestimos/emprestimos.component';
import { RecomendacoesComponent } from './sistema/recomendacoes/recomendacoes.component';
import { HomeComponent } from './features/home/home.component';
import { NavbarComponent } from './features/home/navbar/navbar.component';

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
    DetalhesLivroComponent,
    LivrosViewComponent,
    BarraBuscaComponent,
    BuscaLivrosComponent,
    TodosOsLivrosComponent,
    FooterComponent,
    CadastrarLivrosComponent,
    CadastrarUsuarioComponent,
    PerfilUsuarioComponent,
    EmprestimosComponent,
    RecomendacoesComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule],
  providers: [DatePipe],
  bootstrap: [AppComponent],
})
export class AppModule {}
