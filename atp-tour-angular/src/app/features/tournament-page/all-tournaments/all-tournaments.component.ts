import { Component, OnInit } from '@angular/core';
import { TournamentService } from 'src/app/core/services/tournament.service';
import { Tournament } from 'src/app/models/tournament.model';

@Component({
  selector: 'app-all-tournaments',
  templateUrl: './all-tournaments.component.html',
  styleUrls: ['./all-tournaments.component.scss']
})
export class AllTournamentsComponent implements OnInit {

  tournaments: Tournament[];
  searchTournaments: Tournament[];
  columns: string[] = ['name', 'startDate', 'completitionDate', 'hostCountry', 'tournamentType'];
  tournamentName: string;
  pageNumber: number = 1;
  tournamentsPerPage: number = 7;
 

  constructor(private tournamentService: TournamentService) {
    this.tournamentService.getTournaments().subscribe(tournaments =>
       { this.tournaments=tournaments;this.searchTournaments = tournaments });
  }

  ngOnInit(): void {
  }

  search() {
    if ( this.tournamentName=='') {
      this.tournaments=this.searchTournaments;
      this.ngOnInit();
    }
    else {
      this.tournamentName = this.tournamentName.replace(/\s\s+/g, ' ');
      this.tournaments = this.searchTournaments.filter(res => {
        return (res.name).toLowerCase().match(this.tournamentName.toLowerCase());
      });
      this.pageNumber=1;
    }
  }

  key: string = 'rank';
  reverse: boolean = false;
  sort(key: string) {
    this.key = key;
    this.reverse = !this.reverse;
  }

}
