import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserService } from 'src/app/core/services/user.service';

/**
 * Represents the registration page component
 * 
 * @author Lazar
 */
@Component({
  selector: 'app-registration-page',
  templateUrl: './registration-page.component.html',
  styleUrls: ['./registration-page.component.scss']
})
export class RegistrationPageComponent implements OnInit {

  registrationForm: FormGroup;
  loading: boolean = false;
  submitted: boolean = false;
  registrationError: boolean = false;
  success: boolean = false;

  /**
   * @constructor
   * 
   * @param {UserService} userService 
   * @param {FormBuilder} formBuilder 
   */
  constructor(private userService: UserService, private formBuilder: FormBuilder) {
  }

  ngOnInit() {
    this.registrationForm = this.configureFormFields();
  }

  /**
   * Sets form variables and registers new user
   * 
   * @return If any form field value is invalid
   */
  onSubmit() {
    this.setFormVariables();
    if (this.registrationForm.invalid) {
      return;
    }
    this.loading = true;
    this.register();
  }

  /**
   * Gets registration form controls
   */
  get f() {
    return this.registrationForm.controls;
  }

  /**
   * Registers new user in the database
   */
  register() {
    this.userService.registerUser(this.registrationForm.value).subscribe({
      next: () => {
        this.registrationError = false;
        this.loading = false;
        this.success = true;
      },
      error: () => {
        this.registrationError = true;
        this.loading = false;
      }
    });
  }

  /**
   * Configures form fields
   * 
   * @returns {FormGroup} Form with configured form fields
   */
  private configureFormFields(): FormGroup {
    var form = this.formBuilder.group({
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      username: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, Validators.minLength(8)]]
    });
    return form;
  }

  /**
   * Sets form variables
   */
  private setFormVariables() {
    this.submitted = true;
    this.success = false;
  }
}