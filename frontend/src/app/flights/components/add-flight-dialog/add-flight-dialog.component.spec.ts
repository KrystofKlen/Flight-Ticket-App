import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AddFlightDialogComponent } from './add-flight-dialog.component';

describe('AddFlightDialogComponent', () => {
  let component: AddFlightDialogComponent;
  let fixture: ComponentFixture<AddFlightDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AddFlightDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AddFlightDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
