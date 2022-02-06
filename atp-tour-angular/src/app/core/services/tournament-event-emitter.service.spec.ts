import { TestBed } from '@angular/core/testing';

import { TournamentEventEmitterService } from './tournament-event-emitter.service';

describe('TournamentEventEmitterService', () => {
  let service: TournamentEventEmitterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(TournamentEventEmitterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
