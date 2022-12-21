import { Dialog, DialogRef } from '@angular/cdk/dialog';
import { Component, Input } from '@angular/core';
import { Inject } from '@angular/core';
import { MAT_DIALOG_DATA } from '@angular/material/dialog';
import { Airport } from 'src/app/Airport';
import { BookingService } from 'src/app/booking/booking.service';
import { FlightService } from 'src/app/flights/flight.service';
import { Booking } from 'src/app/Booking';
import { catchError, throwError } from 'rxjs';
import { HttpErrorResponse } from '@angular/common/http';

@Component({
  selector: 'app-add-flight-dialog',
  templateUrl: './add-flight-dialog.component.html',
  styleUrls: ['./add-flight-dialog.component.css']
})
export class AddFlightDialogComponent {

  from:Airport = {airportName:""};
  to:Airport = {airportName:""};
  freeSeats:number = 0;
  @Input() personId:number | undefined;
  newBooking:Booking = DUMMY;

  constructor(@Inject (MAT_DIALOG_DATA) public personID:number,
  private flightService:FlightService){
    
  }

  setFreeSeats(event: any) {
    console.log("Receive ",event);
    this.freeSeats = event;
  }

  setFrom(event:any) {
    console.log("EReceive from",event);
    this.from=event;
  }

  setTo(event:any) {
    console.log("EVENT to",event);
    this.to=event;
  }

  

  confirm(){
    console.log("CONFIRMING:",this.from,this.to,this.freeSeats);
    this.flightService.postFlight({flightId:0,from:this.from,to:this.to,freeSeats:this.freeSeats})
    .subscribe({
      error:(err) => alert("Flight information invalid."),
      complete(){
        window.location.reload();
      }
    })
  }
  
}

const DUMMY:Booking = 
{
  bookingId:-1,
  personDto:
  {
    personId:-1,
    email:""
  },
  flightDto:
  {
    flightId:-1,
    from:
    {
      airportName:""
    },
    to:
    {
      airportName:""
    },
    freeSeats:-1
  }
};

