import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Match } from 'src/app/models/match.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class MatchesService {

  private API_URL = environment.API_URL;

  constructor(private httpClient:HttpClient) {}

  public updateMatches(matches:Match[]) : Observable<Match[]>{
    return this.httpClient.put<Match[]>(this.API_URL+"/matches", matches);
  }

  public filterMatches(filter: Match) : Observable<Match[]>{    
    return this.httpClient.post<Match[]>(this.API_URL+"/matches/filter", filter);
  }

  public addNewMatch(match: Match, rowIndex: number, matches: Match[], updateMatches: Match[]){
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

  private getNextDate(match: Match): string {
    if (match.round === "finals") {
      console.log(match.tournament.completionDate.toDateString);
    }
    var date = new Date(match.matchDate);
    date.setDate(date.getDate() + 2);
    return date.toISOString().split('T')[0];
  }

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
