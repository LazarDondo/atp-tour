import { Component, OnInit } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { AuthService } from 'src/app/core/services/auth.service';


/**
 * Represents the site header component
 * 
 * @author Lazar
 */
@Component({
  selector: 'atp-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {

  loggedIn: boolean = false;
  userInfo: string;

  /**
   * @constructor
   * 
   * @param {AuthService} authService
   */
  constructor(private authService: AuthService, public translate: TranslateService) {
    if (localStorage.getItem('language')) {
      this.translate.use(localStorage.getItem('language')!);
    }
    else {
      translate.setDefaultLang('eng');
      localStorage.setItem('language', 'eng');
    }
  }

  /**
   * Sets userInfo variable if user is logged in
   */
  ngOnInit(): void {
    this.loggedIn = this.authService.isLoggedIn();
    if (this.loggedIn) {
      this.userInfo = this.authService.getUserInfo();
    }
  }

  /**
   * Removes {@link User} from the session
   */
  logout() {
    sessionStorage.removeItem('loggedUser');
  }

  translateLanguageTo(event: any, language: string) {
    event.preventDefault();
    this.translate.use(language);
    localStorage.setItem('language', language);
  }

}
