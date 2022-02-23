import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Match } from 'src/app/models/match.model';
import { Statistics } from 'src/app/models/statistics.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class StatisticsService {

  private API_URL = environment.API_URL;

  constructor(private httpClient:HttpClient) {}

  public saveStatistics(statistics:Statistics) : Observable<Statistics>{
    return this.httpClient.put<Statistics>(this.API_URL+"/statistics", statistics);
  }

  public findStatistics(match: Match) : Observable<Statistics>{
    return this.httpClient.post<Statistics>(this.API_URL+"/statistics/find", match)
  }
}