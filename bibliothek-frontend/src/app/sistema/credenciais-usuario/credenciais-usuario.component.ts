import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { AuthService } from 'src/app/auth.service';
import { Router } from '@angular/router';
import { UploadService } from '../../upload.service';

@Component({
  selector: 'app-credenciais-usuario',
  templateUrl: './credenciais-usuario.component.html',
  styleUrls: ['./credenciais-usuario.component.css'],
})

export class SwitchCredenciaisComponent{

  message: string = '';
  messageSuccess: boolean = false;
  messageError: boolean = false;
  matricula: string | null = localStorage.getItem('matricula');
  usuario: any[] = [];

    constructor(
      private authService: AuthService,
      private datePipe: DatePipe,
      private router: Router,
      private uploadService: UploadService
    ) {}

  ngOnInit(): void {
    this.carregarUsuario();
  }

  carregarUsuario(){
    this.authService.listaUsuarioCredenciais().subscribe(
      (usuarios) => {
        usuarios.forEach((usuario) => {
          this.uploadService.buscarFotoPerfil(usuario.matricula).subscribe(
            (fotoPerfil) => {
              const imageUrl = URL.createObjectURL(fotoPerfil);
              usuario.fotoPerfil = imageUrl;
            },
            (error) => {
              console.error(error);
            }
          );
        });
        this.usuario = usuarios;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  editarCredenciais(usuario: any) {
    this.authService.setUsuarioSelecionado(usuario);
    localStorage.setItem('usuario', JSON.stringify(usuario));
    this.router.navigate(['/sistema/editar-credenciais']);
  }

}
