import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from './features/login/login.component';
import { PlatformComponent } from './features/platform/platform.component';
import { BookDetailsComponent } from './features/platform/book-details/book-details.component';
import { AuthGuard } from './core/guards/auth.guard';
import { NewBookComponent } from './features/platform/new-book/new-book.component';
import { NewUserComponent } from './features/platform/new-user/new-user.component';
import { AdminAuthGuard } from './core/guards/admin.auth.guard';
import { UserProfileComponent } from './features/platform/user-profile/user-profile.component';
import { ProfileGuard } from './core/guards/profile.guard';
import { LoansComponent } from './features/platform/loans/loans.component';

const routes: Routes = [
  {
    path: '',
    component: LoginComponent,
    title: 'Bibliothek - Login',
  },
  {
    path: 'platform',
    component: PlatformComponent,
    canActivate: [AuthGuard],
    title: 'Bibliothek - Plataforma',
  },
  {
    path: 'platform/details/:bookId',
    component: BookDetailsComponent,
    canActivate: [AuthGuard],
    title: 'Bibliothek - Detalhes',
  },
  {
    path: 'platform/user/:registration',
    component: UserProfileComponent,
    canActivate: [AuthGuard, ProfileGuard],
    title: 'Bibliothek - Perfil',
  },
  {
    path: 'platform/newBook',
    component: NewBookComponent,
    canActivate: [AuthGuard, AdminAuthGuard],
    title: 'Bibliothek - Cadastro de Livros',
  },
  {
    path: 'sistema/cadastrarusuario',
    component: NewUserComponent,
    canActivate: [AuthGuard, AdminAuthGuard],
    title: 'Bibliothek - Cadastro de Usuários',
  },
  {
    path: 'sistema/emprestimos',
    component: LoansComponent,
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
