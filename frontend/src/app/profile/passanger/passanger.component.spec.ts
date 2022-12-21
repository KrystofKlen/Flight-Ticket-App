import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PassangerComponent } from './passanger.component';

describe('PassangerComponent', () => {
  let component: PassangerComponent;
  let fixture: ComponentFixture<PassangerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PassangerComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PassangerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
