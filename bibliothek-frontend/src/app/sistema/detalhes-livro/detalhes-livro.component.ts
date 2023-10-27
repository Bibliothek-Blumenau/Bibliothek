import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LivroApiService } from '../../livro-api.service';
import { of } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { Livro } from 'src/app/livro';
import { EmprestimoService } from 'src/app/emprestimo.service';

@Component({
  selector: 'app-detalhes-livro',
  templateUrl: './detalhes-livro.component.html',
  styleUrls: ['./detalhes-livro.component.css'],
})
export class DetalhesLivroComponent implements OnInit {
  livro: Livro | null = null;
  matricula: string | null = localStorage.getItem('matricula');
  mostrarInformacoes: boolean = true;
  mostrarEditar: boolean = false;
  message: string = '';
  messageSuccess: boolean = false;
  messageError: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private livroApiService: LivroApiService,
    private router: Router,
    private emprestimoService: EmprestimoService
  ) {}

  ngOnInit(): void {
    this.route.params
      .pipe(
        switchMap((params) => {
          const codLivro = params['codLivro'];
          if (codLivro) {
            return this.livroApiService.getLivroById(codLivro);
          } else {
            return of(null);
          }
        })
      )
      .subscribe((livro) => {
        this.livro = livro;
      });
  }

  isAdmin(): boolean {
    const roles = localStorage.getItem('roles');
    return !!roles && roles.includes('ROLE_ADMIN');
  }

  agendarEmprestimo(codLivro: number, matricula: string) {
    if (!confirm('Tem certeza de que deseja agendar este livro?')) {
      return;
    }

    this.emprestimoService.agendarEmprestimo(codLivro, matricula).subscribe(
      (response) => {
        this.messageSuccess = true;
        this.messageError = false;
        this.message = 'Livro agendado com sucesso!';
        if (this.livro) {
          this.livro.quantidade = this.livro.quantidade - 1;
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
    if (this.livro) {
      this.livroApiService.editarLivro(this.livro).subscribe(
        (response) => {
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

    if (this.livro) {
      this.livroApiService.apagarLivro(this.livro.codLivro).subscribe(
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
