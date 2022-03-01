import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TournamentEventEmitterService } from 'src/app/core/services/tournament-event-emitter.service';
import { TournamentService } from 'src/app/core/services/tournament.service';
import { Tournament } from 'src/app/models/tournament.model';

/**
 * Represents the update tournament component
 * 
 * @author Lazar
 */
@Component({
  selector: 'app-update-tournament',
  templateUrl: './update-tournament.component.html',
  styleUrls: ['./update-tournament.component.scss']
})
export class UpdateTournamentComponent implements OnInit {

  @Input() selectedTournament: Tournament;
  @Input() isAdminUser: boolean;
  tournamentForm: FormGroup;
  loading: boolean = false;
  submitted: boolean = false;
  success: boolean = false;
  error: boolean = false;
  tournamentStarted: boolean;
  validCountry: boolean = false;
  today: Date;
  futureDate: Date
  myControl = new FormControl();

  /**
   * @constructor Sets future's data value to tomorow and subscribes to display tournament event
   * 
   * @param {TournamentService} tournamentService 
   * @param {FormBuilder} formBuilder 
   * @param {TournamentEventEmitterService} eventEmitterService 
   */
  constructor(private tournamentService: TournamentService, private formBuilder: FormBuilder,
    private eventEmitterService: TournamentEventEmitterService) {
    this.today = new Date();
    this.futureDate = this.today;
    this.futureDate.setDate(this.today.getDate() + 1);
    this.subscribeToDisplayTournamentEvent();
  }

  /**
   * Configures form fields and displays selected tournament
   */
  ngOnInit(): void {
    this.tournamentForm = this.configureFormFields();
    this.displayTournament(this.selectedTournament);
  }

  /**
   * Sets form variables and updates tournament on form submit
   * 
   * @returns If any form field value is invalid
   */
  onSubmit() {
    this.setFormVariables();
    if (this.tournamentForm.invalid) {
      return;
    }
    this.loading = true;
    this.updateTournament();
  }

  /**
   * Configures form fields
   * 
   * @returns {FormGroup} Form with configured form fields
   */
  private configureFormFields(): FormGroup {
    var form = this.formBuilder.group({
      id: [],
      name: ['', Validators.required],
      startDate: ['', Validators.required],
      completionDate: [],
      hostCountry: [],
      tournamentType: ['', Validators.required],
    });
    return form;
  }

  /**
   * Sets form field variables
   */
  private setFormVariables() {
    this.submitted = true;
    this.error = false;
    this.success = false;
  }

  /**
   * Updates tournament and displays updated tournament
   */
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

  /**
   * Gets tournament form controls
   */
  get f() {
    return this.tournamentForm.controls;
  }

  /**
   * Subsribes to display tournament event. Displays selected tournament's data
   */
  subscribeToDisplayTournamentEvent() {
    this.eventEmitterService.invokeDisplayTournamentFunction.subscribe((tournament) => {
      this.selectedTournament = tournament;
      this.tournamentStarted = this.hasStarted(tournament);
      this.displayTournament(tournament);
    });
  }

  /**
   * Deletes tournament from the database
   */
  deleteTournament() {
    this.tournamentService.deleteTournament(this.tournamentForm.value.id).subscribe();
    location.reload();
  }

  /**
   * Checks wheter tournament has started
   * 
   * @param {Tournament} tournament Tournament whose date is being validated
   * 
   * @returns {boolean}
   *      <ul>
   *         <li>True if the tournament has started</li>
   *         <li>False if the tournament hasn't started</li>
   *      </ul>
   */
  private hasStarted(tournament: Tournament): boolean {
    var tournamentDate = new Date(tournament.startDate);
    return tournamentDate.getTime() <= this.today.getTime();
  }

  /**
   * Displays selected tournament
   * 
   * @param {Tournament} tournament Selected tournament
   */
  private displayTournament(tournament: Tournament) {
    this.tournamentForm.setValue(tournament);
    this.tournamentForm.controls['hostCountry'].setValue(tournament.hostCountry.name);
  }
}
