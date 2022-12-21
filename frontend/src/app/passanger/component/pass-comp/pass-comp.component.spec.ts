import { ComponentFixture, TestBed } from '@angular/core/testing';

import { PassCompComponent } from './pass-comp.component';

describe('PassCompComponent', () => {
  let component: PassCompComponent;
  let fixture: ComponentFixture<PassCompComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ PassCompComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(PassCompComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
