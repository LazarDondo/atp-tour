import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler, HttpEvent, HttpInterceptor, HttpXsrfTokenExtractor } from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

/**
 * Class represents interceptor for adding Authorization header to HTTP request
 * 
 * @author Lazar
 */
@Injectable(
  {
    providedIn: 'root'
  }
)
export class AuthInterceptor implements HttpInterceptor {

  /**
   * @constructor
   * 
   * @param {AuthService} authService 
   * @param {HttpXsrfTokenExtractor} tokenExtractor 
   */
  constructor(private authService: AuthService, private tokenExtractor: HttpXsrfTokenExtractor) { }

  /**
   * Intercepts http request and adds Authorization header to it
   * 
   * @param {HttpRequest} request 
   * @param {HttpHandler} next 
   * 
   * @returns {Observable<HttpEvent<unknown>>}
   */
  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let headers = request.headers;
    if (this.authService.isUserAuthenticated()) {
      headers = request.headers.set('Authorization', this.authService.getToken());
    }
    const authReq = request.clone({ headers });
    return next.handle(authReq);
  }
}
