import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Livro } from 'src/app/livro';
import { LivroApiService } from 'src/app/livro-api.service';

@Component({
  selector: 'app-recomendacoes',
  templateUrl: './recomendacoes.component.html',
  styleUrls: ['./recomendacoes.component.css'],
})
export class RecomendacoesComponent implements OnInit {
  @Input() livroAtual: Livro | null = null; // Atributo de entrada para o livro atual

  recomendacoes: Livro[] = [];
  recomendacoesLimitadas: Livro[] = [];

  constructor(
    private route: ActivatedRoute,
    private livroApiService: LivroApiService
  ) {}

  ngOnInit(): void {
    this.route.params.subscribe((params) => {
      const codLivro = params['codLivro'];

      if (codLivro) {
        this.livroApiService.getLivroById(codLivro).subscribe((livro) => {
          if (livro) {
            this.livroApiService
              .getRecomendacoesPorGenero(livro.genero)
              .subscribe((recomendacoes) => {
                this.recomendacoes = recomendacoes;
                this.recomendacoes = this.recomendacoes.filter(
                  (recomendacao) =>
                    recomendacao.codLivro !== this.livroAtual?.codLivro
                );
                this.recomendacoesLimitadas = this.recomendacoes.slice(0, 5);
              });
          }
        });
      }
    });
  }
}
