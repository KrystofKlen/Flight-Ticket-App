import { Component, Input, OnInit } from '@angular/core';
import { FlightService } from 'src/app/flights/flight.service';
import { Flight } from 'src/app/Flight';
import { AddFlightDialogComponent } from 'src/app/flights/components/add-flight-dialog/add-flight-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { AirportsService } from 'src/app/airports/airports.service';

@Component({
  selector: 'app-flight',
  templateUrl: './flight.component.html',
  styleUrls: ['./flight.component.css']
})
export class FlightComponent implements OnInit{

  displayedColumns: string[] = ['flightId','from','to','freeSeats','delete'];
  dataSource:Flight[] = [];
  clickedRows = new Set<Flight>();
  constructor(private serviceFlight:FlightService,
    public dialog:MatDialog){}
  

  ngOnInit(): void {
    this.setFlightsData(); 
  }

  setFlightsData(){
    this.serviceFlight.getFlights()
    .subscribe(
      data => this.dataSource = data
    );
  }

  openDialog() {
    const dialogRef = this.dialog.open(
      AddFlightDialogComponent);
  }

  /**
   * Deletes flight iff it has no bookings.
   */
  deleteFlight(row:Flight){
    let shouldRefresh = true;
    this.serviceFlight.deleteFlight(row)
    .subscribe({
      next:(data)=>{},
      error:(err)=>{
        shouldRefresh = false;
        console.log("ERRRRR");
        alert("Flight can not be deleted. It has bookings.");
      },
      complete(){
        window.location.reload(); 
      }

  });
  }
}

