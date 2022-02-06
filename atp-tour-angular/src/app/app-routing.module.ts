import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LoginPageComponent } from './features/login-page/login-page.component';
import { PlayerPageComponent } from './features/player-page/player-page.component';
import { RegistrationPageComponent } from './features/registration-page/registration-page.component';
import { AddTournamentComponent } from './features/tournament-page/add-tournament/add-tournament.component';

const routes: Routes = [
  {path:'login',component:LoginPageComponent},
  {path:'register',component:RegistrationPageComponent},
  {path:'player',component:PlayerPageComponent},
  {path:'tournament',component:AddTournamentComponent},
  {path:'',redirectTo:'/login', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
