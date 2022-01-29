import { Injectable } from '@angular/core';
import { User } from 'src/app/models/user.model';

@Injectable({
  providedIn: 'root'
})
export class AuthService {

  constructor(private loggedUser:User) { 
    this.loggedUser.enabled=true;
   }

   getLoggedUser(){
     return this.loggedUser;
   }

   setLoggedUser(user: User){
     this.loggedUser=user;
   }

   isUserAuthenticated(){
     return this.loggedUser.enabled;
   }

   getToken(){
    this.loggedUser.username="admin";
    this.loggedUser.password="admin";
    return 'Basic ' + btoa(`${this.loggedUser.username}:${this.loggedUser.password}`)
  }
}
