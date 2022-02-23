
import { Injectable } from "@angular/core";
import { Match } from "./match.model";

@Injectable({
    providedIn: 'root'
})
export class Statistics {
    id: number;
    match: Match;
    firstPlayerPoints: number;
    secondPlayerPoints: number;
    firstPlayerAces: number;
    secondPlayerAces: number;
    firstPlayerBreakPoints: number;
    secondPlayerBreakPoints: number;
    firstPlayerFirstServesIn: number;
    secondPlayerFirstServesIn: number;
    firstPlayerSecondServesIn: number;
    secondPlayerSecondServesIn: number;
}
