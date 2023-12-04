import { Component } from '@angular/core';
import { AuthService } from 'src/app/auth.service';

@Component({
  selector: 'app-cadastrar-usuario',
  templateUrl: './cadastrar-usuario.component.html',
  styleUrls: ['./cadastrar-usuario.component.css'],
})
export class CadastrarUsuarioComponent {
  usuario: any = {
    matricula: '',
    nomeCompleto: '',
    password: '',
    roles: 'ROLE_USER',
  };

  message: string = '';
  messageSuccess: boolean = false;
  messageError: boolean = false;
  passwordVisible: boolean = false;

  constructor(private authService: AuthService) {}

  cadastrarUsuario() {
    if (!this.usuario.matricula.trim() || this.usuario.matricula.startsWith(' ') || this.usuario.matricula.endsWith(' ') ||
      !this.usuario.nomeCompleto.trim() || this.usuario.nomeCompleto.startsWith(' ') || this.usuario.nomeCompleto.endsWith(' ') ||
      !this.usuario.password.trim() || this.usuario.password.startsWith(' ') || this.usuario.password.endsWith(' ')) {
      this.messageSuccess = false;
      this.messageError = true;
      this.message = 'As informações não podem estar vazias e não podem começar ou terminar com espaços em branco.';
      return;
    }
    this.authService.registerUser(this.usuario).subscribe(
      (response) => {
        this.clearForm();
        this.messageSuccess = true;
        this.messageError = false;
        this.message = 'Usuário cadastrado com sucesso!';
      },
      (error) => {
        this.clearForm();
        this.messageSuccess = false;
        this.messageError = true;
        this.message = 'Erro ao cadastrar usuário.';
      }
    );
  }

  clearForm() {
    this.usuario = {
      matricula: '',
      nomeCompleto: '',
      password: '',
      roles: 'ROLE_USER',
    };
    this.message = '';
  }

  togglePasswordVisibility() {
    this.passwordVisible = !this.passwordVisible;
  }
}
