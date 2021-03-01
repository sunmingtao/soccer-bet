package com.smt.betfair.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@Setter
@Getter
@DynamoDBTable(tableName = "betfair-soccer-match")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MatchUnderWatch {

    private String eventId;

    public MatchUnderWatch (long eventIdAsLong) {
        this.eventId = String.valueOf(eventIdAsLong);
    }

    @DynamoDBHashKey
    @EqualsAndHashCode.Include
    public String getEventId() {
        return eventId;
    }

    @DynamoDBIgnore
    public long getEventIdAsLong() {
        return Long.parseLong(eventId);
    }
}
