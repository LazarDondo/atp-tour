import { TestBed } from '@angular/core/testing';

import { StatisticsEventEmitterService } from './statistics-event-emitter.service';

describe('StatisticsEventEmitterService', () => {
  let service: StatisticsEventEmitterService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(StatisticsEventEmitterService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
