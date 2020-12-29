package com.smt.soccerbetrestapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.smt.soccerbetrestapi.utils.DoubleUtils;
import com.smt.soccerbetrestapi.utils.DoublyLinkedList;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

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

    public double getTotalProfit() {
        return DoubleUtils.round(matchStatsList.stream().map(MatchStats::getProfit).reduce(0d, Double::sum), 2);
    }

    public double getTotalProfitAsFav() {
        return DoubleUtils.round(matchStatsList.stream()
                .filter(matchStats -> StringUtils.equalsIgnoreCase(matchStats.getFavouriteOrUnderDog(), "Fav"))
                .map(MatchStats::getProfit).reduce(0d, Double::sum), 2);
    }
}
