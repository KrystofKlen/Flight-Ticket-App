import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Flight } from 'src/app/Flight';
import { AddFlightDialogComponent } from '../add-flight-dialog/add-flight-dialog.component';

@Component({
  selector: 'app-flight-home',
  templateUrl: './flight-home.component.html',
  styleUrls: ['./flight-home.component.css']
})
export class FlightHomeComponent {

  constructor(public dialog:MatDialog){}

  openDialog() {
    const dialogRef = this.dialog.open(
      AddFlightDialogComponent);
  }
}
