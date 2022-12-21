package com.example.app.Algo;

import lombok.Data;

import java.util.Set;

@Data
public class Node {
    private AirportBFS airport;

    private Set<Long> neighbours;

}
