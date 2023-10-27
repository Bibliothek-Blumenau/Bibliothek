import { Component } from '@angular/core';
import { AuthService } from 'src/app/auth.service';
import { EmprestimoService } from 'src/app/emprestimo.service';
import { DatePipe } from '@angular/common';

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
  matricula: string | null = localStorage.getItem('matricula');
  emprestimos: any[] = [];

  constructor(
    private authService: AuthService,
    private emprestimoService: EmprestimoService,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    if (this.matricula) {
      this.emprestimoService.getEmprestimosUsuario(this.matricula).subscribe(
        (emprestimos) => {
          this.emprestimos = emprestimos;
        },
        (error) => {
          console.error(error);
        }
      );
    }
  }

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
    let novaFotoPerfil = this.usuario.fotoPerfil;

    if (!novaFotoPerfil) {
      this.message = 'Por favor, selecione uma imagem.';
      return;
    }

    if (novaFotoPerfil.match(/^data:image\/[^;]+;base64,/)) {
      novaFotoPerfil = novaFotoPerfil.replace(/^data:image\/[^;]+;base64,/, '');
    }

    this.authService.uploadImageToImgbb(novaFotoPerfil).subscribe(
      (response: any) => {
        const imageUrl = response.data.url;
        this.authService.atualizarFotoPerfil(imageUrl).subscribe(
          () => {
            this.messageSuccess = true;
            this.messageError = false;
            this.message = 'Upload realizado com sucesso!';
            this.voltarParaInformacoes();
          },
          (error) => {
            this.messageSuccess = false;
            this.messageError = true;
            this.message = 'Erro ao atualizar a foto de perfil.';
            console.error(error);
          }
        );
      },
      (error) => {
        this.messageSuccess = false;
        this.messageError = true;
        this.message = 'Erro ao fazer o upload da imagem.';
        console.error(error);
      }
    );
  }

  onFileSelected(event: any) {
    const file: File = event.target.files[0];

    if (file) {
      const reader = new FileReader();

      reader.onload = (e) => {
        this.usuario.fotoPerfil = reader.result as string;
      };

      reader.readAsDataURL(file);
    }
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
