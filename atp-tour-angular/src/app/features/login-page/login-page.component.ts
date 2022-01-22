import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/core/services/auth.service';
import { UserService } from 'src/app/core/services/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  loginError=false;
  returnUrl: string;


  constructor(
    private userService: UserService,
    private authService: AuthService,
    private formBuilder: FormBuilder) { }

  ngOnInit(): void {

    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required, Validators.email]],
      password: ['', Validators.required]
    });
  }

  onSubmit() {
    this.submitted = true;

    if (this.loginForm.invalid) {
      return;
    }

    this.loading = true;
    this.login();
  }


  login() {
    this.userService.loginUser(this.loginForm.value).subscribe({
      next: loggedUser => {
        loggedUser.password = this.loginForm.get('password')!.value;
        this.authService.setLoggedUser(loggedUser);
        this.loginError=false;
        this.loading=false;
      },
       error: err => {
        this.loginError=true;
        this.loading=false;
      }
    });
  }

  get f() {
    return this.loginForm.controls;
  }

}