import { Component } from '@angular/core';
import { LivroApiService } from '../../core/services/livro-api.service';
import { Livro } from 'src/app/core/models/livro';

@Component({
  selector: 'app-em-destaque',
  templateUrl: './em-destaque.component.html',
  styleUrls: ['./em-destaque.component.css'],
})
export class EmDestaqueComponent {
  livrosEmDestaque: Livro[] = [];

  constructor(private livroApiService: LivroApiService) {}

  ngOnInit(): void {
    this.livroApiService.getLivrosEmDestaque().subscribe((livros) => {
      this.livrosEmDestaque = livros;
    });
  }
}
