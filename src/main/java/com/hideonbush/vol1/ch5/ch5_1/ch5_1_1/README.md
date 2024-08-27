# enum 사용예제
```java
Level level = Level.BASIC; // Level.BASIC 상수를 사용
int levelValue = level.intValue(); // 1을 반환
System.out.println(levelValue); // 1

Level levelFromValue = Level.valueOf(2); // Level.SILVER을 반환
System.out.println(levelFromValue); // SILVER
```