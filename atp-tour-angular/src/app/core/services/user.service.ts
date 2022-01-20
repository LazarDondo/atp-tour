import { Injectable } from '@angular/core';
import { User } from '../../models/user.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';



@Injectable({
  providedIn: 'root'
})
export class UserService {
  private API_URL = environment.API_URL;
  constructor(private httpClient:HttpClient) { }

  public loginUser(user: User): Observable<User>{
    return this.httpClient.post<User>(this.API_URL+"/user/login", user);
  }

  public registerUser(user: User): Observable<User>{
    return this.httpClient.post<User>(this.API_URL+"/user/register", user);
  }
  
}


