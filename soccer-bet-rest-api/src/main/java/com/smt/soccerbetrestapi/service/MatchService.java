package com.smt.soccerbetrestapi.service;

import com.smt.soccerbetrestapi.entity.Match;
import com.smt.soccerbetrestapi.repo.MatchRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchService {
    private final MatchRepo matchRepo;

    @Cacheable(value = "matchesCache", key = "#season")
    public List<Match> findMatches(String season) {
        return findMatchesNoCache(season).collect(Collectors.toList());
    }

    public Stream<Match> findMatchesNoCache(String season) {
        return matchRepo.findBySeason(season).stream();
    }

    public void saveAllMatches(Iterable<Match> matches) {
        matchRepo.saveAll(matches);
    }

}
