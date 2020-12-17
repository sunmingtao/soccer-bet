package com.smt.soccerbetrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smt.soccerbetrestapi.utils.DoublyLinkedList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Team {

    private final String name;
    @JsonIgnore
    private DoublyLinkedList<MatchStats> matchStatsList = new DoublyLinkedList<>();

    public void addMatchStats(MatchStats matchStats) {
        matchStatsList.add(matchStats);
    }

}
