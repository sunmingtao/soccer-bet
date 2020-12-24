package com.smt.soccerbetrestapi.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import com.smt.soccerbetrestapi.utils.SeasonUtils;

import java.util.Date;

public class MatchDateCustomConverter implements DynamoDBTypeConverter<String, Date>  {

    @Override
    public String convert(Date date) {
        return SeasonUtils.toFullDateString(date);
    }

    @Override
    public Date unconvert(String s) {
        return SeasonUtils.fromFullDateString(s);
    }
}
