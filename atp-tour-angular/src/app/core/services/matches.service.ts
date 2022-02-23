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
}
