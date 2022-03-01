import { NgModule } from '@angular/core';
import { StatisticsComponent } from 'src/app/features/statistics/statistics.component';
import { MatchesComponent } from 'src/app/features/matches/matches.component';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [
    MatchesComponent,
    StatisticsComponent
  ],
  imports: [
    SharedModule
  ]
})
export class MatchesModule { }
