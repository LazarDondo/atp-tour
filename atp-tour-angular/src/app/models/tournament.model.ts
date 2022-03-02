import { Country } from "./country.model";

/**
 * Class representing a tennis tournament
 */
export class Tournament {
    id?: number;
    name: string;
    startDate: Date;
    completionDate: Date;
    hostCountry: Country;
    tournamentType: string;
}
