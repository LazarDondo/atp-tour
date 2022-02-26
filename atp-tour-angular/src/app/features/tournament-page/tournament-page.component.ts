import { Component, OnInit } from '@angular/core';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { AuthService } from 'src/app/core/services/auth.service';
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
  isAdminUser : boolean;

  constructor(private eventEmitterService:TournamentEventEmitterService, private dialog: MatDialog, private authService: AuthService){}

  ngOnInit(): void {
      this.eventEmitterService.    
      invokeCloseDialogFunction.subscribe(()=>{ 
        this.dialogRef.close();
      });  
      this.isAdminUser = this.authService.isAdmin();      
  }

  openDialog(): void {
    this.dialogRef = this.dialog.open(AddTournamentComponent, {
      width: '1000px'});
  }

  displayTournament(tournament:Tournament){
    this.selectedTournament=tournament;
    this.eventEmitterService.displayTournament(tournament);
  }

}
