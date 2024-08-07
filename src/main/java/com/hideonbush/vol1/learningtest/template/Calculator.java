package com.hideonbush.vol1.learningtest.template;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Calculator {
    public Integer calcSum(String filepath) throws IOException {
        // 순수하게 계산에만 관심을 갖는 향상된 콜백
        LineCallback<Integer> sumCallback = new LineCallback<Integer>() {
            public Integer doSomethingWithLine(String line, Integer value) {
                return value + Integer.valueOf(line);
            }
        };
        return lineReadTemplate(filepath, sumCallback, 0);
    }

    public Integer calcMultiply(String filepath) throws IOException {
        LineCallback<Integer> multiplyCallback = new LineCallback<Integer>() {
            public Integer doSomethingWithLine(String line, Integer value) {
                return value * Integer.valueOf(line);
            }
        };
        return lineReadTemplate(filepath, multiplyCallback, 1);
    }

    public String concatenate(String filepath) throws IOException {
        LineCallback<String> concateCallback = new LineCallback<String>() {
            public String doSomethingWithLine(String line, String value) {
                return value + line;
            }
        };
        return lineReadTemplate(filepath, concateCallback, "");
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
    // 파일의 라인을 읽고 처리해서 T 타입의 결과를 만드는 범용적인 템플릿
    // 여러 타입이 가능하기 때문에 다양하게 확장 가능해졌다
    public <T> T lineReadTemplate(String filepath, LineCallback<T> callback, T initVal) throws IOException {
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(filepath));
            T res = initVal;
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
