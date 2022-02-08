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
  myControl = new FormControl();
  countries: Country[]
  filteredCountries: Observable<Country[]>
  players: Player[]
  filteredPlayers: Observable<Player[]>
  chosenPlayers: Player[];
  numberOfParticipants: number;

  constructor(private tournamentService: TournamentService, private formBuilder: FormBuilder, private playerService:PlayerService,
    private countryService: CountryService, private eventEmitterService: TournamentEventEmitterService) {
    this.futureDate = new Date();
    this.futureDate.setDate(this.futureDate.getDate() + 1);
    this.chosenPlayers=[];
    this.numberOfParticipants=0;
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

    this.playerService.getPlayers().subscribe(players=>{
      this.players=players;
      this.filteredPlayers = this.myControl.valueChanges.pipe(
        startWith(''),
        map(value=>{return this._playerFilter(value)})
      )
    })
  }

  private _filter(value: string | Country): Country[] {
    const filterValue = (value instanceof Country) ? value.name : value;
    return this.countries.filter(option => {
      return option.name.toLowerCase().includes(filterValue)
    })
  }

  private _playerFilter(value: string | Player):Player[]{
    const filterValue = (value instanceof Player) ? value.lastName : value;
    return this.players.filter(option=>{
      return option.lastName.toLowerCase().includes(filterValue)
  })
} 

  onSubmit() {
    this.submitted = true;
    this.error = false;
    this.success = false;
    this.validateCountry();
    if (this.tournamentForm.invalid || !this.validCountry ||this.numberOfParticipants!==16) {
      return;
    }
    this.loading = true;
    this.tournamentForm.value.participants = this.chosenPlayers;
    console.log(this.tournamentForm.value);
    this.addTournament();
  }

  addTournament() {
    this.tournamentService.addTournament(this.tournamentForm.value).subscribe({
      next: addedTournament => { 
        this.eventEmitterService.updateTournamentsTable(addedTournament);
        delete addedTournament.id;
        console.log(addedTournament.id);
        this.tournamentForm.setValue(addedTournament);
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

  displayPlayer() {
    startWith('');
    return '';
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

  addParticipant(player:Player){
    this.chosenPlayers.push(player);
    let index = this.players.indexOf(player);
    this.players.splice(index,1);
    this.numberOfParticipants++;

  }

  removeParticipant(player:Player){
    this.players.push(player);
    let index = this.chosenPlayers.indexOf(player);
    this.chosenPlayers.splice(index,1);
    this.numberOfParticipants--;
  }
}
