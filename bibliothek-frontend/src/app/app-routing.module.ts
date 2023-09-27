import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SistemaComponent } from './sistema/sistema.component';
import { DetalhesLivroComponent } from './sistema/detalhes-livro/detalhes-livro.component';
import { LivrosViewComponent } from './sistema/livros-view/livros-view.component';
import { TodosOsLivrosComponent } from './sistema/todos-os-livros/todos-os-livros.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'sistema', component: SistemaComponent },
  { path: 'sistema/detalhes/:cod_livro', component: DetalhesLivroComponent },
  { path: 'sistema/livros', component: TodosOsLivrosComponent },
  { path: 'sistema/buscarlivros', component: LivrosViewComponent },
];

@NgModule({
  imports: [
    RouterModule.forRoot(routes, {
      scrollPositionRestoration: 'enabled',
    }),
  ],
  exports: [RouterModule],
})
export class AppRoutingModule {}
