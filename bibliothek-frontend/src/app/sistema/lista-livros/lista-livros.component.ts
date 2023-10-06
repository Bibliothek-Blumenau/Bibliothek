import { Component, ElementRef } from '@angular/core';
import { LivroApiService } from '../../livro-api.service';
import { Livro } from 'src/app/livro';

@Component({
  selector: 'app-lista-livros',
  templateUrl: './lista-livros.component.html',
  styleUrls: ['./lista-livros.component.css'],
})
export class ListaLivrosComponent {
  livros: Livro[] = [];
  currentPage = 0;
  pageSize = 8;
  totalPages = 0;

  constructor(
    private livroApiService: LivroApiService,
    private el: ElementRef
  ) {}

  ngOnInit(): void {
    this.carregarLivros();
  }

  carregarLivros() {
    this.livroApiService
      .getAllLivros(this.currentPage, this.pageSize)
      .subscribe((page) => {
        this.livros = page.content;
        this.totalPages = page.totalPages;
        this.scrollToAnchor();
        window.scrollBy(0, -70);
      });
  }

  scrollToAnchor() {
    const element = this.el.nativeElement.querySelector('#anchor');
    if (element) {
      element.scrollIntoView();
    }
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.carregarLivros();
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.carregarLivros();
    }
  }
}
