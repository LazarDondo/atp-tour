import { Component, Input, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { PlayerService } from 'src/app/core/services/player.service';
import { Player } from 'src/app/models/player.model';

@Component({
  selector: 'app-update-player',
  templateUrl: './update-player.component.html',
  styleUrls: ['./update-player.component.scss']
})
export class UpdatePlayerComponent implements OnInit {
  @Input() selectedPlayer:Player;

  playerForm: FormGroup;
  loading = false;
  submitted = false;
  success = false;
  error = false;
  validCountry = false;
  returnUrl: string;
  myControl = new FormControl();

  constructor(private playerService: PlayerService, private formBuilder: FormBuilder) {
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
      livePoints:['', Validators.required]
    });
    this.myControl.addValidators
    this.playerForm.setValue(this.selectedPlayer);
  }

  onSubmit() {
    console.log(123);
    this.submitted = true;
    if (this.playerForm.invalid || !this.validCountry) {
      return;
    }
    this.loading = true;
    this.updatePlayer();
  }

  updatePlayer() {
    console.log(456);
    this.playerService.updatePlayer(this.playerForm.value).subscribe({
      next: updatedPlayer => {
        this.selectedPlayer=updatedPlayer;
        this.playerForm.setValue(updatedPlayer);
        this.error = false;
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

  displayPlayer(player:Player){
    this.selectedPlayer=player;
  }
}
