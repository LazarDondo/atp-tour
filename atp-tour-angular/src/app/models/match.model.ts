import { Injectable } from "@angular/core";
import { Player } from "./player.model";
import { Tournament } from "./tournament.model";

@Injectable({
    providedIn: 'root'
})
export class Match {
    tournament: Tournament;
    firstPlayer: Player;
    secondPlayer: Player;
    matchDate: string;
    round: string;
    result?: string;
    winner?: Player;
}
