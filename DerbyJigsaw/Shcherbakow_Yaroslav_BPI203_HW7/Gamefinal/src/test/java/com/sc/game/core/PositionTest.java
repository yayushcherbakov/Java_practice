package com.sc.game.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class PositionTest {

    @Test
    void getX() {
        int x = 2, y = 3;
        Position position = new Position(x, y);
        Assertions.assertEquals(x, position.getX());
    }

    @Test
    void getY() {
        int x = 2, y = 3;
        Position position = new Position(x, y);
        Assertions.assertEquals(y, position.getY());
    }
}