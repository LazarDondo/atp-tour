import { Component, Input, OnInit } from '@angular/core';
import { UserService } from 'src/app/core/services/user.service';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  user:User;
  constructor(private userService: UserService) { }

  ngOnInit(): void {
   this.user = new User();
  }

  login() {
    console.log(this.user.username);
    this.userService.loginUser(this.user).subscribe(
      loggedUser=>{
        console.log(loggedUser);
      }
    );
  }

}
