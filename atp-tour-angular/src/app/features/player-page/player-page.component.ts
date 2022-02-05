import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { EventEmitterService } from 'src/app/core/services/event-emitter.service';
import { Player } from 'src/app/models/player.model';
import {MatDialog, MatDialogRef, MAT_DIALOG_DATA} from '@angular/material/dialog'
import { AddPlayerComponent } from './add-player/add-player.component';


@Component({
  selector: 'app-player-page',
  templateUrl: './player-page.component.html',
  styleUrls: ['./player-page.component.scss']
})
export class PlayerPageComponent implements OnInit {
 
  selectedPlayer:Player;
  dialogRef:MatDialogRef<AddPlayerComponent, any>

  constructor(private eventEmitterService:EventEmitterService, public dialog: MatDialog){}

  ngOnInit(): void {
      this.eventEmitterService.subsVar = this.eventEmitterService.    
      invokeCloseDialogFunction.subscribe(()=>{ 
        this.dialogRef.close();
      });    
    
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