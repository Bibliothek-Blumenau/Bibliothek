import { Component } from '@angular/core';
import { AuthService } from 'src/app/auth.service';
import { EmprestimoService } from 'src/app/emprestimo.service';
import { DatePipe } from '@angular/common';
import { HttpEventType } from '@angular/common/http';
import { UploadService } from '../../upload.service';

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

  selectedFile: File | null = null;
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
    private datePipe: DatePipe,
    private uploadService: UploadService
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
    return localStorage.getItem('fotoPerfil') || '../../../assets/profile-picture.png';
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

  onFileSelected(event: any) {
    this.selectedFile = <File>event.target.files[0];
  }

  onUpload() {
    if (this.matricula && this.selectedFile) {
      this.uploadService.alterarFotoPerfil(this.selectedFile, this.matricula).subscribe(
        (event: any) => {
          if (event && event.type === HttpEventType.Response) {
            const newProfilePic = event.body;
            localStorage.setItem('fotoPerfil', newProfilePic);
            this.usuario.fotoPerfil = newProfilePic;
            this.messageSuccess = true;
            this.messageError = false;
            this.message = 'Foto do perfil atualizada com sucesso!';
            setTimeout(() => {
              this.buscarFotoPerfil();
              this.usuario.fotoPerfil = this.getProfilePic();
            }, 500);
          } else if (event && event.type === HttpEventType.UploadProgress) {
            const percentDone = Math.round((event.loaded / event.total) * 100);
            this.messageSuccess = false;
            this.messageError = false;
            this.message = `Carregando... ${percentDone}%`;
          }
        },
        (error) => {
          this.messageSuccess = false;
          this.messageError = true;
          this.message = 'Erro ao carregar a foto do perfil.';
          console.log(error);
        }
      );
    }
  }


  private buscarFotoPerfil() {
    if (this.matricula) {
      this.uploadService.buscarFotoPerfil(this.matricula).subscribe(
        (fotoPerfilBlob) => {
          const reader = new FileReader();
          reader.onloadend = () => {
            const profilePicDataUrl = reader.result as string;
            localStorage.setItem('fotoPerfil', profilePicDataUrl);
            this.usuario.fotoPerfil = profilePicDataUrl;
          };
          reader.readAsDataURL(fotoPerfilBlob);
        },
        (error) => {
          console.log(error);
        }
      );
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

    if(!novaSenha.trim() || this.usuario.password.startsWith(' ') || this.usuario.password.endsWith(' ')){
      this.messageSuccess = false;
      this.messageError = true;
      this.message = 'A senha não deve começar ou terminar com espaços em branco.';
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
