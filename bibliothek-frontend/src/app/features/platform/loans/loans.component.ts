import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { AuthService } from 'src/app/core/services/auth.service';
import { LoanService } from 'src/app/core/services/loan.service';

@Component({
  selector: 'app-loans',
  templateUrl: './loans.component.html',
  styleUrls: ['./loans.component.css'],
})
export class LoansComponent {
  message: string = '';
  messageSuccess: boolean = false;
  messageError: boolean = false;
  matricula: string | null = localStorage.getItem('matricula');
  emprestimos: any[] = [];

  constructor(
    private authService: AuthService,
    private emprestimoService: LoanService,
    private datePipe: DatePipe
  ) {}

  ngOnInit(): void {
    this.carregarEmprestimos();
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

  aprovarEmprestimo(codEmprestimo: number) {
    this.emprestimoService.aprovarEmprestimo(codEmprestimo).subscribe(
      (response) => {
        this.carregarEmprestimos();
      },
      (error) => {
        console.error(error);
      }
    );
  }

  finalizarEmprestimo(codEmprestimo: number) {
    this.emprestimoService.finalizarEmprestimo(codEmprestimo).subscribe(
      (response) => {
        this.carregarEmprestimos();
      },
      (error) => {
        console.error(error);
      }
    );
  }

  renovarEmprestimo(codEmprestimo: number) {
    this.emprestimoService.renovarEmprestimo(codEmprestimo).subscribe(
      (response) => {
        this.carregarEmprestimos();
      },
      (error) => {
        console.error(error);
      }
    );
  }
}
