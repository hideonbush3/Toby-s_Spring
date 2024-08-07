package com.hideonbush.vol1.learningtest.template;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

public class CalcSumTest {
    Calculator calculator;
    String numFilepath;

    @Before
    public void setup() {
        this.calculator = new Calculator();
        // getResource(String name)
        // 그냥 파일명 전달시 현재 클래스의 패키지 기준으로 찾는다(상대 경로)
        // '/'를 넣을시 클래스패스의 루트 디렉토리를 기준으로 찾는다(절대 경로)
        this.numFilepath = getClass().getResource("numbers.txt").getPath();
    }

    @Test
    public void sumOfNumbers() throws IOException {
        assertThat(calculator.calcSum(numFilepath), is(10));
    }

    @Test
    public void multiplyOfNumbers() throws IOException {
        assertThat(calculator.calcMultiply(numFilepath), is(24));
    }
}
