import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AuthService } from '../services/auth.service';

/**
 * Class represents auth guard for preventing users who are not logged in from accessing site content
 * 
 * @author Lazar
 */
@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {

  /**
   * @constructor
   * 
   * @param {Router} router
   * @param {AuthService} authService 
   */
  constructor(private router: Router, private authService: AuthService) { }

  /**
   * Validates if user is logged in or not. If user isn't logged in he or she is redirected to the login page
   * 
   * @param {ActivatedRouteSnapshot} route 
   * @param {RouterStateSnapshot} state 
   * 
   * @returns {boolean}
   *      <ul>
   *         <li>True if the user is logged in</li>
   *         <li>False if the user isn't logged in</li>
   *      </ul>
   */
  canActivate(
    route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (this.authService.isLoggedIn()) {
      return true;
    }
    this.router.navigate(['/login'], { queryParams: { redirect: true } });
    return false;
  }

}
