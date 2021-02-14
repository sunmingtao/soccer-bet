package com.smt.betfair.converter;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverter;
import lombok.SneakyThrows;
import org.apache.commons.lang3.time.FastDateFormat;

import java.text.ParseException;
import java.util.Date;

public class MatchOddsDateCustomConverter implements DynamoDBTypeConverter<String, Date> {

    private static final FastDateFormat DATE_FORMATTER = FastDateFormat.getInstance("yyyy/MM/dd HH:mm");

    @Override
    public String convert(Date date) {
        return DATE_FORMATTER.format(date);
    }

    @Override
    @SneakyThrows(ParseException.class)
    public Date unconvert(String s) {
        return DATE_FORMATTER.parse(s);
    }
}
