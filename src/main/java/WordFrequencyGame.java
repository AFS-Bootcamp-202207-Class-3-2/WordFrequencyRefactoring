import java.util.*;
import java.util.stream.Collectors;

public class WordFrequencyGame {

    public static final String SPLIT_REGEX = "\\s+";
    public static final String LINE_BREAK = "\n";
    public static final String CALCULATE_ERROR = "Calculate Error";
    public static final int DEFAULT_COUNT = 1;

    public String getResult(String inputStr) {
        if (inputStr.split(SPLIT_REGEX).length == 1) {
            return inputStr + " " + DEFAULT_COUNT;
        }
        try {
            //split the input string with 1 to n pieces of spaces
            String[] words = inputStr.split(SPLIT_REGEX);
            List<InputWord> inputWordList = Arrays.stream(words)
                    .map(word -> new InputWord(word, DEFAULT_COUNT))
                    .collect(Collectors.toList());
            //get the map for the next step of sizing the same word
            Map<String, List<InputWord>> wordCountMap = getListMap(inputWordList);
            inputWordList = getCountedWordsInOrder(wordCountMap);
            return inputWordList.stream()
                    .map(word -> word.getWord() + " " + word.getWordCount())
                    .collect(Collectors.joining(LINE_BREAK));
        } catch (Exception e) {
            return CALCULATE_ERROR;
        }
    }


    private List<InputWord> getCountedWordsInOrder(Map<String, List<InputWord>> wordCountMap) {
        List<InputWord> inputWordList;
        List<InputWord> countedList = new ArrayList<>();
        wordCountMap.entrySet().stream()
                .forEach(entry ->
                        countedList.add(new InputWord(entry.getKey(), entry.getValue().size()))
                );
        inputWordList = countedList;
        inputWordList.sort(Comparator.comparingInt(InputWord::getWordCount).reversed());
        return inputWordList;
    }

    private Map<String, List<InputWord>> getListMap(List<InputWord> inputWordList) {
        return inputWordList.stream()
                .collect(Collectors.toMap(
                        InputWord::getWord,
                        word -> new ArrayList<InputWord>(){{add(word);}},
                        (oldList, newList) -> {
                            oldList.addAll(newList);
                            return oldList;
                        }
                ));
    }
}
