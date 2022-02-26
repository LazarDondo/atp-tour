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
  @Input() isAdminUser: boolean;

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
    this.tournamentForm = this.configureFormFields();
    this.displayTournament(this.selectedTournament);
  }


  onSubmit() {
    this.setFormVariables();
    if (this.tournamentForm.invalid) {
      return;
    }
    this.loading = true;
    this.updateTournament();
  }

  private configureFormFields(): FormGroup{
    var form = this.formBuilder.group({
      id: [],
      name: ['', Validators.required],
      startDate: ['', Validators.required],
      completitionDate: [],
      hostCountry: [],
      tournamentType: ['', Validators.required],
    });
    return form;
  }

  private setFormVariables(){
    this.submitted = true;
    this.error = false;
    this.success = false;
  }

  updateTournament() {
    this.selectedTournament.name = this.tournamentForm.value.name;
    this.selectedTournament.startDate = this.tournamentForm.value.startDate;
    this.tournamentService.updateTournament(this.selectedTournament).subscribe({
      next: updatedTournament => {
        this.selectedTournament = updatedTournament;
        this.displayTournament(updatedTournament);
        this.eventEmitterService.updateTournamentsTable(updatedTournament);
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

  subscribeToDisplayTournamentEvent() {
    this.eventEmitterService.invokeDisplayTournamentFunction.subscribe((tournament) => {
        this.selectedTournament = tournament;
        this.tournamentStarted = this.today >= tournament.startDate;
        this.displayTournament(tournament);
      });
  }

  deleteTournament() {
    this.tournamentService.deleteTournament(this.tournamentForm.value.id).subscribe();
    location.reload();
  }

  private displayTournament(tournament: Tournament) {
    this.tournamentForm.setValue(tournament);
    this.tournamentForm.controls['hostCountry'].setValue(tournament.hostCountry.name);
  }
}
