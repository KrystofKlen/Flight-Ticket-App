import { Airport } from "./Airport";

export interface Flight {
    flightId:number;
    from:Airport;
    to:Airport;
    freeSeats:number;
}