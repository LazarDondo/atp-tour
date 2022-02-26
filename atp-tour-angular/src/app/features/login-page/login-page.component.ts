import { Component, Input, OnInit } from '@angular/core';
import { AuthService } from 'src/app/core/services/auth.service';
import { UserService } from 'src/app/core/services/user.service';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';


@Component({
  selector: 'app-login-page',
  templateUrl: './login-page.component.html',
  styleUrls: ['./login-page.component.scss']
})
export class LoginPageComponent implements OnInit {
  loginForm: FormGroup;
  loading = false;
  submitted = false;
  loginError = false;
  redirect = false;


  constructor(private userService: UserService, private formBuilder: FormBuilder, private route: ActivatedRoute) {
    this.route.queryParams.subscribe(params => {
      this.redirect = params['redirect'];
    })
  }

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
        sessionStorage.setItem('loggedUser', JSON.stringify(loggedUser));
        location.reload();
      },
      error: () => {
        this.loginError = true;
        this.loading = false;
      }
    });
  }

  get f() {
    return this.loginForm.controls;
  }

}
