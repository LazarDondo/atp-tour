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
  countryControl = new FormControl();
  participantControl = new FormControl();
  countries: Country[]
  filteredCountries: Observable<Country[]>
  players: Player[]
  filteredPlayers: Observable<Player[]>
  chosenPlayers: Player[];
  numberOfParticipants: number;

  constructor(private tournamentService: TournamentService, private formBuilder: FormBuilder, private playerService: PlayerService,
    private countryService: CountryService, private eventEmitterService: TournamentEventEmitterService) {
    this.futureDate = new Date();
    this.futureDate.setDate(this.futureDate.getDate() + 1);
    this.chosenPlayers = [];
    this.numberOfParticipants = 0;
  }

  ngOnInit(): void {
    this.tournamentForm = this.configureFormFields();
    this.getCountries();

    this.playerService.getPlayers().subscribe(players => {
      this.players = players;
      this.filteredPlayers = this.participantControl.valueChanges.pipe(
        startWith(''),
        map(value => { return this.playerService.filterPlayers(value, this.players); })
      )
    })
  }

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
    console.log(this.tournamentForm.value);
    this.addTournament();
  }

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

  private getCountries() {
    this.countryService.getCountries().subscribe(countries => {
      this.countries = countries;
      this.filteredCountries = this.countryControl.valueChanges.pipe(
        startWith(''),
        map(value => { return this.countryService.filterCountries(value, this.countries); })
      )
    });
  }

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

  get f() {
    return this.tournamentForm.controls;
  }

  displayFn(country: Country) {
    return country ? country.name : "";
  }

  displayPlayer() {
    return '';
  }

  validateCountry() {
    var hostCountry = this.countryControl.value
    this.validCountry = this.countries.filter(c => c == hostCountry || c.name == hostCountry).length > 0;
    if (this.validCountry && typeof (hostCountry) === 'string') {
      hostCountry = this.countries.find(country => country.name == hostCountry);
    }
    this.tournamentForm.value.hostCountry = hostCountry;
  }

  closeDialog() {
    this.eventEmitterService.closeDialog();
  }

  addParticipant(chosenPlayer: Player) {
    this._addPlayerByRank(this.chosenPlayers, chosenPlayer);
    this._removePlayer(this.players, chosenPlayer);
    this.numberOfParticipants++;
  }

  removeParticipant(chosenPlayer: Player) {
    this._addPlayerByRank(this.players, chosenPlayer);
    this._removePlayer(this.chosenPlayers, chosenPlayer);
    this.numberOfParticipants--;
  }

  private _addPlayerByRank(players: Player[], chosenPlayer: Player) {
    let insertIndex = players.findIndex(p => p.rank! > chosenPlayer.rank!);
    if (insertIndex != -1) {
      players.splice(insertIndex, 0, chosenPlayer);
    }
    else {
      players.push(chosenPlayer);
    }
  }

  private _removePlayer(players: Player[], chosenPlayer: Player) {
    let removeIndex = players.indexOf(chosenPlayer);
    players.splice(removeIndex, 1);
  }
}
