import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Player } from 'src/app/models/player.model';
import { environment } from 'src/environments/environment';

/**
 * Service for player data management
 * 
 * @author Lazar
 */
@Injectable({
  providedIn: 'root'
})
export class PlayerService {

  private API_URL: string = environment.API_URL;

  /**
   * @constructor
   * 
   * @param {HttpClient} httpClient 
   */
  constructor(private httpClient: HttpClient) { }

  /**
   * Adds new player to the database
   * 
   * @param {Player} player Player to be added
   * 
   * @returns {Observable<Player>} Added player
   */
  public addPlayer(player: Player): Observable<Player> {
    return this.httpClient.post<Player>(this.API_URL + "/player", player);
  }

  /**
   * Updates player in the database
   * 
   * @param {Player} player Player to be updated
   * 
   * @returns {Observable<Player>} Updated player
   */
  public updatePlayer(player: Player): Observable<Player> {
    return this.httpClient.put<Player>(this.API_URL + "/player", player);
  }

  /**
   * Gets all players from the database
   * 
   * @returns {Observable<Player[]>} Found players
   */
  public getPlayers(): Observable<Player[]> {
    return this.httpClient.get<Player[]>(this.API_URL + "/player")
  }

 /**
  * Filters players based on their last name
  * 
  * @param {string} value Filter value
  * @param {Player[]} players Players for filtering
  * 
  * @returns {Player[]} Filtered players
  */
  public filterPlayers(value: string | Player, players: Player[]): Player[] {
    const filterValue = (value instanceof Player) ? value.lastName : value;
    return players.filter(option => {
      return option.lastName.toLowerCase().includes(filterValue)
    })
  }
}
