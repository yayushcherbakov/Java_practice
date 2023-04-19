package HW3;

/**
 * Класс баланс.
 */
public class Balance {
    /**
     * Переменная, хранящая информацию о балансе.
     */
    private int balance;

    /**
     * Конструктор баланса.
     */
    Balance() {
        balance = 0;
    }

    /**
     * Возвращает баланс.
     *
     * @return информацию о балансе.
     */
    int getBalance() {
        return balance;
    }

    /**
     * Добавляет очки на баланс.
     *
     * @param value Добавляемые очки.
     */
    void addPoints(int value) {
        balance += value;
    }

    /**
     * Вычитает очки из баланса.
     *
     * @param value Вычитаемые очки.
     */
    void stealPoints(int value) {
        balance -= value;
    }
}
