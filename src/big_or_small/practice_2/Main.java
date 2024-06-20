package big_or_small.practice_2;

import java.util.Random;
import java.util.Scanner;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        BigOrSmall game = new BigOrSmall();
        System.out.println("ゲームを開始します。");
        game.play();
        System.out.println("ゲームを終了します。");
    }
}

class BigOrSmall {
    private int remainingGames;
    private int currentNumber;
    private int score;
    private int winningStreak;

    public BigOrSmall() {
        RandomNumberGenerator random = new RandomNumberGenerator();

        currentNumber = random.generate(1, 9);
        remainingGames = 10;
        score = 0;
        winningStreak = 0;
    }

    public void play() {
        while (true) {
            BigOrSmallGame game = new BigOrSmallGame(currentNumber);
            System.out.println(String.format("残りのゲーム回数: %d", remainingGames));
            System.out.println(String.format("現在のスコア: %d", score));

            GameResult result = game.play();
            if (result == GameResult.WIN) {
                winningStreak++;
                score += 100 * winningStreak;
                remainingGames--;
            } else if (result == GameResult.DEFEAT) {
                winningStreak = 0;
                remainingGames--;
            }

            if (remainingGames == 0) {
                System.out.println("GAME OVER");
                System.out.println(String.format("最終スコア: %d", score));
                writeToLog(String.format(
                    "score: %s\ndate: %s",
                    score,
                    java.time.LocalDateTime.now()
                ));
                break;
            }

            currentNumber = game.nextNumber;
        }
    }

    private void writeToLog(String message) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("log.txt", true))) {
            writer.write(message);
            writer.newLine();
        } catch (IOException e) {
            System.err.println("An error occurred while writing to the log file.");
            e.printStackTrace();
        }
    }
}

enum GameResult {
    DEFEAT,
    WIN,
    DRAW
}

class BigOrSmallGame {
    private Player player;
    private RandomNumberGenerator random;
    private int currentNumber;
    public int nextNumber;

    public BigOrSmallGame(int _currentNumber) {
        player = new Player();
        random = new RandomNumberGenerator();
        currentNumber = _currentNumber;
        nextNumber = random.generate(1, 9);
    }

    public GameResult play() {
        System.out.println(String.format("次の数字は%dより大きいと思いますか？", currentNumber));
        System.out.println("入力してください。(高い: 0, 低い: 1)");
        int userInput = player.askNumber();
        System.out.println(String.format("あなたの予測: %s", (userInput == 1 ? "Small" : "Big")));
        System.out.println(String.format("現在の数字: %d", currentNumber));
        System.out.println(String.format("次の数字: %d", nextNumber));
        
        if (currentNumber == nextNumber) {
            System.out.println("Draw");
            System.out.println("Draw!");
            return GameResult.DRAW;
        } else if (nextNumber < currentNumber) {
            System.out.println("Small");

            if (userInput == 1) {
                System.out.println("当たり！");
                return GameResult.WIN;
            } else {
                return GameResult.DEFEAT;
            }
        } else {
            System.out.println("Big");

            if (userInput == 0) {
                System.out.println("当たり！");
                return GameResult.WIN;
            } else {
                return GameResult.DEFEAT;
            }
        }
    }
}

class RandomNumberGenerator {
    private Random random;

    public RandomNumberGenerator() {
        random = new Random();
    }

    public int generate(int min, int max) {
        return min + random.nextInt(max);
    }
}

class Player {
    private Scanner scanner;

    public Player() {
        scanner = new Scanner(System.in);
    }

    public int askNumber() {
        while (true) {
            try {
                int userInput = scanner.nextInt();
                if (userInput == 0 || userInput == 1) {
                    return userInput;
                } else {
                    System.out.println("有効な数字を入力してください");
                }
            } catch (InputMismatchException e) {
                System.out.println("有効な数字を入力してください");
                scanner.next();
            }
        }
    }
}