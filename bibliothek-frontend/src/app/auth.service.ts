import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, map } from 'rxjs';

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
  ): Observable<{ nomeCompleto: string; roles: string }> {
    const headers = this.getAuthHeaders();
    return this.http
      .get<any>(`http://localhost:8080/api/users/${matricula}`, { headers })
      .pipe(
        map((response: any) => ({
          nomeCompleto: response.nomeCompleto,
          roles: response.roles,
        }))
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
    this.router.navigate(['/']);
  }

  navigateToSistema(): void {
    this.router.navigate(['/sistema']);
  }
}
