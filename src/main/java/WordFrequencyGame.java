import java.util.*;

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
                InputWord inputWord = new InputWord(word, 1);
                inputWordList.add(inputWord);
            }
            //get the map for the next step of sizing the same word
            Map<String, List<InputWord>> inputCountMap = getListMap(inputWordList);
            List<InputWord> countedList = new ArrayList<>();
            for (Map.Entry<String, List<InputWord>> entry : inputCountMap.entrySet()) {
                InputWord inputWord = new InputWord(entry.getKey(), entry.getValue().size());
                countedList.add(inputWord);
            }
            inputWordList = countedList;
            inputWordList.sort((w1, w2) -> w2.getWordCount() - w1.getWordCount());
            StringJoiner joiner = new StringJoiner(LINE_BREAK);
            for (InputWord word : inputWordList) {
                String wordOutPut = word.getWord() + " " + word.getWordCount();
                joiner.add(wordOutPut);
            }
            return joiner.toString();
        } catch (Exception e) {
            return CALCULATE_ERROR;
        }
    }

    private Map<String, List<InputWord>> getListMap(List<InputWord> inputWordList) {
        Map<String, List<InputWord>> inputCountMap = new HashMap<>();
        for (InputWord inputWord : inputWordList) {
            if (!inputCountMap.containsKey(inputWord.getWord())) {
                ArrayList inputCountList = new ArrayList<>();
                inputCountList.add(inputWord);
                inputCountMap.put(inputWord.getWord(), inputCountList);
            } else {
                inputCountMap.get(inputWord.getWord()).add(inputWord);
            }
        }
        return inputCountMap;
    }
}
