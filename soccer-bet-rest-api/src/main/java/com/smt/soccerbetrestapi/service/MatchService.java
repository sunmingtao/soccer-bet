package com.smt.soccerbetrestapi.service;

import com.smt.soccerbetrestapi.entity.Match;
import com.smt.soccerbetrestapi.repo.MatchRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
@Slf4j
public class MatchService {
    private final MatchRepo matchRepo;

    @Cacheable(value = "matchesCache", key = "{#root.methodName}")
    public List<Match> findAllMatches() {
        log.info("Find all matches");
        return StreamSupport.stream(matchRepo.findAll().spliterator(), false).collect(Collectors.toList());
    }
}