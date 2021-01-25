package com.smt.nba.model;

import com.opencsv.bean.CsvBindByName;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class MoneyLine {
    @CsvBindByName(column = "game_id")
    private String gameId;

    @CsvBindByName(column = "book_name")
    private String bookName;

    @CsvBindByName(column = "book_id")
    private String bookId;

    @CsvBindByName(column = "team_id")
    private String teamId1;

    @CsvBindByName(column = "a_team_id")
    private String teamId2;

    @CsvBindByName
    private double price1;

    @CsvBindByName
    private double price2;

}
