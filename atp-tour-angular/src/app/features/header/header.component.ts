import { Component, OnInit } from '@angular/core';
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'atp-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.scss']
})
export class HeaderComponent implements OnInit {
  loggedIn = false;
  userInfo: string;

  constructor(private authService: AuthService) { }

  ngOnInit(): void {
    this.loggedIn = this.authService.isLoggedIn();
    if (this.loggedIn) {
      this.userInfo = this.authService.getUserInfo();
    }
  }

  logout() {
    sessionStorage.removeItem('loggedUser');
  }

}
