import { Component } from '@angular/core';
import { AuthService } from '../../core/services/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  registration: string = '';
  password: string = '';
  errorMessage: string = '';
  isLoading = false;

  constructor(
    private readonly authService: AuthService, 
    private readonly router: Router
  ) {}

  onSubmit() {
    this.isLoading = true;
    this.authService.login(this.registration, this.password).subscribe({
      next: (response) => {
          localStorage.setItem('token', response.token);
          localStorage.setItem('registration', this.registration);
          this.getUserInfo();
      },
      error: () => {
        this.errorMessage = 'Matrícula ou senha invalida.';
        this.isLoading = false;
      }
    });
  }
  
  private getUserInfo() {
    this.authService.getUserInfo(this.registration).subscribe({
      next: (userInfo) => {
        localStorage.setItem('name', userInfo.name);
        localStorage.setItem('profilePic', userInfo.profilePic);

        this.router.navigate(['/platform']);
      },
      error: () => this.errorMessage = 'Matrícula ou senha invalida.',
      complete: () => this.isLoading = false
    })
  }
}
