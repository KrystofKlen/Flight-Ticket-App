import { Injectable } from '@angular/core';
import { BehaviorSubject, catchError, Observable, Subject, throwError } from 'rxjs';
import { Flight } from '../Flight';
import { HttpClient, HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { HttpHeaders } from '@angular/common/http';
import { Airport } from '../Airport';
import { Booking } from '../Booking';
import { Form } from '@angular/forms';

@Injectable({
  providedIn: 'root'
})
export class FlightService {

  private url:string="http://localhost:8080/api/v1/flight";

  private httpOptions:Object= {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    }),
    observe: 'response'
  }

  private subjectSelectedFlight = new Subject<any>();
  public selectedFlight:Flight = {flightId:-1,from:{airportName:""},to:{airportName:""},freeSeats:0};
  private selectedFlightSource = new BehaviorSubject(this.selectedFlight)
  public selectedFlightToDisplay = this.selectedFlightSource.asObservable();

  public subject = new Subject<any>();
  public flights:Flight[] = [];
  private flightsSourceForTable = new BehaviorSubject(this.flights);
  flightsToDisplay = this.flightsSourceForTable.asObservable();

  public setSelectedFlight(flight:Flight){
    this.selectedFlightSource.next(flight);
  }

  /**
   * Refreshes array of flights to be displayed in a table.
   * Call this function whenever you want make selection or 
   * filtering of flights.
   * @param route true=we set parameters => we hant flights @from @to, false = we want to display all rhe flights
   * @param from 
   * @param to 
   * @returns 
   */
  public refreshFlightsToDisplay(route:boolean,from:Airport,to:Airport){
      // display all flights
      if(!route){
        this.http.get<Flight[]>(this.url).subscribe(
          flight=>{this.flightsSourceForTable.next(flight);
          console.log("UPDATING ",flight)
          }
        )
        return;
      }
      //retreive value from objects
      let a = JSON.stringify(from.airportName)
      .substring(16).replace("}","").replace("\"","").replace("%s","%20");
      let b = JSON.stringify(to.airportName).substring(16).replace("}","").replace("\"","");
      ;
      //get flights with with passed params      
      this.http.get<Flight[]>(`${this.url}/${a}/${b}`).subscribe(
        flight=>{
          this.flightsSourceForTable.next(flight);
          console.log("UPDATING ",flight);
        }
      )
  }

  constructor(private http : HttpClient) { }

  getFlights():Observable<Flight[]>{
    return this.http.get<Flight[]>(this.url);
  }

  getFlightsOnConcreteRoute(from:string, to:string):Observable<Flight[]>{
    return this.http.get<Flight[]>(`${this.url}${from}/${to}`);
  }

  postFlight(flight:Flight):Observable<Object>{
    return this.http.post(this.url,flight,this.httpOptions);
  }

  /**
   * deletes flight iff it has no bookings
   * if flight has bookings, alert shown
   */
  deleteFlight(flight:Flight):Observable<Flight>{
    return this.http.delete<Flight>(`${this.url}/del/${flight.flightId}`,this.httpOptions);
  }

  getFlightsFronTo(from:string,to:string):Observable<Flight[]>{
    //retreive value from objects
    let a = JSON.stringify(from)
    .substring(16).replace("}","").replace("\"","").replace("%s","%20");
    let b = JSON.stringify(to).substring(16).replace("}","").replace("\"","");
    ;
    let murl = `${this.url}/${a}/${b}`;
    console.log(murl);
    return this.http.get<Flight[]>(`${this.url}/${a}/${b}`);
  }


}
