import { Component } from '@angular/core';
import { AuthService } from '../auth.service';
import { UploadService } from '../upload.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent {
  matricula: string = '';
  password: string = '';
  errorMessage: string = '';
  passwordVisible: boolean = false;
    usuario: any = {}; // Adicione esta linha

  constructor(private authService: AuthService, private uploadService: UploadService) {}

  onSubmit() {
    this.authService.login(this.matricula, this.password).subscribe(
      (response: any) => {
        if (response && response.token) {
          this.handleSuccessfulLogin(response);
        } else {
          this.handleFailedLogin();
        }
      },
      (error: any) => {
        console.error('Login failed', error);
        this.handleFailedLogin();
      }
    );
  }

  handleSuccessfulLogin(response: any) {
    localStorage.setItem('token', response.token);
    localStorage.setItem('matricula', this.matricula);
    this.fetchUserInfo();
    this.fetchProfilePicture();
    this.authService.navigateToSistema();
  }

fetchProfilePicture() {
  this.uploadService.buscarFotoPerfil(this.matricula).subscribe(
    (fotoPerfilBlob: any) => {
      const reader = new FileReader();
      reader.onloadend = () => {
        if (reader.result) {
          this.usuario.fotoPerfil = reader.result.toString();
          localStorage.setItem('fotoPerfil', this.usuario.fotoPerfil);
        }
      };
      reader.readAsDataURL(fotoPerfilBlob);
    },
    (error: any) => {
      console.error('Failed to fetch profile picture', error);
    }
  );
}


  handleFailedLogin() {
    this.errorMessage = 'Matrícula ou senha invalida.';
  }

  fetchUserInfo() {
    this.authService.getUserInfo(this.matricula).subscribe(
      (userInfo: {
        nomeCompleto: string;
        roles: string;
        fotoPerfil: string;
      }) => {
        localStorage.setItem('nomeCompleto', userInfo.nomeCompleto);
        localStorage.setItem('roles', userInfo.roles);
        localStorage.setItem('fotoPerfil', userInfo.fotoPerfil);
      },
      (error: any) => {
        console.error('Failed to fetch user info', error);
      }
    );
  }

  isLoggedIn(): boolean {
    const token = localStorage.getItem('token');
    return !!token;
  }

  getCurrentUser(): any {
    return {
      nomeCompleto: localStorage.getItem('nomeCompleto'),
      roles: localStorage.getItem('roles'),
      fotoPerfil: localStorage.getItem('fotoPerfil'),
    };
  }
}
