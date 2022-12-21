import { Airport } from "./Airport";
import { Flight } from "./Flight";
import { Passanger } from "./Passanger";

export interface Booking{
    bookingId:number;
    personDto:Passanger;
    flightDto:Flight;
}