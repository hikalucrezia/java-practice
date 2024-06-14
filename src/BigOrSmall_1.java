import java.util.Random;
import java.util.Scanner;

public class BigOrSmall_1 {
    public static void main(String[] args) {
        Random random = new Random();
        Scanner scanner = new Scanner(System.in);

        for (int i = 0; i < 3; i++) {
            playGame(random, scanner);
            if (i == 2) {
                break;
            }
            System.out.println(String.format("Do you want to play one more game?"));
            System.out.println("Enter 0 for No, otherwise 1");

            if (!getUserInputYesOrNo(scanner)) {
                break;
            }
        }

        System.out.println("Googbye!");
    }

    private static void playGame (Random random, Scanner scanner) {
        int randomNumber1 = 2 + random.nextInt(7);
        System.out.println(String.format("Do you think the next number is bigger than %d?", randomNumber1));
        System.out.println("Enter 0 for No, otherwise 1");

        boolean userInput = getUserInputYesOrNo(scanner);

        int randomNumber2 = 2 + random.nextInt(7);
        System.out.println(String.format("I chose %d this time", randomNumber2));

        if (randomNumber1 == randomNumber2) {
            System.out.println("Draw");
        } else {
            if (!userInput && randomNumber2 < randomNumber1) {
                System.out.println("Win!");
            } else if (userInput && randomNumber2 > randomNumber1) {
                System.out.println("Win!");
            } else {
                System.out.println("Lose..");
            }
        }
    }

    private static boolean getUserInputYesOrNo(Scanner scanner) {
        int userInput = scanner.nextInt();
        while (userInput != 0 && userInput != 1) {
            System.out.println("Please enter a valid number. 0 for No, 1 for Yes");
            userInput = scanner.nextInt();
        }
        return userInput == 1;
    }
}