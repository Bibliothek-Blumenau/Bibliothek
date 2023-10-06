import { Component } from '@angular/core';
import { LivroApiService } from '../../livro-api.service';
import { LivroSearch } from '../../livro-search';
import { Router } from '@angular/router';
import { ResultadoBuscaService } from 'src/app/resultado-busca.service';

@Component({
  selector: 'app-barra-busca',
  templateUrl: './barra-busca.component.html',
  styleUrls: ['./barra-busca.component.css'],
})
export class BarraBuscaComponent {
  livroSearch: LivroSearch = { titulo: '', tipo: 'titulo' };

  constructor(
    private livroApiService: LivroApiService,
    private router: Router,
    private resultadoBuscaService: ResultadoBuscaService
  ) {}

  realizarBusca(): void {
    if (this.livroSearch.titulo.length < 3) {
      const errorMessage =
        'Você precisa digitar no mínimo uma palavra com 3 ou mais caracteres válidos para realizar a busca.';
      window.alert(errorMessage);
      return;
    }

    this.livroApiService.buscarLivros(this.livroSearch).subscribe((livros) => {
      this.resultadoBuscaService.atualizarResultadosBusca(livros);
      console.log('Resultados da busca:', livros);
      this.router.navigate(['/sistema/buscarlivros'], {
        queryParams: { busca: this.livroSearch.titulo },
      });
    });
  }
}
