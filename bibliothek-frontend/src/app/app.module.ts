import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';
import { HttpClientModule } from '@angular/common/http';
import { FormsModule } from '@angular/forms';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './features/login/login.component';
import { PlatformComponent } from './features/platform/platform.component';
import { NavbarComponent } from './layout/navbar/navbar.component';
import { FeaturedBooksComponent } from './layout/featured-books/featured-books.component';
import { ListBooksComponent } from './features/platform/list-books/list-books.component';
import { BookDetailsComponent } from './features/platform/book-details/book-details.component';
import { BarraBuscaComponent } from './layout/search-bar/search-bar.component';
import { FooterComponent } from './layout/footer/footer.component';
import { NewBookComponent } from './features/platform/new-book/new-book.component';
import { NewUserComponent } from './features/platform/new-user/new-user.component';
import { UserProfileComponent } from './features/platform/user-profile/user-profile.component';
import { DatePipe } from '@angular/common';
import { LoansComponent } from './features/platform/loans/loans.component';
import { Recommendationsomponent } from './layout/recommendations/recommendations.component';
import { LoadingSpinnerComponent } from './shared/loading-spinner/loading-spinner.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    PlatformComponent,
    NavbarComponent,
    FeaturedBooksComponent,
    ListBooksComponent,
    BookDetailsComponent,
    BarraBuscaComponent,
    FooterComponent,
    NewBookComponent,
    NewUserComponent,
    UserProfileComponent,
    LoansComponent,
    Recommendationsomponent,
    LoadingSpinnerComponent,
  ],
  imports: [BrowserModule, AppRoutingModule, HttpClientModule, FormsModule],
  providers: [DatePipe],
  bootstrap: [AppComponent],
})
export class AppModule {}
