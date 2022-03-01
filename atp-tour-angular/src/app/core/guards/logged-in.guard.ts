import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, Router, RouterStateSnapshot, UrlTree } from '@angular/router';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

/**
 * Class represents logged in guard for preventing users who are logged in from accessing login or register pages
 * 
 * @author Lazar
 */
@Injectable({
  providedIn: 'root'
})
export class LoggedInGuard implements CanActivate {

  /**
   * @constructor
   * 
   * @param {Router} router
   * @param {AuthService} authService 
   */
  constructor(private router: Router, private authService: AuthService) { }

  /**
   * Validates if user is logged in. If user is logged in he or she is redirected to the players page
   * 
   * @param {ActivatedRouteSnapshot} route
   * @param {RouterStateSnapshot} state 
   * 
   * @returns {boolean}
   *      <ul>
   *         <li>True if the user isn't logged in</li>
   *         <li>False if the user is logged in</li>
   *      </ul>
   */
  canActivate(
    route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
    if (!this.authService.isLoggedIn()) {
      return true;
    }
    this.router.navigate(['/player']);
    return false;
  }

}
