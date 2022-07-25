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
            List<InputWord> inputWordList = new ArrayList<>();
            for (String word : words) {
                inputWordList.add(new InputWord(word, DEFAULT_COUNT));
            }
            //get the map for the next step of sizing the same word
            Map<String, List<InputWord>> wordCountMap = getListMap(inputWordList);
            List<InputWord> countedList = new ArrayList<>();
            wordCountMap.entrySet().stream()
                    .forEach(entry ->
                            countedList.add(new InputWord(entry.getKey(), entry.getValue().size()))
                    );
            inputWordList = countedList;
            inputWordList.sort((w1, w2) -> w2.getWordCount() - w1.getWordCount());
            return inputWordList.stream()
                    .map(word -> word.getWord() + " " + word.getWordCount())
                    .collect(Collectors.joining(LINE_BREAK));
        } catch (Exception e) {
            return CALCULATE_ERROR;
        }
    }

    private Map<String, List<InputWord>> getListMap(List<InputWord> inputWordList) {
        Map<String, List<InputWord>> wordCountMap = new HashMap<>();
        inputWordList.stream().forEach(word ->{
            if (!wordCountMap.containsKey(word.getWord())) {
                wordCountMap.put(word.getWord(), new ArrayList<InputWord>(){{
                    add(word);
                }});
            } else {
                wordCountMap.get(word.getWord()).add(word);
            }
        });
        return wordCountMap;
    }
}
