package com.smt.soccerbetrestapi.utils;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.smt.soccerbetrestapi.model.Team;

public class TeamCustomConverter implements DynamoDBTypeConverter<String, Team> {

    @Override
    public final String convert(final Team team) {
        return team.getName();
    }
    @Override
    public final Team unconvert(final String s) {
        return new Team(s);
    }
}
