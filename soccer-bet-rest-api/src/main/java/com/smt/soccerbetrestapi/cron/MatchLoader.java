package com.smt.soccerbetrestapi.cron;

import com.smt.soccerbetrestapi.entity.Match;
import com.smt.soccerbetrestapi.repo.MatchRepo;
import com.smt.soccerbetrestapi.service.MatchService;
import com.smt.soccerbetrestapi.service.SoccerDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
@Slf4j
@RequiredArgsConstructor
public class MatchLoader {

    private final MatchService matchService;
    private final SoccerDataService soccerDataService;

    @Scheduled(cron = "${load.match.job.cron}")
    public void load() {
        loadNewMatches(soccerDataService.loadAllLeagueMatches());
    }

    private void loadNewMatches(List<Match> matches) {
        List<Match> existingMatches = matchService.findAllMatchesNoCache().collect(Collectors.toList());
        List<Match> newMatches = matches.stream().filter(match -> !existingMatches.contains(match)).collect(Collectors.toList());
        if (!newMatches.isEmpty()) {
            log.info("Found new matches: {}", newMatches);
            matchService.saveAllMatches(newMatches);
        } else {
            log.info("Didn't find new matches");
        }
    }
}
