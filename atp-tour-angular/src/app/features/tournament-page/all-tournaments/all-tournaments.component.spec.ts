import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AllTournamentsComponent } from './all-tournaments.component';

describe('AllTournamentsComponent', () => {
  let component: AllTournamentsComponent;
  let fixture: ComponentFixture<AllTournamentsComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AllTournamentsComponent ]
    })
    .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AllTournamentsComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
