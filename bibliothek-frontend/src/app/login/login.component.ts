import { Component } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  matricula: string = '';
  password: string = '';
  errorMessage: string = '';

  constructor(private authService: AuthService) {}

  onSubmit() {
    this.authService.login(this.matricula, this.password).subscribe(
      (response: any) => {
        if (response && response.token) {
          localStorage.setItem('token', response.token);
          localStorage.setItem('matricula', this.matricula);
          this.authService.getUserInfo(this.matricula).subscribe(
            (userInfo: { nomeCompleto: string; roles: string }) => {
              localStorage.setItem('nomeCompleto', userInfo.nomeCompleto);
              localStorage.setItem('roles', userInfo.roles);
            },
            (error: any) => {
              console.error('Failed to fetch user info', error);
            }
          );
          this.authService.navigateToSistema();
        } else {
          this.errorMessage = 'Matrícula ou senha invalida.';
        }
      },
      (error: any) => {
        console.error('Login failed', error);
        this.errorMessage = 'Matrícula ou senha invalida.';
      }
    );
  }
}
