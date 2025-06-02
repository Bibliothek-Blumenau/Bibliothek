import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Router } from '@angular/router';
import { Observable, tap } from 'rxjs';
import { UserInfo } from '../models/user-info';
import { TokenData } from '../models/token-data';
import { LoginResponse } from '../models/login-response';

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

    const tokenData: TokenData = JSON.parse(atob(token.split('.')[1]));
    const expirationDate = new Date(tokenData.exp * 1000);

    return expirationDate > new Date();
  }

  login(registration: string, password: string): Observable<LoginResponse> {
    const credentials = { registration, password };
    return this.http.post<LoginResponse>(`${this.apiUrl}/login`, credentials);
  }

  registerUser(registerRequest: any): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.post(`${this.apiUrl}/register`, registerRequest, {
      headers,
    });
  }

  getUserInfo(matricula: string): Observable<UserInfo> {
    const headers = this.getAuthHeaders();
    return this.http.get<UserInfo>(`http://localhost:8080/api/user/${matricula}`, { headers });
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
  
  uploadImageToImgbb(imageData: string): Observable<any> {
    const formData = new FormData();
    formData.append('key', 'd0176b7d250adadd6c772429c8ff233b');
    formData.append('image', imageData);

    const headers = new HttpHeaders();

    return this.http.post('https://api.imgbb.com/1/upload', formData, {
      headers,
    });
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
    const token = localStorage.getItem('token');

    if (!token) {
      return false;
    }

    const tokenData: TokenData = JSON.parse(atob(token.split('.')[1]));

    return tokenData.role.includes('ROLE_ADMIN');
  }

  logout(): void {
    localStorage.clear();
    this.router.navigate(['/']);
  }
}
