import { Injectable } from "@angular/core";
import { Country } from "./country.model";

@Injectable({
    providedIn: 'root'
})
export class Tournament {
    id?: number;
    name: string;
    startDate: Date;
    completionDate: Date;
    hostCountry: Country;
    tournamentType: string;
}
