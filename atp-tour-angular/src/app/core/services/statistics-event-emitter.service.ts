import { EventEmitter, Injectable } from '@angular/core';

/**
 * Service for emmiting events related to statistics page
 * 
 * @author Lazar
 */
@Injectable({
  providedIn: 'root'
})
export class StatisticsEventEmitterService {


  invokeCloseDialogFunction = new EventEmitter();

  /**
   * @constructor
   */
  constructor() { }

  /**
   * Closes save statistics dialog
   */
  closeDialog() {
    this.invokeCloseDialogFunction.emit();
  }
}
