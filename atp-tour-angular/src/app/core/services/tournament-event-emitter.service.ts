import { EventEmitter, Injectable } from '@angular/core';
import { Tournament } from 'src/app/models/tournament.model';

/**
 * Service for emmiting events connected to tournament page
 * 
 * @author Lazar
 */
@Injectable({
  providedIn: 'root'
})
export class TournamentEventEmitterService {

  invokeDisplayTournamentFunction = new EventEmitter();
  invokeUpdateTournamentsTableFunction = new EventEmitter();
  invokeCloseDialogFunction = new EventEmitter();

  /**
   * constructor
   */
  constructor() { }

  /**
   * Displays {@link UpdateTournamentComponent} when the event is triggered
   * 
   * @param {Tournament} tournament Tournament to be displayed
   */
  displayTournament(tournament: Tournament) {
    this.invokeDisplayTournamentFunction.emit(tournament);
  }

  /**
   * Update all tournaments table
   * 
   * @param {Tournament} tournament Tournament to be added to the table
   */
  updateTournamentsTable(tournament: Tournament) {
    this.invokeUpdateTournamentsTableFunction.emit(tournament);
  }


  /**
   * Closes add tournament dialog
   */
  closeDialog() {
    this.invokeCloseDialogFunction.emit();
  }
}
