import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { PlayerEventEmitterService } from 'src/app/core/services/player-event-emitter.service';
import { PlayerService } from 'src/app/core/services/player.service';
import { Player } from 'src/app/models/player.model';

/**
 * Represents the all players component
 * 
 * @author Lazar
 */
@Component({
  selector: 'app-all-players',
  templateUrl: './all-players.component.html',
  styleUrls: ['./all-players.component.scss']
})
export class AllPlayersComponent implements OnInit {

  @Output()  selectedPlayer = new EventEmitter<Player>();
  players: Player[];
  searchPlayers: Player[];
  playerName: string;
  pageNumber: number = 1;
  playersPerPage: number = 7;
  key: string = 'rank';
  reverse: boolean = false;
 
 /**
  * @constructor Subsribes to update player event
  * 
  * @param {PlayerService} playerService 
  * @param {PlayerEventEmitterService} eventEmitterService 
  */
  constructor(private playerService: PlayerService, private eventEmitterService:PlayerEventEmitterService) {
    this.playerService.getPlayers().subscribe(players => { this.players=players; this.searchPlayers = players });
    this.subscribeToUpdatePlayerEvent();
  }

  ngOnInit(): void {
  }

  /**
   * Searches for players by first and last name
   */
  search() {
    if ( this.playerName=='') {
      this.players=this.searchPlayers;
      this.ngOnInit();
    }
    else {
      this.playerName = this.playerName.replace(/\s\s+/g, ' ');
      this.players = this.searchPlayers.filter(res => {
        return (res.firstName + " " + res.lastName).toLowerCase().match(this.playerName.toLowerCase());
      });
      this.pageNumber=1;
    }
  }

  /**
   * Sorts table by columns
   * 
   * @param {string} key sort key
   */
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }

  /**
   * Displays selected player
   * 
   * @param {Player} player Selected player
   */
  displayPlayer(player:Player){
    this.selectedPlayer.emit(player);
  }

  /**
   * Subscribes to update player event
   */
  subscribeToUpdatePlayerEvent(){
     this.eventEmitterService.invokeUpdatePlayersTableFunction.subscribe((player)=>{  
        let index = this.players.findIndex(p=>player.id===p.id)
        index===-1 ? this.players.push(player) : this.players[index] = player;
        this.ngOnInit(); 
      }); 
  }

}
