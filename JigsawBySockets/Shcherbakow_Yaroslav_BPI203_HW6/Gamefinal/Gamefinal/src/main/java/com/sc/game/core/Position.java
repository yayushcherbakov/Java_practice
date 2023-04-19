package com.sc.game.core;

public record Position(int x, int y) {
    /**
     * Координаты.
     *
     * @param x Координата X.
     * @param y Координата Y.
     */
    public Position {
    }

    /**
     * Возвращает иксовую координату.
     *
     * @return Иксовая координата.
     */
    public int getX() {
        return x;
    }

    /**
     * Возвращает игриковую координату.
     *
     * @return Игриковая координата.
     */
    public int getY() {
        return y;
    }
}
