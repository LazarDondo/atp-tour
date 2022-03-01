import { NgModule } from '@angular/core';
import { LoginPageComponent } from 'src/app/features/login-page/login-page.component';
import { RegistrationPageComponent } from 'src/app/features/registration-page/registration-page.component';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [
    LoginPageComponent,
    RegistrationPageComponent
  ],
  imports: [
    SharedModule
  ]
})
export class AuthenticationModule { }
