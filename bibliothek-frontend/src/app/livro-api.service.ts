import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { LivroSearch } from './livro-search';

@Injectable({
  providedIn: 'root',
})
export class LivroApiService {
  private apiUrl = 'http://localhost:8080/api/livros';
  private authHeader: string;

  constructor(private http: HttpClient) {
    // Codificar as credenciais user e password em base64
    this.authHeader = 'Basic ' + btoa('0:password');
  }

  getLivrosEmDestaque(): Observable<any[]> {
    const headers = new HttpHeaders({
      Authorization: this.authHeader, // Adicionar o cabeçalho de autenticação
    });

    return this.http.get<any[]>(`${this.apiUrl}/destaque`, { headers });
  }

  getAllLivros(): Observable<any[]> {
    const headers = new HttpHeaders({
      Authorization: this.authHeader,
    });
    return this.http.get<any[]>(`${this.apiUrl}`, { headers });
  }

  getLivroById(bookId: string): Observable<any> {
    const headers = new HttpHeaders({
      Authorization: this.authHeader,
    });
    return this.http.get<any>(`${this.apiUrl}/${bookId}`, { headers });
  }

  buscarLivros(livroSearch: LivroSearch): Observable<any[]> {
    const headers = new HttpHeaders({
      Authorization: this.authHeader,
    });

    const params = {
      titulo: livroSearch.titulo,
      tipo: livroSearch.tipo,
    };

    return this.http.get<any[]>(`${this.apiUrl}/busca`, { headers, params });
  }
}
