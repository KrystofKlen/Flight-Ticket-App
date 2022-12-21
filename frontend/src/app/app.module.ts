import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatSlideToggleModule } from '@angular/material/slide-toggle';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { MatPaginatorModule } from '@angular/material/paginator';
import { MatSortModule } from '@angular/material/sort';
import {MatToolbarModule} from '@angular/material/toolbar';
import {MatIconModule} from '@angular/material/icon';
import {MatButtonModule} from '@angular/material/button';

import {MatTableModule} from '@angular/material/table';
import { PassCompComponent } from './passanger/component/pass-comp/pass-comp.component';
import { PassServService } from './passanger/service/pass-serv.service';
import { HttpClientModule } from '@angular/common/http';
import {MatDialogModule} from '@angular/material/dialog';
import {MatFormFieldModule} from '@angular/material/form-field';
import { DialogAddNewPersonComponent } from './passanger/component/dialog-add-new-person/dialog-add-new-person.component';
import {FormsModule, ReactiveFormsModule} from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import {MatSidenavModule} from '@angular/material/sidenav';
import { NavbarComponent } from './navbar/navbar.component';
import { FlightComponent } from './flights/components/flight/flight.component';
import { PassangerComponent } from './profile/passanger/passanger.component';
import { BookingComponent } from './booking/booking/booking.component';
import { AddFlightDialogComponent } from './flights/components/add-flight-dialog/add-flight-dialog.component';
import { AirportSelectorComponent } from './airportSelector/airport-selector/airport-selector.component';
import {MatAutocompleteModule} from '@angular/material/autocomplete';
import { NumberInputComponent } from './flights/components/number-input/number-input.component';
import {MatStepperModule} from '@angular/material/stepper';
import { AirportComponent } from './airports/airport/airport.component';
import { AirportAddDialogComponent } from './airports/airport-add-dialog/airport-add-dialog.component';
import { FlightHomeComponent } from './flights/components/flight-home/flight-home.component';
import { UpdateUserComponent } from './passanger/component/update-user/update-user.component';
import { TableBookingComponent } from './flights/components/table-booking/table-booking.component';

@NgModule({
  declarations: [
    AppComponent,
    PassCompComponent,
    DialogAddNewPersonComponent,
    NavbarComponent,
    FlightComponent,
    FlightComponent,
    PassangerComponent,
    BookingComponent,
    AirportSelectorComponent,
    NumberInputComponent,
    AirportComponent,
    AirportAddDialogComponent,
    FlightHomeComponent,
    UpdateUserComponent,
    AddFlightDialogComponent,
    TableBookingComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MatSlideToggleModule,
    BrowserAnimationsModule,
    MatTableModule,
    MatPaginatorModule,
    MatSortModule,
    MatToolbarModule,
    MatIconModule,
    MatButtonModule,
    MatDialogModule,
    HttpClientModule,
    MatFormFieldModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatSidenavModule,
    MatAutocompleteModule,
    MatStepperModule
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
