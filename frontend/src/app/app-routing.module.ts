import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AirportComponent } from './airports/airport/airport.component';
import { BookingComponent } from './booking/booking/booking.component';
import { FlightHomeComponent } from './flights/components/flight-home/flight-home.component';
import { FlightComponent } from './flights/components/flight/flight.component';
import { PassCompComponent } from './passanger/component/pass-comp/pass-comp.component';
import { PassangerComponent } from './profile/passanger/passanger.component';

const routes: Routes = [
  {path:'passangers',component:PassCompComponent},
  {path:'flight',component:FlightHomeComponent},
  {path:'profile/:personId',component:PassangerComponent},
  {path:'booking',component:BookingComponent},
  {path:'airport',component:AirportComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
