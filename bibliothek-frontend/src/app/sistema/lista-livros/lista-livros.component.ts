import { Component } from '@angular/core';
import { LivroApiService } from '../../livro-api.service';

@Component({
  selector: 'app-lista-livros',
  templateUrl: './lista-livros.component.html',
  styleUrls: ['./lista-livros.component.css'],
})
export class ListaLivrosComponent {
  livros: any[] = [];

  constructor(private livroApiService: LivroApiService) {}

  ngOnInit(): void {
    this.livroApiService.getAllLivros().subscribe((livros) => {
      this.livros = livros;
    });
  }
}
