import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/core/services/auth.service';
import { UserService } from 'src/app/core/services/user.service';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  user:User;
  constructor(private userService: UserService, private authService: AuthService) { }

  ngOnInit(): void {
   this.user = new User();
  }

  login() {
    this.userService.loginUser(this.user).subscribe(
      loggedUser=>{
        loggedUser.password=this.user.password;
         this.user=loggedUser;
         this.authService.setLoggedUser(loggedUser);
      }
    );
  }

}
