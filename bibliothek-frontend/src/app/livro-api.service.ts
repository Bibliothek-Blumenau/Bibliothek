import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LivroSearch } from './livro-search';
import { Livro } from './livro';

@Injectable({
  providedIn: 'root',
})
export class LivroApiService {
  private apiUrl = 'http://localhost:8080/api/livros';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  getLivrosEmDestaque(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(`${this.apiUrl}/destaque`, { headers });
  }

  getAllLivros(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(`${this.apiUrl}`, { headers });
  }

  getLivroById(bookId: string): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get<any>(`${this.apiUrl}/${bookId}`, { headers });
  }

  createLivro(livro: Livro): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post<any>(`${this.apiUrl}`, livro, { headers });
  }

  buscarLivros(livroSearch: LivroSearch): Observable<any[]> {
    const headers = this.getAuthHeaders();
    const params = {
      titulo: livroSearch.titulo,
      tipo: livroSearch.tipo,
    };
    return this.http.get<any[]>(`${this.apiUrl}/busca`, { headers, params });
  }

  editarLivro(livro: Livro): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.put(`${this.apiUrl}/${livro.cod_livro}`, livro, {
      headers,
    });
  }

  apagarLivro(codLivro: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.delete(`${this.apiUrl}/${codLivro}`, { headers });
  }
}
