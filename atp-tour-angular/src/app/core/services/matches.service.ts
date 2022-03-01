import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Match } from 'src/app/models/match.model';
import { environment } from 'src/environments/environment';

/**
 * Service for matches data management
 * 
 * @author Lazar
 */
@Injectable({
  providedIn: 'root'
})
export class MatchesService {


  private API_URL: string = environment.API_URL;

  /**
   * @constructor
   * 
   * @param {HttpClient} httpClient 
   */
  constructor(private httpClient: HttpClient) { }

  /**
   * Updates matches in the database
   * 
   * @param {Match[]} matches Matches to be updated
   *  
   * @returns {Observable<Match[]>}
   */
  public updateMatches(matches: Match[]): Observable<Match[]> {
    return this.httpClient.put<Match[]>(this.API_URL + "/matches", matches);
  }

  /**
   * Gets matches from the database by filter value. Matches can be filtered by tournament, first or second player
   * 
   * @param {Match} filter match containing filter values
   * 
   * @returns {Observable<Match[]>} Filtered matches
   */
  public filterMatches(filter: Match): Observable<Match[]> {
    return this.httpClient.post<Match[]>(this.API_URL + "/matches/filter", filter);
  }

  /**
   * Adds next round match if both matches from the draw have finished
   * 
   * @param {Match} match Finished match
   * @param {number} rowIndex Index of the finished match in the table
   * @param {Match[]} matches All the matches from the same tournament as the finished match 
   * @param {Match[]} updateMatches All changed matches ready for update
  */
  public addNewMatch(match: Match, rowIndex: number, matches: Match[], updateMatches: Match[]) {
    if (match.round === "finals") {
      return;
    }
    var nextOpponent = rowIndex % 2 === 0 ? matches[rowIndex + 1].winner : matches[rowIndex - 1].winner
    if (!nextOpponent) {
      return;
    }
    var nextRound = this.getNextRound(match.round);
    var nextDate = this.getNextDate(match);
    var newMatch: Match = {
      tournament: match.tournament, firstPlayer: match.winner!,
      secondPlayer: nextOpponent, matchDate: nextDate, round: nextRound
    };
    updateMatches.push(newMatch);
  }

  /**
   * Gets date of the following match
   * 
   * @param {Match} match Finished match
   * 
   * @returns {string} Date for the match in the next round
   */
  private getNextDate(match: Match): string {
    var date = new Date(match.matchDate);
    date.setDate(date.getDate() + 2);
    return date.toISOString().split('T')[0];
  }

  /**
   * Gets the name of the next round
   * 
   * @param {string} round Current round
   * 
   * @returns {string} Name of the following round
   */
  private getNextRound(round: string): string {
    switch (round) {
      case 'eights-finals':
        return 'quater-finals';
      case 'quater-finals':
        return 'semi-finals';
      case 'semi-finals':
        return 'finals';
      default:
        return '';
    }
  }
}
