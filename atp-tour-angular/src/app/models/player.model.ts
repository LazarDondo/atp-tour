import { Country } from "./country.model";

export class Player {
    id?: number;
    firstName: string;
    lastName: string;
    birthCountry: Country;
    dateOfBirth: Date;
    currentPoints: number;
    livePoints?: number;
    rank?: number;
}
