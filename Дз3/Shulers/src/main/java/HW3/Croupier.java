package HW3;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Класс крупье.
 */
public class Croupier {
    /**
     * Список игроков.
     */
    public ArrayList<Player> players = new ArrayList<Player>();

    /**
     * Колода карт.
     */
    CardsDeck cardsDeck;

    /**
     * Конструктор крупье.
     *
     * @param honestPlayerCount Количество честных игроков.
     * @param cheatersCount     Количество шулеров.
     */
    Croupier(int honestPlayerCount, int cheatersCount) {
        cardsDeck = new CardsDeck();

        for (int i = 0; i < honestPlayerCount; ++i) {
            var hp = new HonestPlayer("Honest-player-" + i, this);
            players.add(hp);
        }

        for (int i = 0; i < cheatersCount; ++i) {
            players.add(new Cheater("Cheater-" + i, this));
        }
    }

    /**
     * Выдаёт карту.
     *
     * @return Число от 1 до 10.
     * @throws InterruptedException
     */
    public int getCard() throws InterruptedException {
        synchronized (cardsDeck) {
            return cardsDeck.getCard();
        }
    }

    /**
     * Начинает игру.
     *
     * @throws InterruptedException
     */
    public void startGame() throws InterruptedException {
        for (var player : players) {
            player.start();
        }

        for (var player : players) {
            player.IsWaiting = false;
        }

        Thread.sleep(10000);

        for (var player : players) {
            player.interrupt();
        }

        while (!isAllPlayersCalmedDown()) {
            Thread.sleep(200);
        }

        displayBalanceTable();
        displayWinners();
    }

    /**
     * Проверка на то, что все игроки успокоились.
     *
     * @return Результат проверки.
     */
    public boolean isAllPlayersCalmedDown() {
        for (var player : players) {
            if (player.isAlive()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Выводит на экран победителей.
     */
    private void displayWinners() {
        Comparator<Player> comparator = Comparator.comparing(obj -> obj.playerBalance.getBalance());
        Collections.sort(players, comparator);
        int winBalance = players.get(players.size() - 1).playerBalance.getBalance();
        for (var player : players) {
            if (player.playerBalance.getBalance() == winBalance) {
                System.out.println("Победил " + player.getName());
            }
        }
    }

    /**
     * Выводит на экран балансы участников.
     */
    private void displayBalanceTable() {
        for (var player : players) {
            System.out.println("Баланс игрока " + player.getName() + ": " + player.playerBalance.getBalance());
        }
    }
}
