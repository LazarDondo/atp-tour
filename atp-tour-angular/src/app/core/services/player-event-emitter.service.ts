import { Injectable, EventEmitter } from '@angular/core';
import { Subscription } from 'rxjs';
import { Player } from 'src/app/models/player.model';

/**
 * Service for emmiting events related to player page
 * 
 * @author Lazar
 */
@Injectable({
  providedIn: 'root'
})
export class PlayerEventEmitterService {

  invokeDisplayPlayerFunction = new EventEmitter();
  invokeCloseDialogFunction = new EventEmitter();
  invokeUpdatePlayersTableFunction = new EventEmitter();

  /**
   * @constructor
   */
  constructor() { }

  /**
   * Displays {@link UpdatePlayerComponent} when the event is triggered
   * 
   * @param {Player} player Player to be displayed
   */
  displayPlayer(player: Player) {
    this.invokeDisplayPlayerFunction.emit(player);
  }

  /**
   * Update all players table
   * 
   * @param {Player} player Player to be added to the table
   */
  updatePlayersTable(player: Player) {
    this.invokeUpdatePlayersTableFunction.emit(player);
  }

  /**
   * Closes add player dialog
   */
  closeDialog() {
    this.invokeCloseDialogFunction.emit();
  }
}
