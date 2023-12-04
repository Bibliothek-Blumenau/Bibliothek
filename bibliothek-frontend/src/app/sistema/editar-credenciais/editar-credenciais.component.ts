import { Component } from '@angular/core';
import { DatePipe } from '@angular/common';
import { AuthService } from 'src/app/auth.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-editar-credenciais',
  templateUrl: './editar-credenciais.component.html',
  styleUrls: ['./editar-credenciais.component.css'],
})

export class SetCredenciaisComponent {

  constructor(
    private authService: AuthService,
    private datePipe: DatePipe,
    private router: Router
  ) {}

  message: string = '';
  messageSuccess: boolean = false;
  messageError: boolean = false;
  usuario: any;
  matricula: string = '';
  nomeCompleto: string = '';
  password: string = '';
  confirmPassword: string = '';
  show: boolean = false;
  passwordVisible: boolean = false;

  ngOnInit(): void {
    let usuarioString = localStorage.getItem('usuario');
    if (usuarioString) {
      this.usuario = JSON.parse(usuarioString);
      this.matricula = this.usuario.matricula;
      this.nomeCompleto = this.usuario.nomeCompleto;
    }
  }

  renovarCredenciais() {
  if (this.matricula && this.password && this.password === this.confirmPassword) {
      this.authService.renovarSenha(this.matricula, this.password).subscribe(
      (response) => {
        this.clearForm();
        this.messageSuccess = true;
        this.messageError = false;
        this.message = 'Credenciais alteradas com sucesso!';
        setTimeout(() => {
          this.router.navigate(['/sistema/credenciaisusuarios']); // Adicione esta linha
        }, 2000);
      },
      (error) => {
        this.clearForm();
        this.messageSuccess = false;
        this.messageError = true;
        this.message = 'Erro ao alterar credenciais.';
      }
    );
    }
    else if (this.password !== this.confirmPassword) {
        this.message = 'As senhas não correspondem.';
      }
  }

  clearForm() {
    this.usuario = {
      matricula: '',
      nomeCompleto: '',
      password: '',
    };
    this.message = '';
  }
}
