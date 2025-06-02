import { ComponentFixture, TestBed } from '@angular/core/testing';

import { FeaturedBooksComponent } from './featured-books.component';

describe('EmDestaqueComponent', () => {
  let component: FeaturedBooksComponent;
  let fixture: ComponentFixture<FeaturedBooksComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [FeaturedBooksComponent]
    });
    fixture = TestBed.createComponent(FeaturedBooksComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
