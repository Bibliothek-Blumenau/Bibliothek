import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';
import { Livro } from './livro';

@Injectable({
  providedIn: 'root',
})
export class ResultadoBuscaService {
  private _resultadosBuscaSubject = new BehaviorSubject<Livro[]>([]);
  resultadosBusca$ = this._resultadosBuscaSubject.asObservable();

  atualizarResultadosBusca(resultados: Livro[]): void {
    this._resultadosBuscaSubject.next(resultados);
  }
}
