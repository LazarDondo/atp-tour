import { Player } from "./player.model";
import { Tournament } from "./tournament.model";

/**
 * Class representing a tennis match
 */
export class Match {
    tournament: Tournament;
    firstPlayer: Player;
    secondPlayer: Player;
    matchDate: string;
    round: string;
    result?: string;
    winner?: Player;
}
