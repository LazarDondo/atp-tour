import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpXsrfTokenExtractor
} from '@angular/common/http';
import { Observable } from 'rxjs';
import { AuthService } from '../services/auth.service';

@Injectable(
  {
    providedIn: 'root'
  }
)
export class AuthInterceptor implements HttpInterceptor {

  constructor(private authService: AuthService, private tokenExtractor: HttpXsrfTokenExtractor) { }

  intercept(request: HttpRequest<unknown>, next: HttpHandler): Observable<HttpEvent<unknown>> {
    let headers = request.headers;
    if (this.authService.isUserAuthenticated()) {
      headers = request.headers.set('Authorization', this.authService.getToken());
    }
    const authReq = request.clone({ headers });
    return next.handle(authReq);
  }
}
