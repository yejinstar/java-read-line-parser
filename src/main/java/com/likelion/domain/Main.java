package com.likelion.domain;

import com.likelion.parser.HospitalParser;
import com.likelion.parser.LineReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        LineReader<Hospital> hospitalLineReader = new LineReader<>(new HospitalParser());
        String filename = "C:\\Users\\user\\Downloads\\seoul_hostpital_infos.csv";
        List<Hospital> hospitals = hospitalLineReader.readLines(filename);

        List<String> lines = new ArrayList<>();
        lines.add("INSERT INTO `likelion-db`.`seoul_hospital`\n" +
                "(`id`,\n" +
                "`address`,\n" +
                "`district`,\n" +
                "`category`,\n" +
                "`emergency_room`,\n" +
                "`name`,\n" +
                "`subdivision`)\n" +
                "VALUES");
        for (Hospital hospital : hospitals) {
            lines.add(hospital.getSqlInsertQuery());
            //lines.add(hospital.getSqlInsertquery1());
            lines.add(",");
        }
        lines.remove(lines.size()-1);
        String sqlFilename = "hospital_insert.sql";
        hospitalLineReader.createANewFile(sqlFilename);
        hospitalLineReader.writeLines(lines, sqlFilename);

    }
}