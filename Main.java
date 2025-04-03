import java.util.List;
import java.util.Scanner;
import java.util.Set;

public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);
    Set<String> dictionary = WordLadder.loadDictionary("words.txt"); // Change to words3.txt for testing

    while (true) {
        System.out.print("Enter start word (type Q to quit): ");
        String startWord = scanner.nextLine().toLowerCase();

        if (startWord.equals("q")) {
            System.out.println("Exiting program.");
            break;
        }

        if (startWord.length() != 5) {
            System.out.println("You must enter words that are 5 letters long.");
            continue;
        }

        System.out.print("Enter end word: ");
        String endWord = scanner.nextLine().toLowerCase();

        if (endWord.length() != 5) {
            System.out.println("You must enter words that are 5 letters long.");
            continue;
        }

        if (!dictionary.contains(startWord) || !dictionary.contains(endWord)) {
            System.out.println("Start or end word is not in the dictionary.");
            continue;
        }

        List<String> ladder = WordLadder.findWordLadder(startWord, endWord, dictionary);

        if (ladder != null) {
            System.out.println("Found ladder: " + String.join(" → ", ladder));
        } else {
            System.out.println("No word ladder exists between " + startWord + " and " + endWord);
        }
    }

    scanner.close();
}