import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LivroSearch } from './livro-search';

@Injectable({
  providedIn: 'root',
})
export class LivroApiService {
  private apiUrl = 'http://localhost:8080/api/livros';

  constructor(private http: HttpClient) {}

  getLivrosEmDestaque(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}/destaque`);
  }

  getAllLivros(): Observable<any[]> {
    return this.http.get<any[]>(`${this.apiUrl}`);
  }

  getLivroById(bookId: string): Observable<any> {
    return this.http.get<any>(`${this.apiUrl}/${bookId}`);
  }

  buscarLivros(livroSearch: LivroSearch): Observable<any[]> {
    const params = {
      titulo: livroSearch.titulo,
      tipo: livroSearch.tipo,
    };

    return this.http.get<any[]>(`${this.apiUrl}/busca`, { params });
  }
}
