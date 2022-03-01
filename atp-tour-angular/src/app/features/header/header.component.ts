import { Component, OnInit } from '@angular/core';
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
  constructor(private authService: AuthService) { }

  /**
   * Displays user's information if the {@link User} is logged in
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

}
