import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css'],
})
export class BarraBuscaComponent {
  searchQuery: string = '';

  constructor(
    private router: Router,
  ) {}

  realizarBusca(): void {
    const trimmedQuery = this.searchQuery.trim();
    if (trimmedQuery.length < 3) {
      const errorMessage =
        'Você precisa digitar no mínimo uma palavra com 3 ou mais caracteres válidos para realizar a busca.';
      window.alert(errorMessage);
      return;
    }

    this.router.navigate(['/platform'], { queryParams: { query: trimmedQuery } });
  }
}
