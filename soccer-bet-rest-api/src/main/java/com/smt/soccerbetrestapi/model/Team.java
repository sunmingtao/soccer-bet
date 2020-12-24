package com.smt.soccerbetrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smt.soccerbetrestapi.utils.DoublyLinkedList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class Team {

    private final String name;
    @JsonIgnore
    private DoublyLinkedList<MatchStats> matchStatsList = new DoublyLinkedList<>();

    public void addMatchStats(MatchStats matchStats) {
        matchStatsList.add(matchStats);
    }

    private Optional<MatchStats> getLastMatchStats() {
        return matchStatsList.isEmpty() ? Optional.empty() : Optional.of(matchStatsList.get(matchStatsList.size() - 1));
    }

    public double getAccumulativePointsDiff() {
        return getLastMatchStats().map(MatchStats::getAccumulativePointsDiff).orElse(0d);
    }

    public double getLast3MatchesPointsDiff() {
        return getLastMatchStats().map(MatchStats::getLast3MatchesPointsDiff).orElse(0d);
    }

    public double getLast5MatchesPointsDiff() {
        return getLastMatchStats().map(MatchStats::getLast5MatchesPointsDiff).orElse(0d);
    }
}
