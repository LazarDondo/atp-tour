import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { BrowserModule } from '@angular/platform-browser';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { AuthInterceptor } from './core/interceptors/auth.interceptor';
import { LoginPageComponent } from './features/login-page/login-page.component';
import { ReactiveFormsModule } from '@angular/forms';
import { RegistrationPageComponent } from './features/registration-page/registration-page.component';
import { HeaderComponent } from './features/header/header.component';
import { FooterComponent } from './features/footer/footer.component';
import { PlayerPageComponent } from './features/player-page/player-page.component';
import { MatAutocompleteModule } from '@angular/material/autocomplete';
import { MatFormFieldModule } from '@angular/material/form-field';
import { MatInputModule } from '@angular/material/input';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations'
import { MatOptionModule } from '@angular/material/core';
import { AddPlayerComponent } from './features/player-page/add-player/add-player.component';
import { AllPlayersComponent } from './features/player-page/all-players/all-players.component';
import { MatTableModule } from '@angular/material/table';
import { NgxPaginationModule } from 'ngx-pagination';
import { OrderModule } from 'ngx-order-pipe';
import { UpdatePlayerComponent } from './features/player-page/update-player/update-player.component';
import { PlayerEventEmitterService } from './core/services/player-event-emitter.service';
import { MatDialogModule } from '@angular/material/dialog';
import { TournamentPageComponent } from './features/tournament-page/tournament-page.component';
import { AddTournamentComponent } from './features/tournament-page/add-tournament/add-tournament.component';
import { AllTournamentsComponent } from './features/tournament-page/all-tournaments/all-tournaments.component';
import { UpdateTournamentComponent } from './features/tournament-page/update-tournament/update-tournament.component';
import { TournamentEventEmitterService } from './core/services/tournament-event-emitter.service';
import { MatchesComponent } from './features/matches/matches.component';
import { StatisticsComponent } from './features/statistics/statistics.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginPageComponent,
    RegistrationPageComponent,
    HeaderComponent,
    FooterComponent,
    PlayerPageComponent,
    AddPlayerComponent,
    AllPlayersComponent,
    UpdatePlayerComponent,
    TournamentPageComponent,
    AddTournamentComponent,
    AllTournamentsComponent,
    UpdateTournamentComponent,
    MatchesComponent,
    StatisticsComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    HttpClientModule,
    ReactiveFormsModule,
    MatAutocompleteModule,
    MatFormFieldModule,
    MatInputModule,
    BrowserAnimationsModule,
    MatOptionModule,
    MatAutocompleteModule,
    MatTableModule,
    NgxPaginationModule,
    OrderModule,
    MatDialogModule,
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    PlayerEventEmitterService, TournamentEventEmitterService],
  bootstrap: [AppComponent]
})
export class AppModule { }
