import { Match } from "./match.model";

/**
 * Class representing a tennis match for saving in excel table
 */
export class ExcelMatch {
    Tournament: string;
    FirstPlayer: string;
    SecondPlayer: string;
    MatchDate: string;
    Round: string;
    Result?: string;
    Winner?: string;

    public constructor(match: Match) {
        this.Tournament = match.tournament.name;
        this.FirstPlayer = match.firstPlayer.firstName + " " + match.firstPlayer.lastName;
        this.SecondPlayer = match.secondPlayer.firstName + " " + match.secondPlayer.lastName;
        this.MatchDate = match.matchDate;
        this.Round = match.round;
        this.Result = match.result;
        this.Winner = match.winner? match.winner.firstName + " " + match.winner.lastName : "";
    }
}
