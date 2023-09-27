import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { LivroApiService } from '../../livro-api.service';
import { Location } from '@angular/common';
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

  constructor(
    private route: ActivatedRoute,
    private livroApiService: LivroApiService,
    private location: Location
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

  back(): void {
    this.location.back();
  }
}
