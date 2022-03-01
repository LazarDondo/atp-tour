import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { map, startWith } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { CountryService } from 'src/app/core/services/country.service';
import { TournamentService } from 'src/app/core/services/tournament.service';
import { Country } from 'src/app/models/country.model';
import { TournamentEventEmitterService } from 'src/app/core/services/tournament-event-emitter.service';
import { PlayerService } from 'src/app/core/services/player.service';
import { Player } from 'src/app/models/player.model';

/**
 * Represents the add tournament component
 * 
 * @author Lazar
 */
@Component({
  selector: 'app-add-tournament',
  templateUrl: './add-tournament.component.html',
  styleUrls: ['./add-tournament.component.scss']
})
export class AddTournamentComponent implements OnInit {

  tournamentForm: FormGroup;
  loading: boolean = false;
  submitted: boolean = false;
  success: boolean = false;
  error: boolean = false;
  validCountry: boolean = false;
  futureDate: Date;
  countryControl = new FormControl();
  participantControl = new FormControl();
  countries: Country[];
  filteredCountries: Observable<Country[]>;
  players: Player[];
  filteredPlayers: Observable<Player[]>;
  chosenPlayers: Player[];
  numberOfParticipants: number;

  /**
   * @constructor Sets future date value to tomorow
   * 
   * @param {TournamentService} tournamentService 
   * @param {FormBuilder} formBuilder 
   * @param {PlayerService} playerService 
   * @param {CountryService} countryService 
   * @param {TournamentEventEmitterService} eventEmitterService 
   */
  constructor(private tournamentService: TournamentService, private formBuilder: FormBuilder, private playerService: PlayerService,
    private countryService: CountryService, private eventEmitterService: TournamentEventEmitterService) {
    this.futureDate = new Date();
    this.futureDate.setDate(this.futureDate.getDate() + 1);
    this.chosenPlayers = [];
    this.numberOfParticipants = 0;
  }

  /**
   * Configures form fields, gets countries and players
   */
  ngOnInit(): void {
    this.tournamentForm = this.configureFormFields();
    this.getCountries();
    this.getPlayers();
  }

  /**
   * Validates country and adds new tournament
   * 
   * @returns If any form field value is invalid
   */
  onSubmit() {
    this.submitted = true;
    this.error = false;
    this.success = false;
    this.validateCountry();
    if (this.tournamentForm.invalid || !this.validCountry || this.numberOfParticipants !== 16) {
      return;
    }
    this.loading = true;
    this.tournamentForm.value.participants = this.chosenPlayers;
    this.addTournament();
  }

  /**
   * Gets players from the database
   */
  private getPlayers() {
    this.playerService.getPlayers().subscribe(players => {
      this.players = players;
      this.filteredPlayers = this.participantControl.valueChanges.pipe(
        startWith(''),
        map(value => { return this.playerService.filterPlayers(value, this.players); })
      )
    });
  }

  /**
   * Configures form fields
   * 
   * @returns {FormGroup} Form with configured form fields
   */
  private configureFormFields(): FormGroup {
    var form = this.formBuilder.group({
      name: ['', Validators.required],
      startDate: ['', Validators.required],
      completionDate: [],
      hostCountry: [],
      tournamentType: ['', Validators.required],
    });
    return form;
  }

  /**
   * Gets all countries from the database and configures value change pipe
   */
  private getCountries() {
    this.countryService.getCountries().subscribe(countries => {
      this.countries = countries;
      this.filteredCountries = this.countryControl.valueChanges.pipe(
        startWith(''),
        map(value => { return this.countryService.filterCountries(value, this.countries); })
      )
    });
  }

  /**
   * Adds new tournament to the database and updates form field values
   */
  addTournament() {
    this.tournamentService.addTournament(this.tournamentForm.value).subscribe({
      next: addedTournament => {
        this.eventEmitterService.updateTournamentsTable(addedTournament);
        delete addedTournament.id;
        delete addedTournament.id;
        this.tournamentForm.setValue(addedTournament);
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
   * Gets tournament form controls
   */
  get f() {
    return this.tournamentForm.controls;
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
   * Display's player
   * 
   * @returns {string}
   */
  displayPlayer() {
    return '';
  }

  /**
   * Validates if with the name entered in the birth country autocomplete field exists
   */
  validateCountry() {
    var hostCountry = this.countryControl.value
    this.validCountry = this.countries.filter(c => c == hostCountry || c.name == hostCountry).length > 0;
    if (this.validCountry && typeof (hostCountry) === 'string') {
      hostCountry = this.countries.find(country => country.name == hostCountry);
    }
    this.tournamentForm.value.hostCountry = hostCountry;
  }

  /**
   * Closes add tournament dialog
   */
  closeDialog() {
    this.eventEmitterService.closeDialog();
  }

  /**
   * Adds new participant
   * 
   * @param {Player} chosenPlayer Player to be added to participants list
   */
  addParticipant(chosenPlayer: Player) {
    this.addPlayerByRank(this.chosenPlayers, chosenPlayer);
    this.removePlayer(this.players, chosenPlayer);
    this.numberOfParticipants++;
  }

  /**
   * Removes participant
   * 
   * @param chosenPlayer Player to be removed from participants list
   */
  removeParticipant(chosenPlayer: Player) {
    this.addPlayerByRank(this.players, chosenPlayer);
    this.removePlayer(this.chosenPlayers, chosenPlayer);
    this.numberOfParticipants--;
  }

  /**
   * Adds player to the list to the appropriate position in the array so that players are sorted ascending by rank
   * 
   * @param {Player[]} players Array of the participants
   * @param {Player} chosenPlayer Player to be added
   */
  private addPlayerByRank(players: Player[], chosenPlayer: Player) {
    let insertIndex = players.findIndex(p => p.rank! > chosenPlayer.rank!);
    if (insertIndex != -1) {
      players.splice(insertIndex, 0, chosenPlayer);
    }
    else {
      players.push(chosenPlayer);
    }
  }

  /**
   * Removes player from the participants array
   * 
   * @param {Player[]} players Array of the participants
   * @param {Player} chosenPlayer Player to be removed
   */
  private removePlayer(players: Player[], chosenPlayer: Player) {
    let removeIndex = players.indexOf(chosenPlayer);
    players.splice(removeIndex, 1);
  }
}
