import { Component, OnInit } from '@angular/core';
import { PlayerEventEmitterService } from 'src/app/core/services/player-event-emitter.service';
import { Player } from 'src/app/models/player.model';
import { MatDialog, MatDialogRef, MAT_DIALOG_DATA } from '@angular/material/dialog'
import { AddPlayerComponent } from './add-player/add-player.component';
import { AuthService } from 'src/app/core/services/auth.service';


/**
 * Represents the add player page component
 * 
 * @author Lazar
 */
@Component({
  selector: 'app-player-page',
  templateUrl: './player-page.component.html',
  styleUrls: ['./player-page.component.scss']
})
export class PlayerPageComponent implements OnInit {

  selectedPlayer: Player;
  dialogRef: MatDialogRef<AddPlayerComponent, any>
  isAdminUser: boolean;

  /**
   * @constructor
   * 
   * @param {PlayerEventEmitterService} eventEmitterService 
   * @param {MatDialog} dialog 
   * @param {AuthService} authService 
   */
  constructor(private eventEmitterService: PlayerEventEmitterService, private dialog: MatDialog, private authService: AuthService) { }

  /**
   * Subsribes to close dialog event
   */
  ngOnInit(): void {
    this.subsribeToCloseDialogEvent();
    this.isAdminUser = this.authService.isAdmin();
  }

  /**
   * Opens {@link AddPlayerComponent} dialog
   */
  openDialog(): void {
    this.dialogRef = this.dialog.open(AddPlayerComponent, {
      width: '600px'
    });
  }

  /**
   * Displays selected player
   *  
   * @param {Player} player Selected player
   */
  displayPlayer(player: Player) {
    this.selectedPlayer = player;
    this.eventEmitterService.displayPlayer(player);
  }

  /**
   * Subscribes to close dialog event
   */
  private subsribeToCloseDialogEvent() {
    this.eventEmitterService.invokeCloseDialogFunction.subscribe(() => {
      this.dialogRef.close();
    });
  }

}