package com.likelion.parser;

import com.likelion.domain.Hospital;
import com.likelion.parser.Parser;

public class HospitalParser implements Parser<Hospital> {

    private String getSubdivision(String name) {
        String[] subdivisions = {"내과", "외과", "소아과", "이비인후과", "피부과", "성형", "정형외과", "산부인과",
                "관절", "안과", "가정의학과", "비뇨기과", "치과"};
        for (String subdivision : subdivisions
        ) {
            if (name.contains(subdivision)) {
                return subdivision;
            }
        }
        return "";
    }

    @Override
    public Hospital parse(String str) {
        str = str.replaceAll("\"", "");
        String[] splitted = str.split(",");

        String name = splitted[10];
        String subdivision = getSubdivision(name);

        return new Hospital(splitted[0], splitted[1], splitted[2],
                Integer.parseInt(splitted[6]), splitted[10], subdivision);
    }
}
