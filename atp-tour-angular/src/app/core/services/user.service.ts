import { Injectable } from '@angular/core';
import { User } from '../../models/user.model';
import { Observable } from 'rxjs';
import { HttpClient } from '@angular/common/http';
import { environment } from 'src/environments/environment';

/**
 * Service for user data management
 * 
 * @author Lazar
 */
@Injectable({
  providedIn: 'root'
})
export class UserService {

  private API_URL: string = environment.API_URL;

  /**
   * @constructor
   * 
   * @param {HttpClient} httpClient 
   */
  constructor(private httpClient: HttpClient) { }

  /**
   * Gets user from the database based on the credentials
   * 
   * @param {User} user User to be found
   * 
   * @returns {Observable<User>} Found user
   */
  public loginUser(user: User): Observable<User> {
    return this.httpClient.post<User>(this.API_URL + "/user/login", user);
  }

  /**
   * Adds new user to the database
   * 
   * @param {User} user User to be added
   * 
   * @returns {Observable<User>} Added user
   */
  public registerUser(user: User): Observable<User> {
    return this.httpClient.post<User>(this.API_URL + "/user/register", user);
  }

}


