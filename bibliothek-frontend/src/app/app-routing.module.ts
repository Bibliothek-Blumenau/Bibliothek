import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './home/home.component';
import { LoginComponent } from './login/login.component';
import { SistemaComponent } from './sistema/sistema.component';
import { DetalhesLivroComponent } from './sistema/detalhes-livro/detalhes-livro.component';
import { LivrosViewComponent } from './sistema/livros-view/livros-view.component';
import { TodosOsLivrosComponent } from './sistema/todos-os-livros/todos-os-livros.component';
import { AuthGuard } from './auth.guard';
import { CadastrarLivrosComponent } from './sistema/cadastrar-livros/cadastrar-livros.component';
import { CadastrarUsuarioComponent } from './sistema/cadastrar-usuario/cadastrar-usuario.component';
import { AdminAuthGuard } from './admin.auth.guard';

const routes: Routes = [
  { path: '', component: HomeComponent },
  { path: 'login', component: LoginComponent },
  { path: 'sistema', component: SistemaComponent, canActivate: [AuthGuard] },
  {
    path: 'sistema/detalhes/:cod_livro',
    component: DetalhesLivroComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'sistema/livros',
    component: TodosOsLivrosComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'sistema/buscarlivros',
    component: LivrosViewComponent,
    canActivate: [AuthGuard],
  },
  {
    path: 'sistema/cadastrarlivros',
    component: CadastrarLivrosComponent,
    canActivate: [AuthGuard, AdminAuthGuard],
  },
  {
    path: 'sistema/cadastrarusuario',
    component: CadastrarUsuarioComponent,
    canActivate: [AuthGuard, AdminAuthGuard],
  },
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
