package com.hideonbush.vol1.learningtest.template;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.io.IOException;

import org.junit.Test;

public class CalcSumTest {
    @Test
    public void sumOfNumbers() throws IOException {
        Calculator calculator = new Calculator();
        // getResource(현재패키지 기준 참조파일 위치)
        int sum = calculator.calcSum(getClass().getResource(
                "numbers.txt").getPath());
        assertThat(sum, is(10));
    }
}
