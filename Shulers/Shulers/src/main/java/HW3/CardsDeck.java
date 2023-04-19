package HW3;

/**
 * Класс колода карт.
 */
public class CardsDeck {
    /**
     * Выдает случайную карту в диапазоне от 1 до 10.
     *
     * @return Число от 1 до 10.
     */
    int getCard() {
        return Random.getRandomNumber(1, 11);
    }
}
