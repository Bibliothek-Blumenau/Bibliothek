import { Component } from '@angular/core';
import { LivroApiService } from '../../livro-api.service';

@Component({
  selector: 'app-em-destaque',
  templateUrl: './em-destaque.component.html',
  styleUrls: ['./em-destaque.component.css'],
})
export class EmDestaqueComponent {
  livrosEmDestaque: any[] = [];

  constructor(private livroApiService: LivroApiService) {}

  ngOnInit(): void {
    this.livroApiService.getLivrosEmDestaque().subscribe((livros) => {
      this.livrosEmDestaque = livros;
    });
  }
}
