import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tournament } from 'src/app/models/tournament.model';
import { environment } from 'src/environments/environment';

/**
 * Service for tournament data management
 * 
 * @author Lazar
 */
@Injectable({
  providedIn: 'root'
})
export class TournamentService {

  private API_URL: string = environment.API_URL;

  /**
   * @constructor
   * 
   * @param {HttpClient} httpClient 
   */
  constructor(private httpClient: HttpClient) { }

  /**
   * Adds new tournament to the database
   * 
   * @param {Tournament} tournament Tournament to be added
   * 
   * @returns {Observable<Tournament>} Added tournament
   */
  public addTournament(tournament: Tournament): Observable<Tournament> {
    return this.httpClient.post<Tournament>(this.API_URL + "/tournament", tournament);
  }

  /**
   * Updates tournament in the database
   * 
   * @param {Tournament} player Tournament to be updated
   * 
   * @returns {Observable<Tournament>} Updated tournament
   */
  public updateTournament(tournament: Tournament): Observable<Tournament> {
    return this.httpClient.put<Tournament>(this.API_URL + "/tournament", tournament);
  }

  /**
   * Gets all tournaments from the database
   * 
   * @returns {Observable<Tournament[]>} Found tournaments
   */
  public getTournaments(): Observable<Tournament[]> {
    return this.httpClient.get<Tournament[]>(this.API_URL + "/tournament");
  }

  /**
   * Deletes a tournament from the database
   * 
   * @param {number} id Id of the tournament to be deleted
   */
  public deleteTournament(id: number) {
    return this.httpClient.delete(this.API_URL + "/tournament/" + id);
  }

  /**
   * Filters tournaments based on their name
   * 
   * @param {string} value Filter value
   * @param {Tournament[]} tournaments Tournaments for filtering
   * 
   * @returns {Tournament[]} Filtered tournaments
   */
  public filterTournaments(value: string | Tournament, tournaments: Tournament[]): Tournament[] {
    const filterValue = (value instanceof Object) ? value.name : value;
    return tournaments.filter(option => {
      return option.name.toLowerCase().includes(filterValue.toLowerCase());
    })
  }
}
