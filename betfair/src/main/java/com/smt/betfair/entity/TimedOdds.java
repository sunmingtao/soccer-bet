package com.smt.betfair.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverted;
import com.smt.betfair.converter.MatchOddsDateCustomConverter;
import lombok.*;
import org.apache.commons.lang3.time.FastDateFormat;

import java.util.Date;

@Setter
@Getter
@DynamoDBTable(tableName = "betfair-soccer-match-odds")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class TimedOdds {

    private String id;
    private int eventId;
    private String eventName;
    private double winOdds;
    private double lossOdds;
    private double drawOdds;
    private Date dateTime;

    @DynamoDBHashKey
    @EqualsAndHashCode.Include
    public String getId() {
        return id;
    }

    @DynamoDBTypeConverted(converter = MatchOddsDateCustomConverter.class)
    public Date getDateTime() {
        return dateTime;
    }
}
