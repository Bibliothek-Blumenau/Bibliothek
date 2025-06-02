import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { BookApiService } from '../../../core/services/book-api.service';
import { LoanService } from 'src/app/core/services/loan.service';
import { BookEntity } from 'src/app/core/models/book-entity';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-book-details',
  templateUrl: './book-details.component.html',
  styleUrls: ['./book-details.component.css'],
})
export class BookDetailsComponent implements OnInit {
  book?: BookEntity;
  registration: string | null = localStorage.getItem('registration');
  mostrarInformacoes: boolean = true;
  mostrarEditar: boolean = false;
  message: string = '';
  messageSuccess: boolean = false;
  messageError: boolean = false;
  isButtonDisabled: boolean = false;

  constructor(
    private readonly route: ActivatedRoute,
    private readonly bookApiService: BookApiService,
    private readonly router: Router,
    private readonly emprestimoService: LoanService,
    private readonly authService: AuthService
  ) {}

  ngOnInit(): void {
    let bookId = '';
    this.message = '';

    this.route.params.subscribe((params) => {
      bookId = params['bookId'];
    });
      
    this.bookApiService.getBookById(bookId).subscribe((livro) => {
      this.book = livro;
      this.verificaEmprestimo();
    });
  }

  isAdmin(): boolean {
    return this.authService.isAdmin();
  }

  agendarEmprestimo(id: string, matricula: string) {
    if (!confirm('Tem certeza de que deseja agendar este livro?')) {
      return;
    }

    this.emprestimoService.agendarEmprestimo(id, matricula).subscribe(
      (response) => {
        this.messageSuccess = true;
        this.messageError = false;
        this.message = 'Livro agendado com sucesso!';
        if (this.book) {
          this.book.availableStock = this.book.availableStock - 1;
          this.isButtonDisabled = true;
        }
      },
      (error) => {
        this.messageSuccess = false;
        this.messageError = true;
        this.message = 'Erro ao agendar livro.';
        console.error(error);
      }
    );
  }

  verificaEmprestimo() {
    this.emprestimoService.getEmprestimosUsuario(this.registration!).subscribe(
      (emprestimos) => {
        for (let emprestimo of emprestimos) {
          if (
            emprestimo.livro.bookId === this.book?.id &&
            !(emprestimo.status === 'Finalizado')
          ) {
            this.isButtonDisabled = true;
            return;
          }
        }
        this.isButtonDisabled = false;
      },
      (error) => {
        console.error(error);
      }
    );
  }

  mostrarEditarLivro() {
    this.mostrarInformacoes = false;
    this.mostrarEditar = true;
    this.message = '';
  }

  voltarParaInformacoes() {
    this.mostrarInformacoes = true;
    this.mostrarEditar = false;
  }

  editarLivro() {
    if (this.book) {
      this.bookApiService.updateBook(this.book).subscribe(
        (response) => {
          this.ngOnInit();
          this.mostrarInformacoes = true;
          this.mostrarEditar = false;
          this.messageSuccess = true;
          this.messageError = false;
          this.message = 'Livro editado com sucesso!';
        },
        (error) => {
          this.messageSuccess = false;
          this.messageError = true;
          this.message = 'Erro ao editar livro.';
          console.error(error);
        }
      );
    }
  }

  apagarLivro() {
    if (!confirm('Tem certeza de que deseja apagar este livro?')) {
      return;
    }

    if (this.book) {
      this.bookApiService.deleteBook(this.book.id ?? 'null').subscribe(
        () => {
          this.message = '';
          this.router.navigate(['/sistema/livros']);
        },
        (error) => {
          this.message = 'Erro ao apagar o livro.';
          console.error(error);
        }
      );
    }
  }
}
