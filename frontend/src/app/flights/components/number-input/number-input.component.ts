import { Component, EventEmitter, Output } from '@angular/core';
import {FormControl, Validators} from '@angular/forms';

@Component({
  selector: 'app-number-input',
  templateUrl: './number-input.component.html',
  styleUrls: ['./number-input.component.css']
})
export class NumberInputComponent {

  freeSeats = new FormControl('', [Validators.required, Validators.pattern('%d')]);
  @Output() value = new EventEmitter<number>();
  freeSeatsNum:number = 0;

  getErrorMessage() {
    if (this.freeSeats.hasError('required')) {
      return 'You must enter a value';
    }

    return this.freeSeats.hasError('') ? 'Not a valid email' : '';
  }

  emit() {
    this.value.emit(this.freeSeatsNum);
  }

}
