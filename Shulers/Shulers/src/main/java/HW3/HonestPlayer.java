package HW3;

/**
 * Класс честного игрока.
 */
public class HonestPlayer extends Player {

    /**
     * Конструктор честного игрока.
     *
     * @param name     Имя честного игрока.
     * @param croupier Крупье честного игрока.
     */
    public HonestPlayer(String name, Croupier croupier) {
        super(name, croupier);
    }

    /**
     * Выполняет ход в игре.
     *
     * @throws InterruptedException
     */
    @Override
    public void makeMove() throws InterruptedException {
        while (true) {
            if (isInterrupted()) {
                throw new InterruptedException();
            }
            synchronized (playerBalance) {
                int gainedPoints = croupier.getCard();
                playerBalance.addPoints(gainedPoints);
                Thread.sleep(Random.getRandomNumber(100, 201));
            }
        }
    }
}
