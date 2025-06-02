import { Component } from '@angular/core';
import { BookEntity } from 'src/app/core/models/book-entity';
import { BookApiService } from 'src/app/core/services/book-api.service';

@Component({
  selector: 'app-new-book',
  templateUrl: './new-book.component.html',
  styleUrls: ['./new-book.component.css'],
})
export class NewBookComponent {
  livro: BookEntity = {
    title: '',
    genre: '',
    author: '',
    publisher: '',
    stock: 0,
    availableStock: 0,
    coverImage: '',
    description: '',
    featured: false
  };

  message: string = '';
  messageSuccess: boolean = false;
  messageError: boolean = false;

  constructor(private livroApiService: BookApiService) {}

  cadastrarLivro() {
    this.livro.stock = this.livro.availableStock;
    this.livroApiService.createBook(this.livro).subscribe(
      (response) => {
        this.clearForm();
        this.messageSuccess = true;
        this.messageError = false;
        this.message = 'Livro cadastrado com sucesso!';
      },
      (error) => {
        this.clearForm();
        this.messageSuccess = false;
        this.messageError = true;
        this.message = 'Erro ao cadastrar livro.';
      }
    );
  }

  clearForm() {
    this.livro = {
      title: '',
      genre: '',
      author: '',
      publisher: '',
      stock: 0,
      availableStock: 0,
      coverImage: '',
      description: '',
      featured: false
    };
  }
}
