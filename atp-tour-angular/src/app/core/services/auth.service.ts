import { Injectable } from '@angular/core';
import { User } from 'src/app/models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  loggedUser: User;
  constructor() {
    var value = sessionStorage['loggedUser'];
    if (value) {
      this.loggedUser = JSON.parse(value);
    }
  }

  getLoggedUser() {
    return this.loggedUser;
  }

  setLoggedUser(user: User) {
    this.loggedUser = user;
  }

  isUserAuthenticated(): boolean {
    if (!this.loggedUser) {
      return false;
    }
    return this.loggedUser.enabled;
  }

  getToken() {
    return 'Basic ' + btoa(`${this.loggedUser.username}:${this.loggedUser.password}`)
  }

  isLoggedIn(): boolean {
    return this.loggedUser ? true : false;
  }

  isAdmin() : boolean{
    return this.loggedUser.roles[0].name==="ADMIN";
  }

  getUserInfo(): string {
    return this.loggedUser.firstName + " " + this.loggedUser.lastName;
  }
}
