import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, map, tap } from 'rxjs';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private apiUrl = 'http://localhost:8080/api/auth';

  constructor(private http: HttpClient, private router: Router) {}

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  isTokenValid(): boolean {
    const token = localStorage.getItem('token');
    if (!token) {
      return false;
    }

    const tokenData = JSON.parse(atob(token.split('.')[1]));
    const expirationDate = new Date(tokenData.exp * 1000);

    return expirationDate > new Date();
  }

  login(matricula: string, password: string): Observable<any> {
    const credentials = { matricula, password };
    return this.http.post(`${this.apiUrl}/login`, credentials);
  }

  registerUser(registerRequest: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(`${this.apiUrl}/register`, registerRequest, {
      headers,
    });
  }

  getUserInfo(
    matricula: string
  ): Observable<{ nomeCompleto: string; roles: string; fotoPerfil: string }> {
    const headers = this.getAuthHeaders();
    return this.http
      .get<any>(`http://localhost:8080/api/users/${matricula}`, { headers })
      .pipe(
        map((response: any) => ({
          nomeCompleto: response.nomeCompleto,
          roles: response.roles,
          fotoPerfil: response.fotoPerfil,
        }))
      );
  }

  atualizarFotoPerfil(novaFotoPerfil: string): Observable<any> {
    const matricula = localStorage.getItem('matricula');
    const headers = this.getAuthHeaders();

    return this.http
      .put(
        `${this.apiUrl}/user/${matricula}/foto`,
        { fotoPerfil: novaFotoPerfil },
        { headers }
      )
      .pipe(
        tap(() => {
          localStorage.setItem('fotoPerfil', novaFotoPerfil);
        })
      );
  }

  atualizarSenha(novaSenha: string): Observable<any> {
    const matricula = localStorage.getItem('matricula');
    const headers = this.getAuthHeaders();

    return this.http.put(
      `${this.apiUrl}/user/${matricula}/senha`,
      { password: novaSenha },
      { headers }
    );
  }

  isAdmin(): boolean {
    const roles = localStorage.getItem('roles');
    return !!roles && roles.includes('ROLE_ADMIN');
  }

  logout(): void {
    localStorage.removeItem('token');
    localStorage.removeItem('matricula');
    localStorage.removeItem('nomeCompleto');
    localStorage.removeItem('roles');
    localStorage.removeItem('fotoPerfil');
    this.router.navigate(['/']);
  }

  navigateToSistema(): void {
    this.router.navigate(['/sistema']);
  }
}
