import { Component } from '@angular/core';
import { FormControl, Validators } from '@angular/forms';
import { AirportsService } from '../airports.service';
@Component({
  selector: 'app-airport-add-dialog',
  templateUrl: './airport-add-dialog.component.html',
  styleUrls: ['./airport-add-dialog.component.css']
})
export class AirportAddDialogComponent {


  airport = new FormControl('', [Validators.required, Validators.email]);
  airportName:string="";

  constructor(private airportService:AirportsService){}

  postAirport() {
    this.airportService.postAirport({airportName:this.airportName})
    .subscribe(data => {
      window.location.reload();
    })
  }

}
