import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { PlayerEventEmitterService } from 'src/app/core/services/player-event-emitter.service';
import { PlayerService } from 'src/app/core/services/player.service';
import { Player } from 'src/app/models/player.model';

@Component({
  selector: 'app-update-player',
  templateUrl: './update-player.component.html',
  styleUrls: ['./update-player.component.scss']
})
export class UpdatePlayerComponent implements OnInit {
  @Input() selectedPlayer: Player;
  @Input() isAdminUser: boolean;

  playerForm: FormGroup;
  loading = false;
  submitted = false;
  success = false;
  error = false;
  playerControl = new FormControl();

  constructor(private playerService: PlayerService, private formBuilder: FormBuilder,
    private eventEmitterService: PlayerEventEmitterService) {
  }

  ngOnInit(): void {
    this.playerForm = this.formBuilder.group({
      id: [],
      rank: [],
      firstName: ['', Validators.required],
      lastName: ['', Validators.required],
      birthCountry: [],
      dateOfBirth: [],
      currentPoints: ['', Validators.required],
      livePoints: ['', Validators.required]
    });
    this.playerControl.addValidators
    this.displayPlayer(this.selectedPlayer);

    this.eventEmitterService.subsVar = this.eventEmitterService.
      invokeDisplayPlayerFunction.subscribe((player) => {
        this.selectedPlayer = player;
        this.displayPlayer(player);
      });
  }

  onSubmit() {
    this.submitted = true;
    this.error = false;
    this.success = false;
    if (this.playerForm.invalid) {
      return;
    }
    this.loading = true;
    this.updatePlayer();
  }

  updatePlayer() {
    this.selectedPlayer.firstName = this.playerForm.value.firstName;
    this.selectedPlayer.lastName = this.playerForm.value.lastName;
    this.selectedPlayer.livePoints = this.playerForm.value.livePoints;
    this.playerService.updatePlayer(this.selectedPlayer).subscribe({
      next: updatedPlayer => {
        this.selectedPlayer = updatedPlayer;
        this.displayPlayer(updatedPlayer);
        this.eventEmitterService.updatePlayersTable(updatedPlayer);
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
    return this.playerForm.controls;
  }

  displayPlayer(player: Player) {
    this.playerForm.setValue(player);
    this.playerForm.controls['birthCountry'].setValue(player.birthCountry.name);
  }
}
