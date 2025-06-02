import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { BookEntity } from 'src/app/core/models/book-entity';
import { BookApiService } from 'src/app/core/services/book-api.service';

@Component({
  selector: 'app-recommendations',
  templateUrl: './recommendations.component.html',
  styleUrls: ['./recommendations.component.css'],
})
export class Recommendationsomponent implements OnChanges {
  @Input() 
  openBook?: BookEntity;

  recommendations: BookEntity[] = [];

  constructor(private bookApiService: BookApiService) {}

  ngOnChanges(changes: SimpleChanges): void {
    if (changes['openBook']) {
      this.getRecommendations();
    }
  }

  getRecommendations() {
    if (this.openBook) {
      this.bookApiService.getRecommendationsByGenre(this.openBook?.genre)
        .subscribe((recommendations) => {
          this.recommendations = recommendations.filter((book) => book.id !== this.openBook?.id);
        });
      }
  }

}
