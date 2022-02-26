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
import { StatisticsComponent } from '../statistics/statistics.component';
import { MatDialog, MatDialogRef } from '@angular/material/dialog';
import { StatisticsEventEmitterService } from 'src/app/core/services/statistics-event-emitter.service';

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
  isAdminUser: boolean;
  dialogRef: MatDialogRef<StatisticsComponent, any>

  constructor(private tournamentService: TournamentService, private playerService: PlayerService, private dialog: MatDialog,
    private matchesService: MatchesService, private formBuilder: FormBuilder, private authService: AuthService,
    private eventEmitterService: StatisticsEventEmitterService) {
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
    this.getTournaments();
    this.getPlayers();
    this.subsribeToCloseDialogEvent();
    this.filterMatches();
    this.isAdminUser = this.authService.isAdmin();
  }

  onSubmit() {
    this.setFormVariables();
    this.validatePlayer();
    this.filterMatches();
    this.validateUpdateMatchesCondition();
  }

  displayTournament(tournament: Tournament) {
    return tournament ? tournament.name : "";
  }

  displayPlayer(player: Player) {
    return player ? '(' + player.rank + ') ' + player.firstName + " " + player.lastName : "";
  }

  openStatisticsDialog(match: Match) {
    if (match.winner) {
      this.dialogRef = this.dialog.open(StatisticsComponent, {
        width: '600px'
      });
      this.dialogRef.componentInstance.selectedMatch = match;
    }
  }

  updateTournamentMatches() {
    if (this.updateMatches.length === 0) {
      return;
    }
    this.matchesService.updateMatches(this.updateMatches).subscribe({
      next: matches => {
        this.matches = matches;
        this.updateMatches = [];
      }
    });
  }

  hasStarted(date: string): boolean {
    var matchDate = new Date(date);
    return matchDate.getTime() <= this.today.getTime();
  }

  allowResultsUpdate(match: Match) {
    return !match.result && this.hasStarted(match.matchDate) && this.isAdminUser && this.displayUpdateButton;
  }

  private getTournaments() {
    this.tournamentService.getTournaments().subscribe(tournaments => {
      this.tournaments = tournaments;
      this.filteredTournaments = this.tournamentControl.valueChanges.pipe(
        startWith(''),
        map(value => { return this.tournamentService.filterTournaments(value, this.tournaments) })
      )
    });
  }

  private getPlayers() {
    this.playerService.getPlayers().subscribe(players => {
      this.players = players;
      this.filteredPlayersFirst = this.configurePlayerControl(this.firstPlayerControl);
      this.filteredPlayersSecond = this.configurePlayerControl(this.secondPlayerControl);
    });
  }

  private subsribeToCloseDialogEvent() {
    this.eventEmitterService.invokeCloseDialogFunction.subscribe(() => {
      this.dialogRef.close();
    });
  }

  private filterMatches() {
    if (this.matchesForm.value.firstPlayer === this.matchesForm.value.secondPlayer) {
      this.matchesForm.value.secondPlayer = null;
    }
    this.matchesService.filterMatches(this.matchesForm.value).subscribe({
      next: matches => {
        this.matches = matches;
        this.loading = false;
        this.success = true;
      },
      error: () => {
        this.error = true;
        this.loading = false;
      }
    });
    this.updateMatches = [];
  }

  private setFormVariables() {
    this.submitted = true;
    this.error = false;
    this.success = false;
    this.loading = true;
  }

  private validatePlayer() {
    this.matchesForm.value.tournament = this.tournamentControl.value == "" ? null : this.tournamentControl.value;
    this.matchesForm.value.firstPlayer = this.firstPlayerControl.value == "" ? null : this.firstPlayerControl.value;
    this.matchesForm.value.secondPlayer = this.secondPlayerControl.value == "" ? null : this.secondPlayerControl.value;

  }

  private configurePlayerControl(playerControl: FormControl): Observable<Player[]> {
    var players = playerControl.valueChanges.pipe(
      startWith(''),
      map(value => { return this.playerService.filterPlayers(value, this.players) })
    );
    return players;
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



  handleResultsUpdate(event: any, rowIndex: number) {
    var match = this.matches[rowIndex];
    var result = event.target.value;
    if (result) {
      match.result = event.target.value;
      match.winner = result.split("-")[0] === '3' ? match.firstPlayer : match.secondPlayer;
      this.updateMatches.push(match);
      this.matchesService.addNewMatch(match, rowIndex, this.matches, this.updateMatches);
    }
  }
}
