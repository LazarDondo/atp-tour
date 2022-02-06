import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Tournament } from 'src/app/models/tournament.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TournamentService {

  private API_URL = environment.API_URL;

  constructor(private httpClient:HttpClient) {}

  public addTournament(tournament:Tournament) : Observable<Tournament>{
    return this.httpClient.post<Tournament>(this.API_URL+"/tournament", tournament);
  }
}
