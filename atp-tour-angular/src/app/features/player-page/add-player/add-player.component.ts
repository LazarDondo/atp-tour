import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Observable } from 'rxjs';
import { CountryService } from 'src/app/core/services/country.service';
import { PlayerService } from 'src/app/core/services/player.service';
import { Country } from 'src/app/models/country.model';
import { map, startWith } from 'rxjs/operators';
import { PlayerEventEmitterService } from 'src/app/core/services/player-event-emitter.service';


/**
 * Represents the add player component
 * 
 * @author Lazar
 */
@Component({
  selector: 'app-add-player',
  templateUrl: './add-player.component.html',
  styleUrls: ['./add-player.component.scss']
})
export class AddPlayerComponent implements OnInit {

  playerForm: FormGroup;
  loading: boolean = false;
  submitted: boolean = false;
  success: boolean = false;
  error: boolean = false;
  validCountry: boolean = false;
  maximumDate: Date;
  myControl = new FormControl();
  countries: Country[];
  filteredCountries: Observable<Country[]>;

  /**
   * @constructor Sets maximum date field to the todays date 16 years ago
   * 
   * @param {PlayerService} playerService 
   * @param {FormBuilder} formBuilder 
   * @param {CountryService} countryService 
   * @param {PlayerEventEmitterService} eventEmitterService 
   */
  constructor(private playerService: PlayerService, private formBuilder: FormBuilder,
    private countryService: CountryService, private eventEmitterService: PlayerEventEmitterService) {
    this.maximumDate = new Date();
    this.maximumDate.setFullYear(this.maximumDate.getFullYear() - 16);
  }

  ngOnInit(): void {
    this.playerForm = this.configureFormFields();
    this.getCountries();
  }

  /**
   * Sets form fields, validates countries and adds player on form submit
   * 
   * @returns If any form field value is invalid
   */
  onSubmit() {
    this.setFormVariables();
    this.validateCountry();
    if (this.playerForm.invalid || !this.validCountry) {
      return;
    }
    this.loading = true;
    this.addPlayer();
  }

  /**
   * Configures form fields
   * 
   * @returns {FormGroup} Form with configured form fields
   */
  private configureFormFields(): FormGroup {
    var form = this.formBuilder.group({
      rank: [],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      birthCountry: [],
      dateOfBirth: ['', Validators.required],
      currentPoints: ['', Validators.required],
      livePoints: []
    });
    return form;
  }

  /**
   * Gets all countries from the database and configures value change pipe
   */
  private getCountries() {
    this.countryService.getCountries().subscribe(countries => {
      this.countries = countries;
      this.filteredCountries = this.myControl.valueChanges.pipe(
        startWith(''),
        map(value => { return this.countryService.filterCountries(value, this.countries) })
      )
    });
  }

  /**
   * Adds player to the database and updates form fields values
   */
  addPlayer() {
    this.playerService.addPlayer(this.playerForm.value).subscribe({
      next: addedPlayer => {
        delete addedPlayer.id;
        this.playerForm.setValue(addedPlayer);
        this.loading = false;
        this.success = true;
      },
      error: () => {
        this.error = true;
        this.loading = false;
      }
    });
  }

  /**
   * Gets player form controls
   */
  get f() {
    return this.playerForm.controls;
  }

  /**
   * Displays country's name
   * 
   * @param {Country} country Country to be displayed
   * 
   * @returns {string} Country's name
   */
  displayFn(country: Country) {
    return country ? country.name : "";
  }

  /**
   * Sets form variables values
   */
  private setFormVariables() {
    this.submitted = true;
    this.error = false;
    this.success = false;
  }

  /**
   * Validates if with the name entered in the birth country autocomplete field exists
   */
  private validateCountry() {
    var birthCountry = this.myControl.value
    this.validCountry = this.countries.filter(c => c == birthCountry || c.name == birthCountry).length > 0;
    if (this.validCountry && typeof (birthCountry) === 'string') {
      birthCountry = this.countries.find(country => country.name == birthCountry);
    }
    this.playerForm.value.birthCountry = birthCountry;
  }

  /**
   * Closes add player dialog
   */
  closeDialog() {
    this.eventEmitterService.closeDialog();
  }
}
