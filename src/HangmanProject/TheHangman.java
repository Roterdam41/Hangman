package HangmanProject;

import java.io.*;
import java.util.*;

public class TheHangman {
    private static boolean knock = true;
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args){
        // Логика программы
        while (knock){
            System.out.println("[N]ew game or [E]xit game");
            String NEW_OR_END_GAME = scanner.nextLine();
            if (NEW_OR_END_GAME.equalsIgnoreCase("E")){
                knockdownWhile();
            } else if (NEW_OR_END_GAME.equalsIgnoreCase("N")){
                List<String> lines = getListLines();
                int COUNT_MISTAKES = 6;

                StringBuilder USE_LETTERS = new StringBuilder("The letters you used: ");

                Random RandomIndexForLines = new Random();
                String MYSTERY_WORD = lines.get(RandomIndexForLines.nextInt(lines.size()-1)).toUpperCase();

                String CIPHER = MYSTERY_WORD.replaceAll("[^a-zA-Z]", "*");
                StringBuilder CIPHER_WORD = new StringBuilder(CIPHER);

                // Здесь я пишу логику программы
                while (COUNT_MISTAKES != 0 && (!MYSTERY_WORD.toString().equals(CIPHER_WORD))){
                    if (MYSTERY_WORD.toString().equals(CIPHER_WORD.toString())){
                        System.out.println("You winner!\n");
                        break;
                    }

                    System.out.println("Enter a letter: ");
                    String litter = scanner.nextLine().toUpperCase();
                    USE_LETTERS.append(litter).append(" ");

                    if (MYSTERY_WORD.contains(litter)){
                        // INDEX_IN_WORD -> List индексов на месте которых должны стоять буквы в зашифрованом слове, если она там есть
                        List<Integer> INDEX_IN_WORD = new ArrayList<>();
                        for (int i = 0; i < MYSTERY_WORD.length(); i++){
                            if (String.valueOf(MYSTERY_WORD.charAt(i)).equalsIgnoreCase(litter)){
                                INDEX_IN_WORD.add(i);
                            }
                        }
                        char charWord = litter.charAt(0);
                        for (int i = 0; i < INDEX_IN_WORD.size(); i++){
                            CIPHER_WORD.setCharAt(INDEX_IN_WORD.get(i), charWord);
                        }
                        System.out.println();
                        System.out.println(CIPHER_WORD);
                    } else {
                        COUNT_MISTAKES-=1;
                        if (COUNT_MISTAKES != 0) {
                            System.out.println(CIPHER_WORD);
                            System.out.printf("You have %d mistakes left \n", COUNT_MISTAKES);
                        }
                        printStateHangman(COUNT_MISTAKES, MYSTERY_WORD);
                    }
                    if (COUNT_MISTAKES != 0) {
                        System.out.println(USE_LETTERS);
                    }
                }
            } else {
                System.out.println("ERROR! Select ->");
            }
        }
    }
    // Метод которые вызывается при условии, что пользователь закончит игру и остановит цикл while
    public static void knockdownWhile(){
        knock = false;
    }
    public static List<String> getListLines(){
        List<String> lines = new ArrayList<>();
        try(Scanner sc = new Scanner(new File("WordsForHangman.txt"))){
            while (sc.hasNextLine()) {
                String word = sc.nextLine();
                if (word.length() > 5) {
                    lines.add(word);
                }
            }
        } catch (FileNotFoundException e){
            e.printStackTrace();
            System.exit(0);
        }
        return lines;
    }

    public static void printStateHangman(int COUNT_MISTAKES, String MYSTERY_WORD){
        switch (COUNT_MISTAKES){
            case 5:
                System.out.println("|\n|\n|\n|\n|\n|\n");
                break;
            case 4:
                System.out.println(" ___\n|/  |\n|\n|\n|\n|\n|");
                break;
            case 3:
                System.out.println(" ___\n|/  |\n|   *\n|\n|\n|\n|");
                break;
            case 2:
                System.out.println(" ___\n|/  |\n|   *\n|  /|\\ \n|\n|\n|");
                break;
            case 1:
                System.out.println(" ___\n|/  |\n|   *\n|  /|\\ \n|   |\n|\n|");
                break;
            case 0:
                System.out.println("You lost and hung a person :(");
                System.out.printf("The target word was [%s] \n \n", MYSTERY_WORD);
                break;
        }
    }
}
