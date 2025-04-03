import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class WordLadder {

    /**
     * Loads the dictionary from a file into a Set.
     *
     * @param filename The name of the dictionary file.
     * @return A Set containing all the words in the dictionary.
     */
    public static Set<String> loadDictionary(String filename) {
        Set<String> dictionary = new HashSet<>();
        try (Scanner fileScanner = new Scanner(new File(filename))) {
            while (fileScanner.hasNextLine()) {
                String word = fileScanner.nextLine().trim().toLowerCase();
                if (word.length() == 5) {
                    dictionary.add(word);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Error: Dictionary file not found.");
            System.exit(1);
        }
        return dictionary;
    }

    /**
     * Finds a word ladder between two words using a Queue of Stacks.
     *
     * @param startWord The starting word.
     * @param endWord   The target word.
     * @param dictionary The Set of valid words.
     * @return A List representing the word ladder, or null if no ladder exists.
     */
    public static List<String> findWordLadder(String startWord, String endWord, Set<String> dictionary) {
        Queue<Stack<String>> queue = new LinkedList<>();
        Set<String> usedWords = new HashSet<>();

        // Initialize the queue with a stack containing the start word
        Stack<String> initialStack = new Stack<>();
        initialStack.push(startWord);
        queue.add(initialStack);
        usedWords.add(startWord);

        while (!queue.isEmpty()) {
            Stack<String> currentStack = queue.poll();
            String currentWord = currentStack.peek();

            // If the current word is the end word, return the ladder
            if (currentWord.equals(endWord)) {
                return new ArrayList<>(currentStack);
            }

            // Generate all possible one-letter variations of the current word
            for (String neighbor : getOneLetterVariations(currentWord, dictionary)) {
                if (!usedWords.contains(neighbor)) {
                    // Create a new stack with the current ladder and the new word
                    Stack<String> newStack = new Stack<>();
                    newStack.addAll(currentStack);
                    newStack.push(neighbor);

                    // Add the new stack to the queue
                    queue.add(newStack);
                    usedWords.add(neighbor);
                }
            }
        }

        // If no ladder is found, return null
        return null;
    }

    /**
     * Generates all valid words that are one letter different from the given word.
     *
     * @param word       The word to generate variations for.
     * @param dictionary The Set of valid words.
     * @return A List of valid one-letter variations.
     */
    public static List<String> getOneLetterVariations(String word, Set<String> dictionary) {
        List<String> variations = new ArrayList<>();
        char[] wordArray = word.toCharArray();

        for (int i = 0; i < wordArray.length; i++) {
            char originalChar = wordArray[i];
            for (char c = 'a'; c <= 'z'; c++) {
                if (c != originalChar) {
                    wordArray[i] = c;
                    String newWord = new String(wordArray);
                    if (dictionary.contains(newWord)) {
                        variations.add(newWord);
                    }
                }
            }
            wordArray[i] = originalChar; // Restore the original character
        }

        return variations;
    }
}