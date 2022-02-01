import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Player } from 'src/app/models/player.model';


@Component({
  selector: 'app-player-page',
  templateUrl: './player-page.component.html',
  styleUrls: ['./player-page.component.scss']
})
export class PlayerPageComponent implements OnInit {
 
  selectedPlayer:Player;

  constructor(){}

  ngOnInit(): void {}

  displayPlayer(player:Player){
    this.selectedPlayer=player;
  }

}