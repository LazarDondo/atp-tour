import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup } from '@angular/forms';
import { map, startWith } from 'rxjs/operators';
import { Observable } from 'rxjs';
import { PlayerService } from 'src/app/core/services/player.service';
import { TournamentService } from 'src/app/core/services/tournament.service';
import { Player } from 'src/app/models/player.model';
import { Tournament } from 'src/app/models/tournament.model';
import { Match } from 'src/app/models/match.model';
import { MatchesService } from 'src/app/core/services/matches.service';

@Component({
  selector: 'app-matches',
  templateUrl: './matches.component.html',
  styleUrls: ['./matches.component.scss']
})
export class MatchesComponent implements OnInit {
  @Input() matches: Match[];
  matchesForm: FormGroup;
  tournaments: Tournament[];
  filteredTournaments: Observable<Tournament[]>;
  players: Player[];
  filteredPlayersFirst: Observable<Player[]>;
  filteredPlayersSecond: Observable<Player[]>;
  playerName: string;
  pageNumber: number = 1;
  matchesPerPage: number = 7;
  tournamentControl = new FormControl();
  firstPlayerControl = new FormControl();
  secondPlayerControl = new FormControl();
  loading = false;
  submitted = false;
  success = false;
  error = false;


  constructor(private tournamentService: TournamentService, private playerService: PlayerService,
    private matchesService: MatchesService, private formBuilder: FormBuilder) {
  }

  ngOnInit(): void {
    this.matchesForm = this.formBuilder.group({
      tournament: [],
      firstPlayer: [],
      secondPlayer: [],
    });

    this.tournamentService.getTournaments().subscribe(tournaments => {
      this.tournaments = tournaments;
      this.filteredTournaments = this.tournamentControl.valueChanges.pipe(
        startWith(''),
        map(value => { return this._filterTournaments(value) })
      )
    });

    this.playerService.getPlayers().subscribe(players => {
      this.players = players;
      this.filteredPlayersFirst = this.firstPlayerControl.valueChanges.pipe(
        startWith(''),
        map(value => { return this._filterFirstPlayer(value) })
      );
      this.filteredPlayersSecond = this.secondPlayerControl.valueChanges.pipe(
        startWith(''),
        map(value => {
          return this._filterFirstPlayer(value)
        })
      )
    });

  }

  private _filterTournaments(value: string | Tournament): Tournament[] {
    const filterValue = (value instanceof Tournament) ? value.name : value;
    return this.tournaments.filter(option => {
      return option.name.toLowerCase().includes(filterValue);
    })
  }

  private _filterFirstPlayer(value: string | Player): Player[] {
    const filterValue = (value instanceof Player) ? value.lastName : value;
    return this.players.filter(option => {
      return option.lastName.toLowerCase().includes(filterValue)
    })
  }



  onSubmit() {
    this.submitted = true;
    this.error = false;
    this.success = false;

    this.loading = true;
    this._validatePlayer();
    this._filterMatches();
  }

  private _filterMatches() {
    this.matchesService.filterMatches(this.matchesForm.value).subscribe({
      next: matches => {
        this.matches = matches;
        this.loading = false;
        this.success = true;
      },
      error: err => {
        this.error = true;
        this.loading = false;
      }
    });

  }


  displayTournament(tournament: Tournament) {
    return tournament ? tournament.name : "";
  }

  displayPlayer(player: Player) {
    return player ? player.firstName + " " + player.lastName : "";
  }



  key: string = 'tournament';
  reverse: boolean = false;
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }

  displayStatistics(match: Match) {

  }

  private _validatePlayer() {
    this.matchesForm.value.tournament = this.tournamentControl.value==""? null : this.tournamentControl.value;
    this.matchesForm.value.firstPlayer = this.firstPlayerControl.value==""? null : this.firstPlayerControl.value;
    this.matchesForm.value.secondPlayer = this.secondPlayerControl.value==""? null : this.secondPlayerControl.value;

  }


}
