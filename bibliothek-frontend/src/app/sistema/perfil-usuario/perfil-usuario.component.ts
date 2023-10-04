import { Component } from '@angular/core';
import { AuthService } from 'src/app/auth.service';

@Component({
  selector: 'app-perfil-usuario',
  templateUrl: './perfil-usuario.component.html',
  styleUrls: ['./perfil-usuario.component.css'],
})
export class PerfilUsuarioComponent {
  usuario: any = {
    password: '',
    fotoPerfil: localStorage.getItem('fotoPerfil') || '',
  };

  message: string = '';
  mostrarInformacoes: boolean = true;
  mostrarFoto: boolean = false;
  mostrarSenha: boolean = false;
  messageSuccess: boolean = false;
  messageError: boolean = false;

  constructor(private authService: AuthService) {}

  getCurrentUserName(): string {
    return localStorage.getItem('nomeCompleto') || '';
  }

  getProfilePic(): string {
    return localStorage.getItem('fotoPerfil') || '';
  }

  mostrarAdicionarFoto() {
    this.message = '';
    this.mostrarInformacoes = false;
    this.mostrarFoto = true;
    this.mostrarSenha = false;
  }

  mostrarAlterarSenha() {
    this.message = '';
    this.mostrarInformacoes = false;
    this.mostrarFoto = false;
    this.mostrarSenha = true;
  }

  voltarParaInformacoes() {
    this.mostrarInformacoes = true;
    this.mostrarFoto = false;
    this.mostrarSenha = false;
  }

  alterarFotoPerfil() {
    const novaFotoPerfil = this.usuario.fotoPerfil;

    if (!novaFotoPerfil) {
      this.message = 'A URL da foto de perfil não pode estar vazia.';
      return;
    }

    this.authService.atualizarFotoPerfil(novaFotoPerfil).subscribe(
      () => {
        this.messageSuccess = true;
        this.messageError = false;
        this.message = 'Foto de perfil atualizada com sucesso!';
        this.voltarParaInformacoes();
      },
      (error) => {
        this.messageSuccess = false;
        this.messageError = true;
        this.message = 'Erro ao atualizar a foto de perfil.';
        console.error(error);
      }
    );
  }

  alterarSenha() {
    const novaSenha = this.usuario.password;

    if (!novaSenha) {
      this.messageSuccess = false;
      this.messageError = true;
      this.message = 'A nova senha não pode estar vazia.';
      return;
    }

    this.authService.atualizarSenha(novaSenha).subscribe(
      () => {
        this.messageSuccess = true;
        this.messageError = false;
        this.message = 'Senha atualizada com sucesso!';
        this.usuario.password = '';
        this.voltarParaInformacoes();
      },
      (error) => {
        this.messageSuccess = false;
        this.messageError = true;
        this.message = 'Erro ao atualizar a senha.';
        console.error(error);
      }
    );
  }
}
