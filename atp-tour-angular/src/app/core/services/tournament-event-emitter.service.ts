import { EventEmitter, Injectable } from '@angular/core';
import { Tournament } from 'src/app/models/tournament.model';

@Injectable({
  providedIn: 'root'
})
export class TournamentEventEmitterService {

  invokeDisplayTournamentFunction = new EventEmitter();
  invokeUpdateTournamentsTableFunction = new EventEmitter();
  invokeCloseDialogFunction = new EventEmitter();

  constructor() { }

  displayTournament(tournament: Tournament) {
    this.invokeDisplayTournamentFunction.emit(tournament);
  }

  updateTournamentsTable(tournament: Tournament) {
    this.invokeUpdateTournamentsTableFunction.emit(tournament);
  }

  closeDialog() {
    this.invokeCloseDialogFunction.emit();
  }
}
