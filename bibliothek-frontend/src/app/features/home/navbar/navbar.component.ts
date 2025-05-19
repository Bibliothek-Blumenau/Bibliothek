import { Component } from '@angular/core';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent {
  hamburgerActive = false;
  navMenuActive = false;

  toggleHamburger() {
    this.hamburgerActive = !this.hamburgerActive;
    this.navMenuActive = !this.navMenuActive;
  }

  closeNavMenu() {
    this.hamburgerActive = false;
    this.navMenuActive = false;
  }
}
