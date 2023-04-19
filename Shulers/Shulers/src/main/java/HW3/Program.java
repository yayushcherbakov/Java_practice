package HW3;

import java.util.Scanner;

public class Program {

    public static void main(String[] args) throws InterruptedException {
        System.out.println("Введите количество честных игроков от 1 до 1000:");
        Scanner in = new Scanner(System.in);

        int honestPlayers = getPlayersCount(in, 1, 1000);
        if (honestPlayers == -1) {
            System.out.println("Честных игроков должно быть не менее 1 и не более 1000.");
            return;
        }
        System.out.println("Введите количество шулеров от 0 до 1000:");
        int cheaters = getPlayersCount(in, 0, 1000);
        if (cheaters == -1) {
            System.out.println("Шулкров должно быть не менее 0 и не более 1000.");
            return;
        }
        Croupier croupier = new Croupier(honestPlayers, cheaters);
        System.out.println("Начинаем игру!");
        croupier.startGame();
    }

    /**
     * Запрашивает у пользователя и возвращает количество игроков.
     *
     * @param in  Переменная для записи считываемого значения с консоли.
     * @param min Нижняя граница игроков.
     * @param max Верхняя граница игроков.
     * @return Количество игроков.
     */
    private static int getPlayersCount(Scanner in, int min, int max) {
        int count;
        try {
            count = in.nextInt();
            if (count < min || count > max) {
                count = -1;
            }
        } catch (Exception ex) {
            count = -1;
        }
        return count;
    }
}