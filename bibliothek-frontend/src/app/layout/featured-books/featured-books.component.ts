import { Component } from '@angular/core';
import { BookApiService } from '../../core/services/book-api.service';
import { BookEntity } from 'src/app/core/models/book-entity';

@Component({
  selector: 'app-featured-books',
  templateUrl: './featured-books.component.html',
  styleUrls: ['./featured-books.component.css'],
})
export class FeaturedBooksComponent {
  featuredBooks: BookEntity[] = [];

  constructor(private livroApiService: BookApiService) {}

  ngOnInit(): void {
    this.livroApiService.getFeaturedBooks().subscribe((books) => {
      this.featuredBooks = books;
    });
  }
}
