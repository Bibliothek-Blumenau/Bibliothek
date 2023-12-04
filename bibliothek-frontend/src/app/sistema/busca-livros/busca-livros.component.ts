import { Component, OnInit } from '@angular/core';
import { Observable, of } from 'rxjs';
import { ResultadoBuscaService } from 'src/app/resultado-busca.service'; // Importe o serviço
import { Livro } from 'src/app/livro';

@Component({
  selector: 'app-busca-livros',
  templateUrl: './busca-livros.component.html',
  styleUrls: ['./busca-livros.component.css'],
})
export class BuscaLivrosComponent implements OnInit {
  livros$: Observable<Livro[]> | null = null;

  constructor(private resultadoBuscaService: ResultadoBuscaService) {}

  ngOnInit(): void {
    this.resultadoBuscaService.resultadosBusca$.subscribe((livros) => {
      this.livros$ = of(livros);
    });
  }
}
