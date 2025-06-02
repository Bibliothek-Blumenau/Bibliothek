import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { Page } from '../models/page';
import { BookEntity } from '../models/book-entity';

@Injectable({
  providedIn: 'root',
})
export class BookApiService {
  private readonly apiUrl = 'http://localhost:8080/api/book';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  filterBooks(searchQuery: string): Observable<Page<BookEntity>> {
    const headers = this.getAuthHeaders();
    const params: any = {};

    if (searchQuery?.trim()) {
      params.searchQuery = searchQuery.trim();
    }

    return this.http.get<Page<BookEntity>>(`${this.apiUrl}`, {
      headers,
      params,
    });
  }

  getFeaturedBooks(): Observable<BookEntity[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<BookEntity[]>(`${this.apiUrl}/featured`, { headers });
  }

  getBookById(bookId: string): Observable<BookEntity> {
    const headers = this.getAuthHeaders();
    return this.http.get<BookEntity>(`${this.apiUrl}/${bookId}`, { headers });
  }

  createBook(livro: BookEntity): Observable<BookEntity> {
    const headers = this.getAuthHeaders();
    return this.http.post<BookEntity>(`${this.apiUrl}`, livro, { headers });
  }

  getRecommendationsByGenre(genre: string): Observable<BookEntity[]> {
    const headers = this.getAuthHeaders();
    const params = { genre };
    return this.http.get<BookEntity[]>(`${this.apiUrl}/recommendations`, {
      headers,
      params,
    });
  }

  updateBook(book: BookEntity): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.http.put<void>(`${this.apiUrl}/${book.id}`, book, { headers });
  }

  deleteBook(bookId: string): Observable<void> {
    const headers = this.getAuthHeaders();
    return this.http.delete<void>(`${this.apiUrl}/${bookId}`, { headers });
  }
}
