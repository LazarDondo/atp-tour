import { NgModule } from '@angular/core';
import { TournamentPageComponent } from 'src/app/features/tournament-page/tournament-page.component';
import { AddTournamentComponent } from 'src/app/features/tournament-page/add-tournament/add-tournament.component';
import { AllTournamentsComponent } from 'src/app/features/tournament-page/all-tournaments/all-tournaments.component';
import { UpdateTournamentComponent } from 'src/app/features/tournament-page/update-tournament/update-tournament.component';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [
    TournamentPageComponent,
    AddTournamentComponent,
    AllTournamentsComponent,
    UpdateTournamentComponent
  ],
  imports: [
    SharedModule
  ]
})
export class TournamentModule { }
