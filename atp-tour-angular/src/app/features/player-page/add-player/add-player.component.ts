import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { CountryService } from 'src/app/core/services/country.service';
import { PlayerService } from 'src/app/core/services/player.service';
import { Country } from 'src/app/models/country.model';
import { map, startWith } from 'rxjs/operators';

@Component({
  selector: 'app-add-player',
  templateUrl: './add-player.component.html',
  styleUrls: ['./add-player.component.scss']
})
export class AddPlayerComponent implements OnInit {

  playerForm: FormGroup;
  loading = false;
  submitted = false;
  success = false;
  error = false;
  validCountry = false;
  returnUrl: string;
  maximumDate: Date
  myControl = new FormControl();
  countries: Country[]
  filteredCountries: Observable<Country[]>

  constructor(private playerService: PlayerService, private formBuilder: FormBuilder,
    private countryService: CountryService) {
    this.maximumDate = new Date();
    this.maximumDate.setFullYear(this.maximumDate.getFullYear() - 16);
  }

  ngOnInit(): void {
    this.playerForm = this.formBuilder.group({
      id:[],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      birthCountry: [],
      dateOfBirth: ['', Validators.required],
      currentPoints: ['', Validators.required],
      livePoints: ['', Validators.required],
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
    console.log(this.playerForm.value);
    if (this.playerForm.invalid || !this.validCountry) {
      return;
    }
    this.loading = true;
    this.addPlayer();
  }

  addPlayer() {
    this.playerService.addPlayer(this.playerForm.value).subscribe({
      next: addedPlayer => {
        this.playerForm.setValue(addedPlayer);
        this.error = false;
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
    return this.playerForm.controls;
  }

  displayFn(country: Country) {
    return country ? country.name : "";
  }

  validateCountry() {
    var birthCountry = this.myControl.value
    this.validCountry = this.countries.filter(c => c == birthCountry || c.name == birthCountry).length > 0;
    if (this.validCountry && typeof (birthCountry) === 'string') {
      birthCountry = this.countries.find(country => country.name == birthCountry);
    }
    this.playerForm.value.birthCountry = birthCountry;
  }
}