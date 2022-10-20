package com.likelion.parser;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class LineReader<T> {
    Parser<T> parser;
    boolean isRemoveColumnName = true;

    public LineReader(Parser<T> parser) {
        this.parser = parser;
    }

    public LineReader(Parser<T> parser, boolean isRemoveColumnName) {
        this.parser = parser;
        this.isRemoveColumnName = isRemoveColumnName;
    }

    public List<T> readLines(String filename) throws IOException {
        List<T> result = new ArrayList<>();
        BufferedReader br; // = new BufferedReader(new FileReader(filename));
        br = Files.newBufferedReader(Paths.get(filename), StandardCharsets.UTF_8);
        String str;
        if (isRemoveColumnName) { //첫 행 날리기
            br.readLine();
        }
        while ((str = br.readLine()) != null) {
            result.add(parser.parse(str));
        }
        return result;
    }

    public void createANewFile(String filename) throws IOException {
        File file = new File(filename);
        file.createNewFile();
        System.out.println("파일 생성 되었는지?:" + file.exists());
    }

    public void writeLines(List<String> lines, String filename) throws IOException{
        File file = new File(filename);

        try {
            BufferedWriter writer
                    = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), "utf-8"));
            for (String str : lines) {
                writer.write(str+ "\n");
            }
            writer.write(";");
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("success");
    }
}
