import { Component } from '@angular/core';
import { AuthService } from 'src/app/core/services/auth.service';

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

  constructor(private authService: AuthService) {}

  cadastrarUsuario() {
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
}
