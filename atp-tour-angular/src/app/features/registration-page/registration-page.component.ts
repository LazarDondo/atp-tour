import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/core/services/user.service';
import { User } from 'src/app/models/user.model';

@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.scss']
})
export class RegistrationPageComponent implements OnInit {
  registerForm: FormGroup;
  loading = false;
  submitted = false;
  registrationError = false;
  success = false;

  constructor(private userService: UserService, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
  }

  get f() {
    return this.registerForm.controls;
  }

  onSubmit() {
    this.submitted = true;
    this.success=false;
    if (this.registerForm.invalid) {
      return;
    }
    this.loading = true;
    this.register();
  }

  register() {
    this.userService.registerUser(this.registerForm.value).subscribe({
      next:() => {
        this.registrationError=false;
        this.loading=false;
        this.success=true;
      },
       error: () => {
        this.registrationError=true;
        this.loading=false;
      }
    });
  }
}