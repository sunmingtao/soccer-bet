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

    private MatchStats getLastMatch() {
        return matchStatsList.get(matchStatsList.size() - 1);
    }

    public double getAccumulativePointsDiff() {
        return getLastMatch().getAccumulativePointsDiff();
    }

    public double getLast3MatchesPointsDiff() {
        return getLastMatch().getLast3MatchesPointsDiff();
    }

    public double getLast5MatchesPointsDiff() {
        return getLastMatch().getLast5MatchesPointsDiff();
    }
}
