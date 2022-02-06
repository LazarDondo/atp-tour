import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { map, startWith } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { CountryService } from 'src/app/core/services/country.service';
import { TournamentService } from 'src/app/core/services/tournament.service';
import { Country } from 'src/app/models/country.model';
import { TournamentEventEmitterService } from 'src/app/core/services/tournament-event-emitter.service';

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
    private countryService: CountryService, private eventEmitterService: TournamentEventEmitterService) {
    this.futureDate = new Date();
    this.futureDate.setDate(this.futureDate.getDate() + 1);
  }

  ngOnInit(): void {
    this.tournamentForm = this.formBuilder.group({
      id: [],
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

  private _filter(value: string | Country): Country[] {
    const filterValue = (value instanceof Country) ? value.name : value;
    return this.countries.filter(option => {
      return option.name.toLowerCase().includes(filterValue)
    })
  }

  onSubmit() {
    this.submitted = true;
    this.error = false;
    this.success = false;
    this.validateCountry();
    if (this.tournamentForm.invalid || !this.validCountry) {
      return;
    }
    this.loading = true;
    this.addTournament();
  }

  addTournament() {
    this.tournamentService.addTournament(this.tournamentForm.value).subscribe({
      next: addedTournament => {
        this.tournamentForm.setValue(addedTournament);
        this.eventEmitterService.updateTournamentsTable(addedTournament);
        this.loading = false;
        this.success = true;
      },
      error: err => {
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

  closeDialog() {
    this.eventEmitterService.closeDialog();
  }
}
