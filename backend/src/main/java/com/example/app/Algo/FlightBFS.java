package com.example.app.Algo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FlightBFS {
    private Long from;
    private Long to;
    private Integer freeSeats;
}
