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

/**
 * Represents the matches page component
 * 
 * @author Lazar
 */
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
  loading: boolean = false;
  submitted: boolean = false;
  success: boolean = false;
  error: boolean = false;
  displayUpdateButton: boolean = false;
  today: Date;
  isAdminUser: boolean;
  dialogRef: MatDialogRef<StatisticsComponent, any>;

  /**
   * @constructor Sets updateMatches to an empty array and today property to the current date
   * 
   * @param {TournamentService} tournamentService 
   * @param {PlayerService} playerService 
   * @param {MatDialog} dialog 
   * @param {MatchesService} matchesService 
   * @param {FormBuilder} formBuilder 
   * @param {AuthService} authService 
   * @param {StatisticsEventEmitterService} eventEmitterService 
   */
  constructor(private tournamentService: TournamentService, private playerService: PlayerService, private dialog: MatDialog,
    private matchesService: MatchesService, private formBuilder: FormBuilder, private authService: AuthService,
    private eventEmitterService: StatisticsEventEmitterService) {
    this.updateMatches = [];
    this.today = new Date();
  }

  ngOnInit(): void {
    this.matchesForm = this.configureFormFields();
    this.getTournaments();
    this.getPlayers();
    this.subsribeToCloseDialogEvent();
    this.filterMatches();
    this.isAdminUser = this.authService.isAdmin();
  }

  /**
   * Validates players and update matches condition, sets form fields variables and filter matches on form submit 
   */
  onSubmit() {
    this.setFormVariables();
    this.prepareFilterValues();
    this.filterMatches();
    this.validateUpdateMatchesCondition();
  }

  /**
   * Configures form fields
   * 
   * @returns {FormGroup} Form with configured form fields
   */
  private configureFormFields(): FormGroup {
    var form = this.formBuilder.group({
      tournament: [],
      firstPlayer: [],
      secondPlayer: [],
    });
    return form;
  }

  /**
   * Displays tournament in autocomplete field
   * 
   * @param {Tournament} tournament Tournament to be displayed
   * 
   * @returns {string} Tournament's name
   */
  displayTournament(tournament: Tournament): string {
    return tournament ? tournament.name : "";
  }

  /**
   * Displays player in autocomplete field
   * 
   * @param {Player} player Player to be displayed
   * 
   * @returns {string} Player's rank, first name and last name
   */
  displayPlayer(player: Player) {
    return player ? '(' + player.rank + ') ' + player.firstName + " " + player.lastName : "";
  }

  /**
   * Opens statistics dialog
   * 
   * @param {Match} match Match for which statistics should be displayed in dialog
   */
  openStatisticsDialog(match: Match) {
    if (match.winner) {
      this.dialogRef = this.dialog.open(StatisticsComponent, {
        width: '600px'
      });
      this.dialogRef.componentInstance.selectedMatch = match;
    }
  }

  /**
   * Updates tournament's matches
   */
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

  /**
   * Checks if match can be updated
   * 
   * @param {Match} match Match that is validated
   * 
   * @returns {boolean}
   *      <ul>
   *         <li>True if the match can be updated</li>
   *         <li>False if the match can't be updated</li>
   *      </ul>    
   */
  allowResultsUpdate(match: Match): boolean {
    return !match.result && this.hasFinished(match.matchDate) && this.isAdminUser && this.displayUpdateButton;
  }

  /**
   * Checks if match has finished
   * 
   * @param {string} date Match date
   * 
   * @returns {boolean}
   *      <ul>
   *         <li>True if the match has finished</li>
   *         <li>False if the match hasn't finished</li>
   *      </ul>  
   */
  hasFinished(date: string): boolean {
    var matchDate = new Date(date);
    return matchDate.getTime() <= this.today.getTime();
  }

  /**
   * Gets all tournaments from the database and configures value change pipe
   */
  private getTournaments() {
    this.tournamentService.getTournaments().subscribe(tournaments => {
      this.tournaments = tournaments;
      this.filteredTournaments = this.tournamentControl.valueChanges.pipe(
        startWith(''),
        map(value => { return this.tournamentService.filterTournaments(value, this.tournaments) })
      )
    });
  }

  /**
   * Gets all players from the database and configures value change pipe
   */
  private getPlayers() {
    this.playerService.getPlayers().subscribe(players => {
      this.players = players;
      this.filteredPlayersFirst = this.configurePlayerControl(this.firstPlayerControl);
      this.filteredPlayersSecond = this.configurePlayerControl(this.secondPlayerControl);
    });
  }

  /**
   * Subsribes to close dialog event
   */
  private subsribeToCloseDialogEvent() {
    this.eventEmitterService.invokeCloseDialogFunction.subscribe(() => {
      this.dialogRef.close();
    });
  }

  /**
   * Filters matches based on tournament's name, first or second player's last name
   */
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

  /**
   * Sets form variables values
   */
  private setFormVariables() {
    this.submitted = true;
    this.error = false;
    this.success = false;
    this.loading = true;
  }

  /**
   * Sets filter field values to null if empty, otherwise sets them to the entered value
   */
  private prepareFilterValues() {
    this.matchesForm.value.tournament = this.tournamentControl.value == "" ? null : this.tournamentControl.value;
    this.matchesForm.value.firstPlayer = this.firstPlayerControl.value == "" ? null : this.firstPlayerControl.value;
    this.matchesForm.value.secondPlayer = this.secondPlayerControl.value == "" ? null : this.secondPlayerControl.value;

  }

  /**
   * Configures value change pipe for players autocomplete fields
   * 
   * @param {FormControl} playerControl Autocomplete form field
   * 
   * @returns {Observable<Player[]>} Filtered players
   */
  private configurePlayerControl(playerControl: FormControl): Observable<Player[]> {
    var players = playerControl.valueChanges.pipe(
      startWith(''),
      map(value => { return this.playerService.filterPlayers(value, this.players) })
    );
    return players;
  }

  /**
   * Validates if matches results can be updated

   */
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
