import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { PlayerEventEmitterService } from 'src/app/core/services/player-event-emitter.service';
import { Player } from 'src/app/models/player.model';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog'
import { AddPlayerComponent } from './add-player/add-player.component';
import { AuthService } from 'src/app/core/services/auth.service';


@Component({
  selector: 'app-player-page',
  templateUrl: './player-page.component.html',
  styleUrls: ['./player-page.component.scss']
})
export class PlayerPageComponent implements OnInit {
 
  selectedPlayer:Player;
  dialogRef:MatDialogRef<AddPlayerComponent, any>
  isAdminUser : boolean;

  constructor(private eventEmitterService:PlayerEventEmitterService, private dialog: MatDialog, private authService: AuthService){}

  ngOnInit(): void {
      this.eventEmitterService.subsVar = this.eventEmitterService.    
      invokeCloseDialogFunction.subscribe(()=>{ 
        this.dialogRef.close();
      });    
      this.isAdminUser = this.authService.isAdmin();   
  }


  openDialog(): void {
    this.dialogRef = this.dialog.open(AddPlayerComponent, {
      width: '600px'});
  }


  displayPlayer(player:Player){
    this.selectedPlayer=player;
    this.eventEmitterService.displayPlayer(player);
  }

}