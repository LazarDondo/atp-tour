import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { TournamentEventEmitterService } from 'src/app/core/services/tournament-event-emitter.service';
import { Tournament } from 'src/app/models/tournament.model';
import { AddTournamentComponent } from './add-tournament/add-tournament.component';

@Component({
  selector: 'app-tournament-page',
  templateUrl: './tournament-page.component.html',
  styleUrls: ['./tournament-page.component.scss']
})
export class TournamentPageComponent implements OnInit {

  selectedTournament:Tournament;
  dialogRef:MatDialogRef<AddTournamentComponent, any>

  constructor(private eventEmitterService:TournamentEventEmitterService, public dialog: MatDialog){}

  ngOnInit(): void {
      this.eventEmitterService.    
      invokeCloseDialogFunction.subscribe(()=>{ 
        this.dialogRef.close();
      });        
  }

  openDialog(): void {
    this.dialogRef = this.dialog.open(AddTournamentComponent, {
      width: '600px'});
  }

  displayTournament(tournament:Tournament){
    this.selectedTournament=tournament;
    this.eventEmitterService.displayTournament(tournament);
  }

}
