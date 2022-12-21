import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DialogAddNewPersonComponent } from './dialog-add-new-person.component';

describe('DialogAddNewPersonComponent', () => {
  let component: DialogAddNewPersonComponent;
  let fixture: ComponentFixture<DialogAddNewPersonComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DialogAddNewPersonComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DialogAddNewPersonComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
