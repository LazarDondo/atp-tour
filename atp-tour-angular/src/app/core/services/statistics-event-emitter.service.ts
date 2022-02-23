import { EventEmitter, Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'
})
export class StatisticsEventEmitterService {

  
  invokeCloseDialogFunction = new EventEmitter();
      
  constructor() { }    

  closeDialog(){
    this.invokeCloseDialogFunction.emit();
  }
}
