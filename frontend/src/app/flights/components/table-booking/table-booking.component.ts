import { Component, EventEmitter, Input, Output } from '@angular/core';
import { BookingService } from 'src/app/booking/booking.service';
import { Flight } from 'src/app/Flight';
import { FlightService } from '../../flight.service';

@Component({
  selector: 'app-table-booking',
  templateUrl: './table-booking.component.html',
  styleUrls: ['./table-booking.component.css']
})
export class TableBookingComponent {

  displayedColumns: string[] = ['flightId','from','to','freeSeats','book'];
  dataSource:Flight[] = [];
  clickedRows = new Set<Flight>();
  
  @Output() flight = new EventEmitter();

  constructor(private serviceFlight:FlightService, private bookingService:BookingService){}

  ngOnInit(): void {
    this.setFlightsData(); 
  }

  setFlightsData(){
    this.serviceFlight.getFlights()
    .subscribe(
      data => {this.dataSource = data;
      }
    );
  }

  emitSelectedFlight(row:Flight){
    console.log("EMITTING",row.flightId);
    this.flight.emit(row);
  }
}
