import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Match } from 'src/app/models/match.model';
import { Statistics } from 'src/app/models/statistics.model';
import { environment } from 'src/environments/environment';

/**
 * Service for player data management
 * 
 * @author Lazar
 */
@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  private API_URL: string = environment.API_URL;

  /**
   * @constructor
   * 
   * @param {HttpClient} httpClient 
   */
  constructor(private httpClient: HttpClient) { }

  /**
   * Saves statistics to the database
   * 
   * @param {Statistics} statistics Statistics to be saved
   * 
   * @returns {Observable<Statistics>} Saved statistics
   */
  public saveStatistics(statistics: Statistics): Observable<Statistics> {
    return this.httpClient.put<Statistics>(this.API_URL + "/statistics", statistics);
  }

  /**
   * Finds match statistics from the database
   * 
   * @param {Match} match A match for which it is necessary to find statistics
   * 
   * @returns Observable<Statistics> Saved statistics
   */
  public findStatistics(match: Match): Observable<Statistics> {
    return this.httpClient.post<Statistics>(this.API_URL + "/statistics/find", match)
  }
}