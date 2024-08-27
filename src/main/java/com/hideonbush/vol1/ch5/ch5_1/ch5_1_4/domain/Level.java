package com.hideonbush.vol1.ch5.ch5_1.ch5_1_4.domain;

public enum Level {
    // 세개의 이늄 오브젝트
    BASIC(1), SILVER(2), GOLD(3);

    private final int value;

    // DB에 저장할 값을 넣어줄 생성자
    Level(int value) {
        this.value = value;
    }

    // 값을 가져오는 메서드
    public int intValue() {
        return this.value;
    }

    // 값으로부터 Level 타입의 오브젝트를 가져오는 스태틱 메서드
    public static Level valueOf(int value) {
        switch (value) {
            case 1:
                return BASIC;
            case 2:
                return SILVER;
            case 3:
                return GOLD;
            default:
                throw new AssertionError("Unknown value: " + value);
        }
    }
}
