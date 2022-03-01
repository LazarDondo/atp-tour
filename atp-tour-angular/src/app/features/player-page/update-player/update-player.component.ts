import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { PlayerEventEmitterService } from 'src/app/core/services/player-event-emitter.service';
import { PlayerService } from 'src/app/core/services/player.service';
import { Player } from 'src/app/models/player.model';

/**
 * Represents the update player component
 * 
 * @author Lazar
 */
@Component({
  selector: 'app-update-player',
  templateUrl: './update-player.component.html',
  styleUrls: ['./update-player.component.scss']
})
export class UpdatePlayerComponent implements OnInit {

  @Input() selectedPlayer: Player;
  @Input() isAdminUser: boolean;
  playerForm: FormGroup;
  loading: boolean = false;
  submitted: boolean = false;
  success: boolean = false;
  error: boolean = false;
  playerControl = new FormControl();

  /**
   * @constructor
   * 
   * @param {PlayerService} playerService 
   * @param {FormBuilder} formBuilder 
   * @param {PlayerEventEmitterService} eventEmitterService 
   */
  constructor(private playerService: PlayerService, private formBuilder: FormBuilder, private eventEmitterService: PlayerEventEmitterService) { }

  /**
   * Configures form fields, displays selected player and subsribes to display player event
   */
  ngOnInit(): void {
    this.playerForm = this.configureFormFields();
    this.displayPlayer(this.selectedPlayer);
    this.subscribeToDisplayPlayerEvent();
  }

  /**
   * Sets form variables and updates player
   * 
   * @returns If any form field value is invalid
   */
  onSubmit() {
    this.setFormVariables();
    if (this.playerForm.invalid) {
      return;
    }
    this.loading = true;
    this.updatePlayer();
  }

  /**
   * Configures form fields
   * 
   * @returns {FormGroup} Form with configured form fields
   */
  private configureFormFields(): FormGroup {
    var form = this.formBuilder.group({
      id: [],
      rank: [],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      birthCountry: [],
      dateOfBirth: [],
      currentPoints: ['', Validators.required],
      livePoints: ['', Validators.required]
    });
    return form;
  }

  /**
   * Subscribes to display player event
   */
  private subscribeToDisplayPlayerEvent() {
    this.eventEmitterService.invokeDisplayPlayerFunction.subscribe((player) => {
      this.selectedPlayer = player;
      this.displayPlayer(player);
    });
  }

  /**
   * Sets form variables;
   */
  private setFormVariables() {
    this.submitted = true;
    this.error = false;
    this.success = false;
  }

  /**
   * Updates player and displays updated player
   */
  private updatePlayer() {
    this.setPlayerProperties();
    this.playerService.updatePlayer(this.selectedPlayer).subscribe({
      next: updatedPlayer => {
        this.selectedPlayer = updatedPlayer;
        this.displayPlayer(updatedPlayer);
        this.eventEmitterService.updatePlayersTable(updatedPlayer);
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
   * Sets player's first name, last name and live points values
   */
  private setPlayerProperties() {
    this.selectedPlayer.firstName = this.playerForm.value.firstName;
    this.selectedPlayer.lastName = this.playerForm.value.lastName;
    this.selectedPlayer.livePoints = this.playerForm.value.livePoints;
  }

  /**
   * Gets player form controls
   */
  get f() {
    return this.playerForm.controls;
  }

  /**
   * Displays selected player
   * 
   * @param {Player} player Selected player
   */
  displayPlayer(player: Player) {
    this.playerForm.setValue(player);
    this.playerForm.controls['birthCountry'].setValue(player.birthCountry.name);
  }
}
