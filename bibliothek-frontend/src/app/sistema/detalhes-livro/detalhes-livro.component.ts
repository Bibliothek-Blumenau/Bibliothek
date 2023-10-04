import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LivroApiService } from '../../livro-api.service';
import { of } from 'rxjs';
import { switchMap } from 'rxjs/operators';
import { Livro } from 'src/app/livro'; // Substitua pelo caminho correto para a sua interface Livro

@Component({
  selector: 'app-detalhes-livro',
  templateUrl: './detalhes-livro.component.html',
  styleUrls: ['./detalhes-livro.component.css'],
})
export class DetalhesLivroComponent implements OnInit {
  livro: Livro | null = null;
  mostrarInformacoes: boolean = true;
  mostrarEditar: boolean = false;
  message: string = '';
  messageSuccess: boolean = false;
  messageError: boolean = false;

  constructor(
    private route: ActivatedRoute,
    private livroApiService: LivroApiService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.route.params
      .pipe(
        switchMap((params) => {
          const cod_livro = params['cod_livro'];
          if (cod_livro) {
            return this.livroApiService.getLivroById(cod_livro);
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
      this.livroApiService.apagarLivro(this.livro.cod_livro).subscribe(
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
