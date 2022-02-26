import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Country } from 'src/app/models/country.model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CountryService {
  private API_URL = environment.API_URL;
    
  constructor(private httpClient:HttpClient) {}

  public getCountries() : Observable<Country[]>{
    return this.httpClient.get<Country[]>(this.API_URL+"/country");
  }

  public filterCountries(value: string | Country, countries: Country[]): Country[] {
    const filterValue = (value instanceof Country) ? value.name : value;
    return countries.filter(option => {
      return option.name.toLowerCase().includes(filterValue)
    })
  }
}
