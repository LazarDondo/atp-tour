import { Country } from "./country.model";

export class Tournament {
    id?: number;
    name: string;
    startDate: Date;
    completionDate: Date;
    hostCountry: Country;
    tournamentType: string;
}
