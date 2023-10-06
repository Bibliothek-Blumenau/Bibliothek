import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LivroSearch } from './livro-search';
import { Livro } from './livro';
import { Page, Pageable } from './pageable';

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

  getAllLivros(page: number, size: number): Observable<Page<any>> {
    const headers = this.getAuthHeaders();
    const params = new HttpParams()
      .set('page', page.toString())
      .set('size', size.toString());

    return this.http.get<Page<any>>(`${this.apiUrl}`, { headers, params });
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
