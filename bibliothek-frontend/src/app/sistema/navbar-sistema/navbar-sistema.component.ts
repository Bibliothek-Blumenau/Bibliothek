import { Component } from '@angular/core';
import { AuthService } from 'src/app/auth.service';

@Component({
  selector: 'app-navbar-sistema',
  templateUrl: './navbar-sistema.component.html',
  styleUrls: ['./navbar-sistema.component.css'],
})
export class NavbarSistemaComponent {
  constructor(private authService: AuthService) {}

  isDropdownOpen = false;
  closeTimer: any;

  openDropdown() {
    this.isDropdownOpen = true;
    this.cancelCloseTimer();
  }

  startCloseTimer() {
    this.closeTimer = setTimeout(() => {
      this.isDropdownOpen = false;
    }, 500);
  }

  cancelCloseTimer() {
    if (this.closeTimer) {
      clearTimeout(this.closeTimer);
    }
  }

  logout() {
    this.authService.logout();
  }

  getCurrentUserName(): string {
    return localStorage.getItem('nomeCompleto') || '';
  }

  isAdmin(): boolean {
    const roles = localStorage.getItem('roles');
    return !!roles && roles.includes('ROLE_ADMIN');
  }
}
