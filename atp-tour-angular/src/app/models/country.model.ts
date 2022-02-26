import { Injectable } from "@angular/core";

@Injectable({
    providedIn: 'root'
})
export class Country {
    id?: number;
    name: string;
    codeName: string;
}
