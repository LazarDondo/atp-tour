import { HTTP_INTERCEPTORS } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { AppComponent } from './app.component';
import { AuthInterceptor } from './core/interceptors/auth.interceptor';
import { PlayerEventEmitterService } from './core/services/player-event-emitter.service';
import { TournamentEventEmitterService } from './core/services/tournament-event-emitter.service';
import { TournamentModule } from './modules/tournament/tournament.module';
import { SharedModule } from './modules/shared/shared.module';
import { PlayerModule } from './modules/player/player.module';
import { MatchesModule } from './modules/matches/matches.module';
import { AuthenticationModule } from './modules/authentication/authentication.module';
import { HeaderFooterModule } from './modules/header-footer/header-footer.module';

@NgModule({
  declarations: [
    AppComponent
  ],
  imports: [
    SharedModule,
    AuthenticationModule,
    HeaderFooterModule,
    TournamentModule,
    PlayerModule,
    MatchesModule
  ],
  providers: [
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true },
    PlayerEventEmitterService, TournamentEventEmitterService],
  bootstrap: [AppComponent]
})
export class AppModule { }
