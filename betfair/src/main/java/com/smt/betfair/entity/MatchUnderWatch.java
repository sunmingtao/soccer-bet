package com.smt.betfair.entity;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBHashKey;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTable;
import lombok.*;

@Setter
@Getter
@DynamoDBTable(tableName = "betfair-soccer-match")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class MatchUnderWatch {

    private long eventId;

    @DynamoDBHashKey
    @EqualsAndHashCode.Include
    public long getEventId() {
        return eventId;
    }
}
