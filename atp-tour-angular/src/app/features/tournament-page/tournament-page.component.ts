import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AuthService } from 'src/app/core/services/auth.service';
import { TournamentEventEmitterService } from 'src/app/core/services/tournament-event-emitter.service';
import { Tournament } from 'src/app/models/tournament.model';
import { AddTournamentComponent } from './add-tournament/add-tournament.component';

/**
 * Represents the add tournament page component
 * 
 * @author Lazar
 */
@Component({
  selector: 'app-tournament-page',
  templateUrl: './tournament-page.component.html',
  styleUrls: ['./tournament-page.component.scss']
})
export class TournamentPageComponent implements OnInit {

  selectedTournament: Tournament;
  dialogRef: MatDialogRef<AddTournamentComponent, any>
  isAdminUser: boolean;

  /**
   * 
   * @param {TournamentEventEmitterService} eventEmitterService 
   * @param {MatDialog} dialog 
   * @param {AuthService} authService 
   */
  constructor(private eventEmitterService: TournamentEventEmitterService, private dialog: MatDialog, private authService: AuthService) { }

  /**
   * Gets admin user and subscribes to close dialog event
   */
  ngOnInit(): void {
    this.subscribeToCloseDialogEvent();
    this.isAdminUser = this.authService.isAdmin();
  }

  /**
   * Opens {@link AddTournamentComponent} dialog
   */
  openDialog(): void {
    this.dialogRef = this.dialog.open(AddTournamentComponent, {
      width: '1000px'
    });
  }

  /**
   * Displays selected tournament
   * 
   * @param {Tournament} tournament Selected tournament
   */
  displayTournament(tournament: Tournament) {
    this.selectedTournament = tournament;
    this.eventEmitterService.displayTournament(tournament);
  }

  /**
   * Subsribes to close dialog event
   */
  private subscribeToCloseDialogEvent() {
    this.eventEmitterService.invokeCloseDialogFunction.subscribe(() => {
      this.dialogRef.close();
    });
  }

}
