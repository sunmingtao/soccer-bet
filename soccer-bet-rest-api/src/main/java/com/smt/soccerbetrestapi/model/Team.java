package com.smt.soccerbetrestapi.model;

import com.smt.soccerbetrestapi.utils.DoublyLinkedList;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Team {

    private final String name;
    private DoublyLinkedList<MatchStats> matchStatList;

}
