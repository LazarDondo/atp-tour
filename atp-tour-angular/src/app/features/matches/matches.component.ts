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
import { AuthService } from 'src/app/core/services/auth.service';

@Component({
  selector: 'app-matches',
  templateUrl: './matches.component.html',
  styleUrls: ['./matches.component.scss']
})
export class MatchesComponent implements OnInit {
  matches: Match[];
  updateMatches: Match[];
  matchesForm: FormGroup;
  tournaments: Tournament[];
  filteredTournaments: Observable<Tournament[]>;
  players: Player[];
  filteredPlayersFirst: Observable<Player[]>;
  filteredPlayersSecond: Observable<Player[]>;
  playerName: string;
  pageNumber: number = 1;
  matchesPerPage: number = 8;
  tournamentControl = new FormControl();
  firstPlayerControl = new FormControl();
  secondPlayerControl = new FormControl();
  loading = false;
  submitted = false;
  success = false;
  error = false;
  displayUpdateButton = false;
  today: Date;
  isAdminUser : boolean;


  constructor(private tournamentService: TournamentService, private playerService: PlayerService,
    private matchesService: MatchesService, private formBuilder: FormBuilder, private authService: AuthService) {
    this.updateMatches = [];
    this.today = new Date();
  }

  ngOnInit(): void {
    this.matchesForm = this.formBuilder.group({
      tournament: [],
      firstPlayer: [],
      secondPlayer: [],
    }
    );

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
    this._filterMatches();
    this.isAdminUser = this.authService.isAdmin(); 
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
    this.validateUpdateMatchesCondition();
  }

  private _filterMatches() {

    if (this.matchesForm.value.firstPlayer === this.matchesForm.value.secondPlayer) {
      this.matchesForm.value.secondPlayer = null;
    }

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

    this.updateMatches = [];

  }


  displayTournament(tournament: Tournament) {
    return tournament ? tournament.name : "";
  }

  displayPlayer(player: Player) {
    return player ? '('+player.rank+') '+ player.firstName + " " + player.lastName : "";
  }



  key: string = 'matchDate';
  reverse: boolean = false;
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }

  displayStatistics(match: Match) {
    console.log(312321);
  }

  private _validatePlayer() {
    this.matchesForm.value.tournament = this.tournamentControl.value == "" ? null : this.tournamentControl.value;
    this.matchesForm.value.firstPlayer = this.firstPlayerControl.value == "" ? null : this.firstPlayerControl.value;
    this.matchesForm.value.secondPlayer = this.secondPlayerControl.value == "" ? null : this.secondPlayerControl.value;

  }

  handleResultsUpdate(event: any, rowIndex: number) {
    console.log(rowIndex);
    var match = this.matches[rowIndex];
    var result = event.target.value;
    if (result) {
      match.result = event.target.value;
      match.winner = result.split("-")[0] === '3' ? match.firstPlayer : match.secondPlayer;
      this.updateMatches.push(match);
      this.addNewMatch(match, rowIndex);
    }
    console.log(this.updateMatches)
  }

  private addNewMatch(match: Match, rowIndex: number) {
    if (match.round === "finals") {
      return;
    }
    var nextOpponent = rowIndex % 2 === 0 ? this.matches[rowIndex + 1].winner : this.matches[rowIndex - 1].winner
    if (!nextOpponent) {
      return;
    }

    var nextRound = this.getNextRound(match.round);
    var nextDate = this.getNextDate(match);
    var newMatch: Match = {
      tournament: match.tournament, firstPlayer: match.winner!,
      secondPlayer: nextOpponent, matchDate: nextDate, round: nextRound
    };
    this.updateMatches.push(newMatch)
  }

  private getNextRound(round: string): string {
    switch (round) {
      case 'eights-finals':
        return 'quater-finals';
      case 'quater-finals':
        return 'semi-finals';
      case 'semi-finals':
        return 'finals';
      default:
        return '';
    }
  }

  private getNextDate(match: Match): string {
    if (match.round === "finals") {
      console.log(match.tournament.completitionDate.toDateString);
    }
    var date = new Date(match.matchDate);
    date.setDate(date.getDate() + 2);
    return date.toISOString().split('T')[0];
  }

  private validateUpdateMatchesCondition() {
    if (!this.isAdminUser || !this.tournamentControl.value || !this.tournamentControl.value.startDate
      || this.firstPlayerControl.value || this.secondPlayerControl.value) {
      this.displayUpdateButton = false;
      return;
    }
    var startDate = new Date(this.tournamentControl.value.startDate);
    this.displayUpdateButton = startDate.getTime() <= this.today.getTime();
  }

  updateTournamentMatches() {
    if (this.updateMatches.length === 0) {
      return;
    }
    this.matchesService.updateMatches(this.updateMatches).subscribe({
      next: matches => {
        this.matches = matches;
        this.updateMatches = [];
      },
      error: err => {
      }
    });
  }

  hasStarted(date: Date): boolean {
    var matchDate = new Date(date);
    return matchDate.getTime() <= this.today.getTime();
  }


}
