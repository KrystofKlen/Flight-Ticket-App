import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Passanger } from 'src/app/Passanger';
import { catchError, Observable } from 'rxjs';
import { Router } from '@angular/router';

@Injectable({
  providedIn: 'root'
})
export class PassServService {

  private url:string="http://localhost:8080/api/v1/person";

  private httpOptions:Object= {
    headers: new HttpHeaders({
      'Content-Type':  'application/json'
    })
  }

  constructor(private http: HttpClient, private router:Router) { }

  getPassangers():Observable<Passanger[]>{
    return this.http.get<Passanger[]>(this.url)
  }

  private person: Passanger = {personId:0,email:""};
  getPassanger(id:number):Observable<Passanger>{
    this.person.personId=id;
    return this.http.get<Passanger>(this.url+`/${id}`);
  }

  postPassanger(data:Passanger):Observable<Passanger>{
    return this.http.post<Passanger>(this.url,data,this.httpOptions);
  }

  deletePerson(person:Passanger){
    this.http.delete<Passanger>( `${this.url}/del/${person.personId}`).subscribe();
  }

  updatePerson(person:Passanger){
    this.http.patch<any>(`${this.url}/patch`,person,this.httpOptions)
    .subscribe((data)=>{console.log("Helli from patch",data)})
  }

  navigateToProfile(personId:number){
    this.router.navigate(['/profile', personId]);
  }



}
