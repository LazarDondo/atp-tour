import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Country } from 'src/app/models/country.model';
import { environment } from 'src/environments/environment';

/**
 * Service for country data management
 * 
 * @author Lazar
 */
@Injectable({
  providedIn: 'root'
})
export class CountryService {

  private API_URL: string = environment.API_URL;

  /**
   * @constructor
   * 
   * @param {HttpClient} httpClient 
   */
  constructor(private httpClient: HttpClient) { }

  /**
   * Gets countries from the database
   * 
   * @returns {Observable<Country[]>} Array of all countries from the database
    */
  public getCountries(): Observable<Country[]> {
    return this.httpClient.get<Country[]>(this.API_URL + "/country");
  }

  /**
   * Filters countries based on the country name
   * 
   * @param {string} value Filter value
   * @param {Country[]} countries Countries for filtering
   * 
   * @returns {Country[]} Filtered countries
   */
  public filterCountries(value: string | Country, countries: Country[]): Country[] {
    const filterValue = (value instanceof Country) ? value.name : value;
    return countries.filter(option => {
      return option.name.toLowerCase().includes(filterValue)
    })
  }
}
