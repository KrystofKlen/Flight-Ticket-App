import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { Airport } from '../Airport';

@Injectable({
  providedIn: 'root'
})
export class AirportsService {

  private url = "http://localhost:8080/api/v1/airport";

  private httpOptions:Object= {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  }

  public airportFrom:string ="";
  public airportTo:string ="";

  public subject = new Subject<any>();

  private airportFromSource = new BehaviorSubject(this.airportFrom);
  private airportToSource = new BehaviorSubject(this.airportTo);

  constructor(private http:HttpClient) { }

  currentFrom = this.airportFromSource.asObservable();
  currentTo = this.airportToSource.asObservable();

  changeAirportFrom(name: string) {
    this.airportFromSource.next(name);
    console.log("_________________________________________________________")
    console.log("changed F ");
    console.log("FROM, ",this.currentFrom)
    console.log("TO  , ",this.currentTo)
    console.log("_________________________________________________________")
  }

  changeAirportTo(name: string) {
    this.airportToSource.next(name);
    console.log("_________________________________________________________")
    console.log("changed T ");
    console.log("FROM, ",this.currentFrom)
    console.log("TO  , ",this.currentTo)
    console.log("_________________________________________________________")
  }

  getAirports():Observable<Airport[]>{
    return this.http.get<Airport[]>(this.url);
  }

  postAirport(airport:Airport):Observable<Airport>{
      console.log("POSTING",airport);
      return this.http.post<Airport>(this.url, airport, this.httpOptions);
  }

}
