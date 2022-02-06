import { ComponentFixture, TestBed } from '@angular/core/testing';

import { UpdateTournamentComponent } from './update-tournament.component';

describe('UpdateTournamentComponent', () => {
  let component: UpdateTournamentComponent;
  let fixture: ComponentFixture<UpdateTournamentComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ UpdateTournamentComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(UpdateTournamentComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
