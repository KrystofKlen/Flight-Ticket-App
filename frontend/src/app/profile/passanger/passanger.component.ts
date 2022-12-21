import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Route, Router } from '@angular/router';
import { Booking } from 'src/app/Booking';
import { BookingService } from 'src/app/booking/booking.service';
import { Passanger } from 'src/app/Passanger';
import { PassServService } from 'src/app/passanger/service/pass-serv.service';
import {animate, state, style, transition, trigger} from '@angular/animations';
import { Flight } from 'src/app/Flight';
import {MatDialog, MatDialogConfig, MAT_DIALOG_DATA} from '@angular/material/dialog';
import { AddFlightDialogComponent } from '../../flights/components/add-flight-dialog/add-flight-dialog.component';

@Component({
  selector: 'app-passanger',
  templateUrl: './passanger.component.html',
  styleUrls: ['./passanger.component.css'],
})
export class PassangerComponent implements OnInit{

  displayedColumns: string[] = ['bookingId', 'flightId','from','to','delete'];
  dataSource:Booking[] = [];
  personId:number =-1; // just for initialization => will be overwrittens

  constructor(private route:ActivatedRoute
    , private servicePassager: PassServService,
    private serviceBooking:BookingService,
    public dialog:MatDialog ){

  }

  setBookings(){
      this.serviceBooking.getBookings(this.personId)
      .subscribe(
        data=>{
          this.dataSource = data;
          console.log("source",this.dataSource);
        }
      )
  }

  ngOnInit(): void {
    this.route.paramMap.subscribe(params => {
      this.personId =  Number(params.get('personId')) ;
    });
    this.setBookings();
  }

  deleteBooking(row:Booking){
    console.log(row.bookingId);
    this.serviceBooking.deleteBooking(row);
    window.location.reload();
  }


}






