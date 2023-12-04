import { Injectable } from '@angular/core';
import { HttpClient, HttpRequest, HttpEvent, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class UploadService {

  private getAuthHeaders(): HttpHeaders {
    const token = localStorage.getItem('token');
    return new HttpHeaders({
      Authorization: `Bearer ${token}`,
    });
  }

  private baseUrl = 'http://localhost:8080/api/upload';

  constructor(private http: HttpClient) { }

  alterarFotoPerfil(file: File, matricula: string): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);
    formData.append('matricula', matricula);

    const req = new HttpRequest('PUT', `${this.baseUrl}/perfil/imagens`, formData, {
      reportProgress: true,
      responseType: 'text',  // Alterado de 'json' para 'text'
      headers: this.getAuthHeaders()  // Adicionado headers
    });

    return this.http.request(req);
  }

  buscarFotoPerfil(matricula: string): Observable<any> {
    const headers = this.getAuthHeaders();
    return this.http.get(`${this.baseUrl}/perfil/user/imagem/${matricula}`, {
      responseType: 'blob',
      headers: headers
    });
  }


  alterarFotoLivro(file: File, codLivro: number): Observable<HttpEvent<any>> {
    const formData: FormData = new FormData();

    formData.append('file', file);

    const req = new HttpRequest('PUT', `${this.baseUrl}/livro/imagens`, formData, {
      reportProgress: true,
      responseType: 'text',  // Alterado de 'json' para 'text'
      headers: this.getAuthHeaders()  // Adicionado headers
    });

    return this.http.request(req);
  }

  buscarFotoLivro(codLivro: number): Observable<any> {
    return this.http.get(`${this.baseUrl}/livro/imagem/${codLivro}`, {
      responseType: 'blob',
      headers: this.getAuthHeaders()  // Adicionado headers
    });
  }

}
