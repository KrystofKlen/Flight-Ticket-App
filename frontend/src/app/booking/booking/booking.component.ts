import { Component, OnInit } from '@angular/core';
import { BookingService } from '../booking.service';
import {FormBuilder, Validators} from '@angular/forms';
import { AirportSelectorComponent } from 'src/app/airportSelector/airport-selector/airport-selector.component';
import { AirportsService } from 'src/app/airports/airports.service';
import { FlightService } from 'src/app/flights/flight.service';
import { Airport } from 'src/app/Airport';
import { Observable } from 'rxjs';
import { Flight } from 'src/app/Flight';
import { compileClassMetadata } from '@angular/compiler';

@Component({
  selector: 'app-booking',
  templateUrl: './booking.component.html',
  styleUrls: ['./booking.component.css']
})
export class BookingComponent implements OnInit{

  firstFormGroup = this._formBuilder.group({
    firstCtrl: ['', Validators.required],
  });
  secondFormGroup = this._formBuilder.group({
    secondCtrl: ['', Validators.required],
  });
  isLinear = false;

  userEmailSelected:string ="";
  from:Airport = {airportName:"New York"};
  to:Airport = {airportName:"New York"};
  flightSelected:Flight = DUMMY_FLIGHT; // just basic initialization, will be overwritten
  displayedColumns: string[] = ['flightId','from','to','freeSeats','book'];
  dataSource:Flight[] = [];
  clickedRows = new Set<Flight>();

  constructor(private _formBuilder: FormBuilder
    ,private airportsService: AirportsService
    ,private flightService:FlightService,
    private bookingService:BookingService) {
  }

  bookFlight(flight:Flight){
    this.flightSelected = flight;
    let SERVER_TAKES_CARE_OF_AUTOMATICALLY = 0;
    this.bookingService.saveBooking(
      {
        bookingId:SERVER_TAKES_CARE_OF_AUTOMATICALLY,
        personDto:
        {
            personId:SERVER_TAKES_CARE_OF_AUTOMATICALLY,
            email:this.userEmailSelected
        },
        flightDto:
        {
          flightId:flight.flightId,
          from:
          {
            airportName:flight.from.airportName
          },
          to:
          {
            airportName:flight.to.airportName
          },
          freeSeats:SERVER_TAKES_CARE_OF_AUTOMATICALLY
        }
      }
    ).subscribe({
      error:(err)=>{alert("Error: Ivalid booking information.")
      },
      complete() {
        window.location.reload();
      },
    });

    console.log("Booking flight: ",flight.flightId, flight.from.airportName, 
    flight.to.airportName, this.userEmailSelected);
  }

  findFlights() {
    console.log(this.from.airportName);
    this.flightService
    .getFlightsFronTo(this.from.airportName,this.to.airportName);
  }
  
  ngOnInit(): void {
    this.airportsService.currentFrom.subscribe(
      airpFrom=>{
        this.from.airportName = airpFrom;
      }
    );
    this.airportsService.currentTo.subscribe(
      airpTo=>{
        this.to.airportName = airpTo;
      }
    )
    this.setFlightsData();
  }

  postBooking() {
    // retreive values from object so that they can be passed into the body of request.
    let a = JSON.stringify(this.from.airportName).substring(16).replace("}","").replace("\"","");
    let b = JSON.stringify(this.to.airportName).substring(16).replace("}","").replace("\"","");

    let SERVER_TAKES_CARE_OF_AUTOMATICALLY = 0;

    console.log("POSTTING:   => ",this.userEmailSelected,a,b)

    let email = this.userEmailSelected;
    let id = this.flightSelected.flightId;
    this.userEmailSelected = "";
    this.flightSelected = DUMMY_FLIGHT;
    
    this.bookingService.saveBooking(
      {
        bookingId:SERVER_TAKES_CARE_OF_AUTOMATICALLY,
        personDto:
        {
            personId:SERVER_TAKES_CARE_OF_AUTOMATICALLY,
            email:email
        },
        flightDto:
        {
          flightId:id,
          from:
          {
            airportName:a
          },
          to:
          {
            airportName:b
          },
          freeSeats:SERVER_TAKES_CARE_OF_AUTOMATICALLY
        }
      }
    ).subscribe({
      error:(err)=>{alert("Error: Ivalid booking information.")
      },
      complete() {
        window.location.reload();
      },
    });
  }
      
  setFlightsData(){
    this.flightService.getFlights()
    .subscribe(
      data => {this.dataSource = data;
      }
    );
  }


  setFlightsFromTo(){
    this.dataSource = [];
    this.flightService.getFlightsFronTo(this.from.airportName,this.to.airportName)
    .subscribe(
      data=>{
        this.dataSource=data;
      }
    )
  }

}

const DUMMY_FLIGHT:Flight = {
  flightId:0,
  from:{airportName:""},
  to:{airportName:""},
  freeSeats:0
}
