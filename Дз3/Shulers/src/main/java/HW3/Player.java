package HW3;

/**
 * Абстрактный класс игрока.
 */
abstract class Player extends Thread {
    /**
     * Статус оиждания остальных игроков.
     */
    public boolean IsWaiting = true;

    /**
     * Баланс.
     */
    Balance playerBalance;

    /**
     * Крупье.
     */
    Croupier croupier;

    /**
     * Конструктор игрока.
     *
     * @param name     Имя игрока.
     * @param croupier Крупье, ведущий игру.
     */
    public Player(String name, Croupier croupier) {
        super(name);
        this.croupier = croupier;
        this.playerBalance = new Balance();
    }

    /**
     * Выполняет ход в игре.
     *
     * @throws InterruptedException
     */
    public abstract void makeMove() throws InterruptedException;

    /**
     * Выполняет тело потока.
     */
    public void run() {
        try {
            while (IsWaiting) {
                Thread.sleep(50);
            }
            makeMove();
        } catch (InterruptedException e) {
        }
    }
}
