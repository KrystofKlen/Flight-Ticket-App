import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AirportAddDialogComponent } from './airport-add-dialog.component';

describe('AirportAddDialogComponent', () => {
  let component: AirportAddDialogComponent;
  let fixture: ComponentFixture<AirportAddDialogComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AirportAddDialogComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AirportAddDialogComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
