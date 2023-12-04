import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { AuthService } from 'src/app/auth.service';
import { EmprestimoService } from '../../emprestimo.service';
import { Router } from '@angular/router';

@Component({
  selector: 'app-renovacoes',
  templateUrl: './renovacoes.component.html',
  styleUrls: ['./renovacoes.component.css'],
})

export class RenovacoesComponent {
  message: string = '';
  messageSuccess: boolean = false;
  messageError: boolean = false;
  matricula: string | null = localStorage.getItem('matricula');
  emprestimos: any[] = [];

  constructor(
    private authService: AuthService,
    private emprestimoService: EmprestimoService,
    private datePipe: DatePipe,
    private router: Router
  ) {}

  ngOnInit(): void {
    if (!this.authService.isAdmin() && this.authService.isRenovacoesAllowed()) {
      this.carregarEmprestimos();
    } else {
      this.router.navigate(['/sistema']);
    }
  }

  carregarEmprestimos() {
    this.emprestimoService.getTodosEmprestimos().subscribe(
      (emprestimos) => {
        this.emprestimos = emprestimos;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  renovarEmprestimo(codEmprestimo: number){
  let emprestimo = this.emprestimos.find(t => t.codEmprestimo === codEmprestimo);
  this.emprestimoService.solicitarEmprestimo(codEmprestimo).subscribe(
     (response) => {
       this.carregarEmprestimos();
     },
     (error) => {
       console.error(error);
     }
   );
  }
}
