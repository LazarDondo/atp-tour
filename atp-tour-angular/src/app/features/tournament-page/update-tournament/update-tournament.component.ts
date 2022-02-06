import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { TournamentService } from 'src/app/core/services/tournament.service';

@Component({
  selector: 'app-update-tournament',
  templateUrl: './update-tournament.component.html',
  styleUrls: ['./update-tournament.component.scss']
})
export class UpdateTournamentComponent implements OnInit {

  tournamentForm: FormGroup;
  loading = false;
  submitted = false;
  success = false;
  error = false;
  tournamentStarted : Boolean;
  validCountry = false;
  futureDate: Date
  myControl = new FormControl();

  constructor(private tournamentService: TournamentService, private formBuilder: FormBuilder) {
    this.futureDate = new Date();
    this.futureDate.setDate(this.futureDate.getDate()+1);
  }

  ngOnInit(): void {
    this.tournamentForm = this.formBuilder.group({
      name: ['', Validators.required],
      startDate: ['', Validators.required],
      completitionDate: [],
      hostCountry: [],
      tournamentType: ['', Validators.required],
    });
    this.myControl.addValidators;
  }


  onSubmit() {
    this.submitted = true;
    if (this.tournamentForm.invalid) {
      return;
    }
    this.loading = true;
    this.updateTournament();
  }

  updateTournament() {
    this.tournamentService.addTournament(this.tournamentForm.value).subscribe({
      next: addedTournament => {
        delete addedTournament.id;
        this.tournamentForm.setValue(addedTournament);
        this.error = false;
        this.loading = false;
        this.success = true;
      },
      error: err => {
        this.success=false;
        this.error = true;
        this.loading = false;
      }
    });
  }

  get f() {
    return this.tournamentForm.controls;
  }

}
