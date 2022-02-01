import { Injectable } from "@angular/core";
import { Country } from "./country.model";

@Injectable({
    providedIn: 'root'
})
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
