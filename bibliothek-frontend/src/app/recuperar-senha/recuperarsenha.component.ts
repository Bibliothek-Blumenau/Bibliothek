import { Component } from '@angular/core';
import { AuthService } from '../auth.service';

@Component({
  selector: 'app-recuperar-senha',
  templateUrl: './recuperarsenha.component.html',
  styleUrls: ['./recuperarsenha.component.css'],
})
export class RecuperarSenhaComponent {
  matricula: string = '';
  nomeCompleto: string = '';
  message: string = '';
  subMessage: string = '';
  errorMessage: boolean = false;
  successMessage: boolean = false;

  constructor(private authService: AuthService) {}

   onSubmit() {
       this.authService.esqueciMinhaSenha(this.matricula, this.nomeCompleto).subscribe(
         (response) => {
         this.clearForm();
         this.successMessage = true;
         this.message = 'Dados enviados com sucesso!';
         },
         (error) => {
         this.clearForm();
         this.errorMessage = true;
         this.message = 'Matrícula ou Nome inválido.';
         this.subMessage = 'Tente usar seu nome de perfil.';
         }
       );
   }

   clearForm() {
   this.successMessage = false;
   this.errorMessage = false;
   this.message = '';
   this.subMessage = '';
   }
}
