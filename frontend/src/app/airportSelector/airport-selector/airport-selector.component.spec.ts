import { ComponentFixture, TestBed } from '@angular/core/testing';

import { AirportSelectorComponent } from './airport-selector.component';

describe('AirportSelectorComponent', () => {
  let component: AirportSelectorComponent;
  let fixture: ComponentFixture<AirportSelectorComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ AirportSelectorComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(AirportSelectorComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
