import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import {FormControl} from '@angular/forms';
import {Observable} from 'rxjs';
import {map, startWith} from 'rxjs/operators';
import { Airport } from 'src/app/Airport';
import { AirportsService } from 'src/app/airports/airports.service';

@Component({
  selector: 'app-airport-selector',
  templateUrl: './airport-selector.component.html',
  styleUrls: ['./airport-selector.component.css']
})
export class AirportSelectorComponent implements OnInit{

  myControl = new FormControl<string | Airport>('');
  options: Airport[] = [];

  filteredOptions: Observable<Airport[]> | undefined;
  airportName:string  = "";
  @Output() value = new EventEmitter<string>();
  @Input() updateAirportFrom = false;

  constructor(private aiportsService : AirportsService){

  }

  setAirportsData(){
    this.aiportsService.getAirports()
    .subscribe(
      data => {
        this.options = data;
      }
    );
  }

  emit(){
    if(this.updateAirportFrom){
      this.aiportsService.changeAirportFrom(this.airportName);
    }else{
      this.aiportsService.changeAirportTo(this.airportName);
    }
    
    this.value.emit(this.airportName);
  }

  private setDataForAutocomplete(){
    this.filteredOptions = this.myControl.valueChanges.pipe(
      startWith(''),
      map(value => {
        const name = typeof value === 'string' ? value : value?.airportName;
        return name ? this._filter(name as string) : this.options.slice();
      }),
    );
  }

  ngOnInit() {
    console.log("INITIALIZED",this.updateAirportFrom);
    this.setAirportsData();
    this.setDataForAutocomplete();
  }

  displayFn(airport: Airport): string {
    return airport && airport.airportName ? airport.airportName : '';
  }

  // filter for autocomplete
  private _filter(name: string): Airport[] {
    const filterValue = name.toLowerCase();
    return this.options.filter(option => option.airportName.toLowerCase().includes(filterValue));
  }

}
