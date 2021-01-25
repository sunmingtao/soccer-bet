package com.smt.nba;

import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import com.opencsv.bean.HeaderColumnNameMappingStrategy;
import com.smt.nba.model.MoneyLine;
import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class CsvReader {

    public static void main(String[] args) throws Exception {
        Path path = Paths.get(
                ClassLoader.getSystemResource("nba_betting_money_line.csv").toURI());
        System.out.println(beanBuilderExample(path, MoneyLine.class));
    }

    @SneakyThrows(IOException.class)
    public static <T> List<T> beanBuilderExample(Path path, Class<T> clazz) {
        try (BufferedReader br = Files.newBufferedReader(path, StandardCharsets.UTF_8)) {
            HeaderColumnNameMappingStrategy<T> strategy = new HeaderColumnNameMappingStrategy<>();
            strategy.setType(clazz);
            CsvToBean<T> csvToBean = new CsvToBeanBuilder<T>(br)
                    .withMappingStrategy(strategy)
                    .withIgnoreLeadingWhiteSpace(true)
                    .build();

            return csvToBean.parse();
        }
    }

}
