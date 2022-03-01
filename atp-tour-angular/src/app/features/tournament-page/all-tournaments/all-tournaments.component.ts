import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { TournamentEventEmitterService } from 'src/app/core/services/tournament-event-emitter.service';
import { TournamentService } from 'src/app/core/services/tournament.service';
import { Tournament } from 'src/app/models/tournament.model';

/**
 * Represents the all tournaments component
 * 
 * @author Lazar
 */
@Component({
  selector: 'app-all-tournaments',
  templateUrl: './all-tournaments.component.html',
  styleUrls: ['./all-tournaments.component.scss']
})
export class AllTournamentsComponent implements OnInit {

  @Output() selectedTournament = new EventEmitter<Tournament>();
  tournaments: Tournament[];
  searchTournaments: Tournament[];
  columns: string[] = ['name', 'startDate', 'completionDate', 'hostCountry', 'tournamentType'];
  tournamentName: string;
  pageNumber: number = 1;
  tournamentsPerPage: number = 7;
  key: string = 'rank';
  reverse: boolean = false;

  /**
   * @constructor Subsribes to update tournaments table event
   * 
   * @param {TournamentService} tournamentService 
   * @param {TournamentEventEmitterService} eventEmitterService 
   */
  constructor(private tournamentService: TournamentService, private eventEmitterService: TournamentEventEmitterService) {
    this.tournamentService.getTournaments().subscribe(tournaments => { this.tournaments = tournaments; this.searchTournaments = tournaments });
    this.subscribeToUpdateTournamentsTableEvent();
  }

  ngOnInit(): void {
  }

  /**
   * Searches for the tournament by name
   */
  search() {
    if (this.tournamentName == '') {
      this.tournaments = this.searchTournaments;
      this.ngOnInit();
    }
    else {
      this.tournamentName = this.tournamentName.replace(/\s\s+/g, ' ');
      this.tournaments = this.searchTournaments.filter(res => {
        return (res.name).toLowerCase().match(this.tournamentName.toLowerCase());
      });
      this.pageNumber = 1;
    }
  }

  /**
   * Sorts table by columns
   * 
   * @param {string} key sort key
   */
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }

  /**
   * Displays selected tournament
   * 
   * @param {Tournament} tournament Selected tournament
   */
  displayTournament(tournament: Tournament) {
    this.selectedTournament.emit(tournament);
  }

  /**
   * Subsribes to update tournaments table event. Updates existing tournament in the table or adds new tournament to the table
   *    
   */
  subscribeToUpdateTournamentsTableEvent() {
    this.eventEmitterService.invokeUpdateTournamentsTableFunction.subscribe((tournament) => {
        let index = this.tournaments.findIndex(t => tournament.id === t.id)
        index === -1 ? this.tournaments.push(tournament) : this.tournaments[index] = tournament;
        this.ngOnInit();
      });
  }

}
