package com.example.app.Algo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightBFS {
    private AirportBFS from;
    private AirportBFS to;
    private Integer freeSeats;
}
