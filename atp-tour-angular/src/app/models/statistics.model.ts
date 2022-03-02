
import { Match } from "./match.model";

/**
 * Class representing a match statistics
 */
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
