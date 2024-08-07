package com.hideonbush.vol1.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filepath) throws IOException {
        // 순수하게 계산에만 관심을 갖는 향상된 콜백
        LineCallback sumCallback = new LineCallback() {
            public Integer doSomethingWithLine(String line, Integer value) {
                return value + Integer.valueOf(line);
            }
        };
        return lineReadTemplate(filepath, sumCallback, 0);
    }

    public Integer calcMultiply(String filepath) throws IOException {
        LineCallback multiplyCallback = new LineCallback() {
            public Integer doSomethingWithLine(String line, Integer value) {
                return value * Integer.valueOf(line);
            }
        };
        return lineReadTemplate(filepath, multiplyCallback, 1);
    }

    // 템플릿
    // public Integer fileReadTemplate(String filepath, BufferedReaderCallback
    // callback)
    // throws IOException {
    // BufferedReader br = null;
    // try {
    // br = new BufferedReader(new FileReader(filepath));
    // int result = callback.doSomethingWithReader(br);
    // return result;
    // } catch (IOException e) {
    // System.out.println(e.getMessage());
    // throw e;
    // } finally {
    // if (br != null) {
    // try {
    // br.close();
    // } catch (IOException e) {
    // System.out.println(e.getMessage());
    // }
    // }
    // }
    // }

    // 콜백마다 중복되는 파일처리 코드까지 포함된 향상된 템플릿
    public Integer lineReadTemplate(String filepath, LineCallback callback, int initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            Integer res = initVal;
            String line = null;
            while ((line = br.readLine()) != null) {
                res = callback.doSomethingWithLine(line, res);
            }
            return res;
        } catch (IOException e) {
            System.out.println(e.getMessage());
            throw e;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
    }
}
