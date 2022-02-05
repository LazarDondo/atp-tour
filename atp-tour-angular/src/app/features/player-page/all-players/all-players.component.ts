import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { EventEmitterService } from 'src/app/core/services/event-emitter.service';
import { PlayerService } from 'src/app/core/services/player.service';
import { Player } from 'src/app/models/player.model';

@Component({
  selector: 'app-all-players',
  templateUrl: './all-players.component.html',
  styleUrls: ['./all-players.component.scss']
})
export class AllPlayersComponent implements OnInit {
  @Output()  selectedPlayer = new EventEmitter<Player>();

  players: Player[];
  searchPlayers: Player[];
  columns: string[] = ['firstName', 'lastName', 'birthCountry', 'dateOfBirth', 'currentPoints', 'livePoints'];
  playerName: string;
  pageNumber: number = 1;
  playersPerPage: number = 7;
 

  constructor(private playerService: PlayerService, private eventEmitterService:EventEmitterService) {
    this.playerService.getPlayers().subscribe(players => { this.searchPlayers = players });
    this.playerService.getPlayers().subscribe(players => { this.players = players });
    this.subscribeToUpdatePlayerEvent();
  }

  ngOnInit(): void {
  }

  search() {
    if (!this.playerName) {
      this.ngOnInit();
    }
    else {
      this.playerName = this.playerName.replace(/\s\s+/g, ' ');
      this.players = this.searchPlayers.filter(res => {
        return (res.firstName + " " + res.lastName).toLowerCase().match(this.playerName.toLowerCase());
      })
    }
  }

  key: string = 'rank';
  reverse: boolean = false;
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }

  displayPlayer(player:Player){
    this.selectedPlayer.emit(player);
  }

  subscribeToUpdatePlayerEvent(){
    this.eventEmitterService.subsVar = this.eventEmitterService.    
      invokeUpdatePlayersTableFunction.subscribe((player)=>{  
        let index = this.players.findIndex(p=>player.rank===p.rank)
        index===-1 ? this.players.push(player) : this.players[index] = player;
        this.ngOnInit(); 
      }); 
  }

}
