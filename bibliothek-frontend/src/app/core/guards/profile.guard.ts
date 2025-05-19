import { Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  RouterStateSnapshot,
  Router,
} from '@angular/router';

@Injectable({
  providedIn: 'root',
})
export class ProfileGuard {
  constructor(private router: Router) {}

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot
  ): boolean {
    const matriculaFromUrl = route.paramMap.get('matricula');
    const matriculaFromLocalStorage = localStorage.getItem('matricula');

    if (matriculaFromUrl === matriculaFromLocalStorage) {
      return true;
    } else {
      this.router.navigate(['/sistema']);
      return false;
    }
  }
}
