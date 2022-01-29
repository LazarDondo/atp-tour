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
}
