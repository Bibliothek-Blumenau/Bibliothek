import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class EmprestimoService {
  private apiUrl = 'http://localhost:8080/api/emprestimos';

  constructor(private http: HttpClient) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  agendarEmprestimo(codLivro: number, matricula: string): Observable<string> {
    const headers = this.getAuthHeaders();
    return this.http.post<string>(
      `${this.apiUrl}/solicitar/${codLivro}/${matricula}`,
      null,
      { headers }
    );
  }

  getEmprestimosUsuario(matricula: string): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(`${this.apiUrl}/verificar/${matricula}`, {
      headers,
    });
  }

  getTodosEmprestimos(): Observable<any[]> {
    const headers = this.getAuthHeaders();
    return this.http.get<any[]>(`${this.apiUrl}`, { headers });
  }

  aprovarEmprestimo(codEmprestimo: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post<any>(
      `${this.apiUrl}/emprestimo/${codEmprestimo}`,
      null,
      { headers }
    );
  }

  finalizarEmprestimo(codEmprestimo: number): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post<any>(
      `${this.apiUrl}/finalizar/${codEmprestimo}`,
      null,
      { headers }
    );
  }
}
