import { NgModule } from '@angular/core';
import { PlayerPageComponent } from 'src/app/features/player-page/player-page.component';
import { AddPlayerComponent } from 'src/app/features/player-page/add-player/add-player.component';
import { AllPlayersComponent } from 'src/app/features/player-page/all-players/all-players.component';
import { UpdatePlayerComponent } from 'src/app/features/player-page/update-player/update-player.component';
import { SharedModule } from '../shared/shared.module';



@NgModule({
  declarations: [
    PlayerPageComponent,
    AddPlayerComponent,
    AllPlayersComponent,
    UpdatePlayerComponent
  ],
  imports: [
    SharedModule
  ]
})
export class PlayerModule { }
