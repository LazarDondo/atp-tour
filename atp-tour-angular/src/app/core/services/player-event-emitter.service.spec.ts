import { TestBed } from '@angular/core/testing';

import { PlayerEventEmitterService } from './player-event-emitter.service';

describe('EventEmitterService', () => {
  let service: PlayerEventEmitterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlayerEventEmitterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
