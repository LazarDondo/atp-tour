import { Injectable } from '@angular/core';
import { User } from 'src/app/models/user.model';

/**
 * Service for user authentication
 * 
 * @author Lazar
 */
@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private loggedUser: User;

  /**
   * @constructor Sets logged user from session storage if one exists
   */
  constructor() {
    var value = sessionStorage['loggedUser'];
    if (value) {
      this.loggedUser = JSON.parse(value);
    }
  }

  /**
   * Gets logged {@link User}
   * 
   * @returns {User}
   */
  getLoggedUser(): User {
    return this.loggedUser;
  }

  /**
   * Sets logged {@link User}
   * @param {User} user Logged user 
   */
  setLoggedUser(user: User) {
    this.loggedUser = user;
  }

  /**
   * Checks if user is authenticated
   * 
   * @returns {boolean}
   *      <ul>
   *         <li>True if user is authenticated</li>
   *         <li>False if user isn't authenticated</li>
   *      </ul>
   */
  isUserAuthenticated(): boolean {
    if (!this.loggedUser) {
      return false;
    }
    return this.loggedUser.enabled;
  }

  /**
   * Gets Basic Authentication token
   * 
   * @returns {string} Basic Authentication token
   */
  getToken(): string {
    return 'Basic ' + btoa(`${this.loggedUser.username}:${this.loggedUser.password}`)
  }

  /**
   * Checks if user is logged in
   * 
   * @returns {boolean}
   *      <ul>
   *         <li>True if user is logged in</li>
   *         <li>False if user isn't logged in</li>
   *      </ul>
   */
  isLoggedIn(): boolean {
    return this.loggedUser ? true : false;
  }

  /**
   * Checks if user has admin role
   * 
   * @returns {boolean}
   *      <ul>
   *         <li>True if user has admin role</li>
   *         <li>False if user doesn't have admin role</li>
   *      </ul>
   */
  isAdmin(): boolean {
    return this.loggedUser.roles[0].name === "ADMIN";
  }

  /**
   * Gets user's first and last name
   * 
   * @returns {string} User's first and last name
   */
  getUserInfo(): string {
    return this.loggedUser.firstName + " " + this.loggedUser.lastName;
  }
}
