import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TournamentEventEmitterService } from 'src/app/core/services/tournament-event-emitter.service';
import { TournamentService } from 'src/app/core/services/tournament.service';
import { Tournament } from 'src/app/models/tournament.model';

@Component({
  selector: 'app-update-tournament',
  templateUrl: './update-tournament.component.html',
  styleUrls: ['./update-tournament.component.scss']
})
export class UpdateTournamentComponent implements OnInit {
  @Input() selectedTournament: Tournament;

  tournamentForm: FormGroup;
  loading = false;
  submitted = false;
  success = false;
  error = false;
  tournamentStarted: boolean;
  validCountry = false;
  today: Date;
  futureDate: Date
  myControl = new FormControl();

  constructor(private tournamentService: TournamentService, private formBuilder: FormBuilder,
    private eventEmitterService: TournamentEventEmitterService) {
    this.today = new Date();
    this.futureDate = this.today;
    this.futureDate.setDate(this.today.getDate() + 1);
    this.subscribeToDisplayTournamentEvent();
  }

  ngOnInit(): void {
    this.tournamentForm = this.formBuilder.group({
      id: [],
      name: ['', Validators.required],
      startDate: ['', Validators.required],
      completitionDate: [],
      hostCountry: [],
      tournamentType: ['', Validators.required],
    });
    this.myControl.addValidators;
    this.tournamentForm.setValue(this.selectedTournament);
  }


  onSubmit() {
    this.submitted = true;
    this.error = false;
    this.success = false;
    if (this.tournamentForm.invalid) {
      return;
    }
    this.loading = true;
    this.updateTournament();
  }

  updateTournament() {
    this.tournamentService.updateTournament(this.tournamentForm.value).subscribe({
      next: updatedTournament => {       
        this.selectedTournament=updatedTournament;
        this.tournamentForm.setValue(updatedTournament);
        this.eventEmitterService.updateTournamentsTable(updatedTournament);
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

  subscribeToDisplayTournamentEvent() {
    this.eventEmitterService.
      invokeDisplayTournamentFunction.subscribe((tournament) => {
        this.selectedTournament = tournament;
        this.tournamentStarted = this.today >= tournament.startDate;
        this.tournamentForm.setValue(tournament);
      });
  }

}
