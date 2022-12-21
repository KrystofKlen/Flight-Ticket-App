import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Airport } from 'src/app/Airport';
import { AirportsService } from '../airports.service';
import { AirportAddDialogComponent } from '../airport-add-dialog/airport-add-dialog.component';

@Component({
  selector: 'app-airport',
  templateUrl: './airport.component.html',
  styleUrls: ['./airport.component.css']
})
export class AirportComponent implements OnInit{

  displayedColumns: string[] = ['airportName'];
  dataSource:Airport[] = [];
  clickedRows = new Set<Airport>();

  constructor(private airportsService:AirportsService, public dialog: MatDialog){
    
  }

  ngOnInit(): void {
    this.setAirportData();
  }

  setAirportData(){
    this.airportsService.getAirports().subscribe(
      data=>{
        this.dataSource = data;
      }
    )
  }

  openDialog() {
    const dialogRef = this.dialog.open(AirportAddDialogComponent);
  }

}
