package com.smt.soccerbetrestapi.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.smt.soccerbetrestapi.model.Team;

public class TeamCustomConverter implements DynamoDBTypeConverter<String, Team>  {

    @Override
    public String convert(Team team) {
        return team.getName();
    }

    @Override
    public Team unconvert(String s) {
        return new Team(s);
    }
}
