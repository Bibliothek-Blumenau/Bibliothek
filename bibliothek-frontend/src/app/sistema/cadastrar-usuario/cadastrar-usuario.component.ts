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
    roles: 'ROLE_USER', // Default role is 'Aluno'
  };

  message: string = '';

  constructor(private authService: AuthService) {}

  cadastrarUsuario() {
    this.authService.registerUser(this.usuario).subscribe(
      (response) => {
        this.clearForm();
        this.message = 'Usuário cadastrado com sucesso!';
      },
      (error) => {
        this.clearForm();
        this.message = 'Erro ao cadastrar usuário.';
      }
    );
  }

  clearForm() {
    this.usuario = {
      matricula: '',
      nomeCompleto: '',
      password: '',
      roles: 'ROLE_USER', // Default role is 'Aluno'
    };
    this.message = '';
  }
}
