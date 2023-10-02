import { Component } from '@angular/core';
import { LivroApiService } from 'src/app/livro-api.service';
import { Livro } from 'src/app/livro';

@Component({
  selector: 'app-cadastrar-livros',
  templateUrl: './cadastrar-livros.component.html',
  styleUrls: ['./cadastrar-livros.component.css'],
})
export class CadastrarLivrosComponent {
  livro: Livro = {
    cod_livro: 0,
    titulo: '',
    genero: '',
    autor: '',
    editora: '',
    quantidade: 0,
    imagemUrl: '',
    destaque: false,
    descricao: '',
  };

  message: string = '';

  constructor(private livroApiService: LivroApiService) {}

  cadastrarLivro() {
    this.livroApiService.createLivro(this.livro).subscribe(
      (response) => {
        this.clearForm();
        this.message = 'Livro cadastrado com sucesso!';
      },
      (error) => {
        this.clearForm();
        this.message = 'Erro ao cadastrar livro.';
      }
    );
  }

  clearForm() {
    this.livro = {
      cod_livro: 0,
      titulo: '',
      genero: '',
      autor: '',
      editora: '',
      quantidade: 0,
      imagemUrl: '',
      destaque: false,
      descricao: '',
    };
  }
}
