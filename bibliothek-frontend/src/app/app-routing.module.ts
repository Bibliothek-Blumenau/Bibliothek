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
import { PerfilUsuarioComponent } from './sistema/perfil-usuario/perfil-usuario.component';
import { ProfileGuard } from './profile.guard';
import { EmprestimosComponent } from './sistema/emprestimos/emprestimos.component';

const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'login',
    component: LoginComponent,
    title: 'Bibliothek - Login',
  },
  {
    path: 'sistema',
    component: SistemaComponent,
    canActivate: [AuthGuard],
    title: 'Bibliothek - Plataforma',
  },
  {
    path: 'sistema/detalhes/:codLivro',
    component: DetalhesLivroComponent,
    canActivate: [AuthGuard],
    title: 'Bibliothek - Detalhes',
  },
  {
    path: 'sistema/livros',
    component: TodosOsLivrosComponent,
    canActivate: [AuthGuard],
    title: 'Bibliothek - Livros',
  },
  {
    path: 'sistema/buscarlivros',
    component: LivrosViewComponent,
    canActivate: [AuthGuard],
    title: 'Bibliothek - Buscar Livros',
  },
  {
    path: 'sistema/usuario/:matricula',
    component: PerfilUsuarioComponent,
    canActivate: [AuthGuard, ProfileGuard],
    title: 'Bibliothek - Perfil',
  },
  {
    path: 'sistema/cadastrarlivros',
    component: CadastrarLivrosComponent,
    canActivate: [AuthGuard, AdminAuthGuard],
    title: 'Bibliothek - Cadastro de Livros',
  },
  {
    path: 'sistema/cadastrarusuario',
    component: CadastrarUsuarioComponent,
    canActivate: [AuthGuard, AdminAuthGuard],
    title: 'Bibliothek - Cadastro de Usuários',
  },
  {
    path: 'sistema/emprestimos',
    component: EmprestimosComponent,
    canActivate: [AuthGuard, AdminAuthGuard],
    title: 'Bibliothek - Empréstimos',
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
