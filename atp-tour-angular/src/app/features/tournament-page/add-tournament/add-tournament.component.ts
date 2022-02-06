import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { map, startWith } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { CountryService } from 'src/app/core/services/country.service';
import { TournamentService } from 'src/app/core/services/tournament.service';
import { Country } from 'src/app/models/country.model';

@Component({
  selector: 'app-add-tournament',
  templateUrl: './add-tournament.component.html',
  styleUrls: ['./add-tournament.component.scss']
})
export class AddTournamentComponent implements OnInit {

  tournamentForm: FormGroup;
  loading = false;
  submitted = false;
  success = false;
  error = false;
  validCountry = false;
  futureDate: Date
  myControl = new FormControl();
  countries: Country[]
  filteredCountries: Observable<Country[]>

  constructor(private tournamentService: TournamentService, private formBuilder: FormBuilder,
    private countryService: CountryService) {
    this.futureDate = new Date();
    this.futureDate.setDate(this.futureDate.getDate()+1);
  }

  ngOnInit(): void {
    this.tournamentForm = this.formBuilder.group({
      name: ['', Validators.required],
      startDate: ['', Validators.required],
      completitionDate: [],
      hostCountry: [],
      tournamentType: ['', Validators.required],
    });
    this.myControl.addValidators
    this.countryService.getCountries().subscribe(countries => {
      this.countries = countries;
      this.filteredCountries = this.myControl.valueChanges.pipe(
        startWith(''),
        map(value => { return this._filter(value) })
      )
    });
  }

  private _filter(value: string): Country[] {
    const filterValue = value.toLowerCase()
    return this.countries.filter(option => {
      return option.name.toLowerCase().includes(filterValue)
    })
  }

  onSubmit() {
    this.submitted = true;
    this.validateCountry();
    console.log(this.tournamentForm.value);
    if (this.tournamentForm.invalid || !this.validCountry) {
      console.log('error'); 
      return;
    }
    this.loading = true;
    this.addTournament();
  }

  addTournament() {
    console.log(1234);
    this.tournamentService.addTournament(this.tournamentForm.value).subscribe({
      next: addedTournament => {
        delete addedTournament.id;
        this.tournamentForm.setValue(addedTournament);
        this.error = false;
        this.loading = false;
        this.success = true;
      },
      error: err => {
        this.success=false;
        this.error = true;
        this.loading = false;
      }
    });
  }

  get f() {
    return this.tournamentForm.controls;
  }

  displayFn(country: Country) {
    return country ? country.name : "";
  }

  validateCountry() {
    var hostCountry = this.myControl.value
    this.validCountry = this.countries.filter(c => c == hostCountry || c.name == hostCountry).length > 0;
    if (this.validCountry && typeof (hostCountry) === 'string') {
      hostCountry = this.countries.find(country => country.name == hostCountry);
    }
    this.tournamentForm.value.hostCountry = hostCountry;
  }
}
