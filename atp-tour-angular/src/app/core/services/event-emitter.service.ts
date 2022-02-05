import { Injectable,EventEmitter } from '@angular/core';
import { Subscription } from 'rxjs';
import { Player } from 'src/app/models/player.model';

@Injectable({
  providedIn: 'root'
})
export class EventEmitterService {

  invokeDisplayPlayerFunction = new EventEmitter();    
  invokeCloseDialogFunction = new EventEmitter();
  invokeUpdatePlayersTableFunction = new EventEmitter();
  
  subsVar: Subscription;    
    
  constructor() { }    
    
  displayPlayer(player:Player) {    
    this.invokeDisplayPlayerFunction.emit(player);    
  }   

  updatePlayersTable(player:Player){
    this.invokeUpdatePlayersTableFunction.emit(player);
  }

  closeDialog(){
    this.invokeCloseDialogFunction.emit();
  }
}
