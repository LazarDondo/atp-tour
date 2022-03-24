import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup} from '@angular/forms';
import { AuthService } from 'src/app/core/services/auth.service';
import { StatisticsEventEmitterService } from 'src/app/core/services/statistics-event-emitter.service';
import { StatisticsService } from 'src/app/core/services/statistics.service';
import { Match } from 'src/app/models/match.model';

/**
 * Represents the statistics component
 * 
 * @author Lazar
 */
@Component({
  selector: 'app-statistics',
  templateUrl: './statistics.component.html',
  styleUrls: ['./statistics.component.scss']
})
export class StatisticsComponent implements OnInit {

  selectedMatch: Match;
  statisticsForm: FormGroup;
  submitted: boolean = false;
  success: boolean = false;
  error: boolean = false;
  loading: boolean = false;
  isAdminUser: boolean;
  statisticsControl = new FormControl();

  /**
   * @constructor
   * 
   * @param {FormBuilder} formBuilder 
   * @param {StatisticsService} statisticsService 
   * @param {StatisticsEventEmitterService} eventEmitterService 
   * @param {AuthService} authService 
   */
  constructor(private formBuilder: FormBuilder, private statisticsService: StatisticsService,
    private eventEmitterService: StatisticsEventEmitterService, private authService: AuthService) { }

  ngOnInit(): void {
    this.statisticsForm = this.configureFormFields();
    this.isAdminUser = this.authService.isAdmin();
    this.previewStatistics();
  }

  /**
   * Saves statistics to the database on form submit
   */
  onSubmit() {
    this.setFormVariables();
    this.saveStatistics();
  }

  /**
   * Configures form fields
   * 
   * @returns {FormGroup} Form with configured form fields
   */
  private configureFormFields(): FormGroup {
    var form = this.formBuilder.group({
      id: [],
      match: [],
      firstPlayerPoints: [],
      secondPlayerPoints: [],
      firstPlayerAces: [],
      secondPlayerAces: [],
      firstPlayerBreakPoints: [],
      secondPlayerBreakPoints: [],
      firstPlayerFirstServesIn: [],
      secondPlayerFirstServesIn: [],
      firstPlayerSecondServesIn: [],
      secondPlayerSecondServesIn: []
    });
    return form;
  }

  /**
   * Sets form variables
   */
  private setFormVariables() {
    this.submitted = true;
    this.error = false;
    this.success = false;
    this.loading = true;
  }

  /**
   * Saves new statistics to teh database
   */
  private saveStatistics() {
    if (!this.statisticsForm.value.id) {
      this.statisticsForm.value.match = this.selectedMatch;
      this.statisticsForm.value.id = 0;
    }
    this.statisticsService.saveStatistics(this.statisticsForm.value).subscribe({
      next: savedStatistics => {
        this.statisticsForm.setValue(savedStatistics);
        this.loading = false;
        this.success = true;
      },
      error: err => {
        this.error = true;
        this.loading = false;
      }
    });
  }

  /**
   * Previews match statistics
   */
  private previewStatistics() {
    this.statisticsService.findStatistics(this.selectedMatch).subscribe({
      next: statistics => {
        if (statistics) {
          this.statisticsForm.setValue(statistics);
        }
      }
    });
  }

  /**
   * Closes statistics dialog
   */
  closeDialog() {
    this.eventEmitterService.closeDialog();
  }

}
