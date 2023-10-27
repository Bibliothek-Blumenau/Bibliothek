import { DatePipe } from '@angular/common';
import { Component } from '@angular/core';
import { AuthService } from 'src/app/auth.service';
import { EmprestimoService } from 'src/app/emprestimo.service';

@Component({
  selector: 'app-emprestimos',
  templateUrl: './emprestimos.component.html',
  styleUrls: ['./emprestimos.component.css'],
})
export class EmprestimosComponent {
  message: string = '';
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
        // Atualize a lista de empréstimos após a finalização
        this.carregarEmprestimos();
      },
      (error) => {
        console.error(error);
      }
    );
  }
}
