import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Player } from 'src/app/models/player.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class PlayerService {
  private API_URL = environment.API_URL;

  constructor(private httpClient:HttpClient) {}

  public addPlayer(player:Player) : Observable<Player>{
    return this.httpClient.post<Player>(this.API_URL+"/player", player);
  }

  public updatePlayer(player:Player) : Observable<Player>{
    return this.httpClient.put<Player>(this.API_URL+"/player", player);
  }

  public getPlayers() : Observable<Player[]>{
    return this.httpClient.get<Player []>(this.API_URL+"/player")
  }

  
  public filterPlayers(value: string | Player, players: Player[]): Player[] {
    const filterValue = (value instanceof Player) ? value.lastName : value;
    return players.filter(option => {
      return option.lastName.toLowerCase().includes(filterValue)
    })
  }
}
