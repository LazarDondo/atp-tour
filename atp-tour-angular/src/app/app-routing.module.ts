import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AuthGuard } from './core/guards/auth.guard';
import { LoginPageComponent } from './features/login-page/login-page.component';
import { MatchesComponent } from './features/matches/matches.component';
import { PlayerPageComponent } from './features/player-page/player-page.component';
import { RegistrationPageComponent } from './features/registration-page/registration-page.component';
import { TournamentPageComponent } from './features/tournament-page/tournament-page.component';

const routes: Routes = [
  {path:'login',component:LoginPageComponent},
  {path:'register',component:RegistrationPageComponent},
  {path:'player',component:PlayerPageComponent, canActivate:[AuthGuard]},
  {path:'tournament',component:TournamentPageComponent},
  {path:'matches',component:MatchesComponent},
  {path:'',redirectTo:'/login', pathMatch:'full'}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
