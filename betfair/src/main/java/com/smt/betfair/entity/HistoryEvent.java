package com.smt.betfair.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBIgnore;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@DynamoDBTable(tableName = "betfair-soccer-match-history")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class HistoryEvent {

    private String eventId;

    public HistoryEvent(long eventIdAsLong) {
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
