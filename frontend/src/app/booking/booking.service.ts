import { HttpClient, HttpHeaders, HttpParams, HttpRequest } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Booking } from '../Booking';
import { Observable } from 'rxjs';
import { Passanger } from '../Passanger';

@Injectable({
  providedIn: 'root'
})
export class BookingService{

  private urlSaveBook = "http://localhost:8080/api/v1/booking";
  private urlGetBooking = "http://localhost:8080/api/v1/person/book";

  private httpOptions:Object= {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  };

  person:Passanger = {personId: -1, email:""}; // dummy passanger just for initialization, will be overwritten
 
  constructor(private http:HttpClient) { }
  
  getBookings(_personId:number):Observable< Booking []>{
    this.person.personId = _personId;
    return this.http.get<Booking[]>(`${this.urlGetBooking}/${_personId}`);
  }

  saveBooking(data:Booking):Observable<Booking>{
    return this.http.post<Booking>(this.urlSaveBook,data,this.httpOptions);
  }

  deleteBooking(booking:Booking){
    this.http.delete(this.urlSaveBook,{
      headers: new HttpHeaders({
        'Content-Type':  'application/json'
      }),
      body:booking
    }).subscribe();
  }

}
