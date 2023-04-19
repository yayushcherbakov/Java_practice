package HW3;

/**
 * Класс рандом.
 */
public class Random {
    /**
     * Возвращает случайное значение из указанного диапазона.
     *
     * @param min Нижняяя граница.
     * @param max Верхняя граница.
     * @return Число из указанного диапазона.
     */
    static public int getRandomNumber(int min, int max) {
        return (int) ((Math.random() * (max - min)) + min);
    }
}
