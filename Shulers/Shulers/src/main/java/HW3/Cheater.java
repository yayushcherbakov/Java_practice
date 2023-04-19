package HW3;

import java.util.Collections;
import java.util.stream.Collectors;

/**
 * Класс шулера.
 */
public class Cheater extends Player {

    /**
     * Конструктор шулера.
     *
     * @param name     Имя шулера.
     * @param croupier Крупье шулера.
     */
    public Cheater(String name, Croupier croupier) {
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

            if (Random.getRandomNumber(0, 11) <= 4) {
                steal();
                Thread.sleep(Random.getRandomNumber(180, 301));
            } else {
                synchronized (playerBalance) {
                    int gainedPoints = croupier.getCard();
                    playerBalance.addPoints(gainedPoints);
                    Thread.sleep(Random.getRandomNumber(100, 201));
                }
            }

        }
    }

    /**
     * Шулер ворует карты с баланса честного игрока.
     */
    public void steal() {
        var honestPlayers = croupier.players.stream().filter(p -> p instanceof HonestPlayer).collect(Collectors.toList());
        Collections.shuffle(honestPlayers);
        Player ChoosePlayer = honestPlayers.get(0);

        synchronized (ChoosePlayer.playerBalance) {
            int expectedStolenPoints = Random.getRandomNumber(0, 9);
            int honestPlayerPoints = ChoosePlayer.playerBalance.getBalance();
            int pointsToStole = honestPlayerPoints >= expectedStolenPoints ? expectedStolenPoints : honestPlayerPoints;

            ChoosePlayer.playerBalance.stealPoints(pointsToStole);
            this.playerBalance.addPoints(pointsToStole);
        }
    }
}
