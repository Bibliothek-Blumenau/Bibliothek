import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, Router } from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class ProfileGuard {
  constructor(private router: Router) {}

  canActivate(route: ActivatedRouteSnapshot): boolean {
    const registrationFromUrl = route.paramMap.get('registration');
    const registrationFromLocalStorage = localStorage.getItem('registration');

    if (registrationFromUrl !== registrationFromLocalStorage) {
      this.router.navigate(['/platform']);
      return false;
    }

    return true;
  }
}
