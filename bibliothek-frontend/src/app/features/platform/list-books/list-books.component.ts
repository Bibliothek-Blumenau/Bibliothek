import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { BookApiService } from '../../../core/services/book-api.service';
import { BookEntity } from 'src/app/core/models/book-entity';
import { Page } from 'src/app/core/models/page';

@Component({
  selector: 'app-list-books',
  templateUrl: './list-books.component.html',
  styleUrls: ['./list-books.component.css'],
})
export class ListBooksComponent implements OnInit, OnChanges {
  @Input()
  public query = '';

  pagedResult?: Page<BookEntity>;
  currentPage = 0;
  totalPages = 1;
  books?: BookEntity[];
  loading = true;

  constructor(
    private readonly bookApiService: BookApiService,
  ) {}

  ngOnInit(): void {
    this.getFilteredBooks();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['query'] && !changes['query'].firstChange) {
      this.currentPage = 0;
      this.getFilteredBooks();
    }
  }

  getFilteredBooks(): void {
    this.loading = true;

    this.bookApiService.filterBooks(this.query)
    .subscribe({
        next: (pagedResult) => {
          this.pagedResult = pagedResult;
          this.totalPages = pagedResult.totalPages;
          this.books = pagedResult.content;
          this.loading = false;
        }
      })
  }

  nextPage() {
    if (this.currentPage < this.totalPages - 1) {
      this.currentPage++;
      this.getFilteredBooks();
    }
  }

  prevPage() {
    if (this.currentPage > 0) {
      this.currentPage--;
      this.getFilteredBooks();
    }
  }
}
