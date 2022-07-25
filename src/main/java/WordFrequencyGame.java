import java.util.*;

public class WordFrequencyGame {

    public static final String SPLIT_REGEX = "\\s+";
    public static final String LINE_BREAK = "\n";

    public String getResult(String inputStr) {
        if (inputStr.split(SPLIT_REGEX).length == 1) {
            return inputStr + " 1";
        }
        try {
            //split the input string with 1 to n pieces of spaces
            String[] wordList = inputStr.split(SPLIT_REGEX);
            List<Input> inputList = new ArrayList<>();
            for (String word : wordList) {
                Input input = new Input(word, 1);
                inputList.add(input);
            }
            //get the map for the next step of sizing the same word
            Map<String, List<Input>> inputCountMap = getListMap(inputList);
            List<Input> countedList = new ArrayList<>();
            for (Map.Entry<String, List<Input>> entry : inputCountMap.entrySet()) {
                Input input = new Input(entry.getKey(), entry.getValue().size());
                countedList.add(input);
            }
            inputList = countedList;
            inputList.sort((w1, w2) -> w2.getWordCount() - w1.getWordCount());
            StringJoiner joiner = new StringJoiner(LINE_BREAK);
            for (Input word : inputList) {
                String wordOutPut = word.getValue() + " " + word.getWordCount();
                joiner.add(wordOutPut);
            }
            return joiner.toString();
        } catch (Exception e) {
            return "Calculate Error";
        }
    }

    private Map<String, List<Input>> getListMap(List<Input> inputList) {
        Map<String, List<Input>> inputCountMap = new HashMap<>();
        for (Input input : inputList) {
//       map.computeIfAbsent(input.getValue(), k -> new ArrayList<>()).add(input);
            if (!inputCountMap.containsKey(input.getValue())) {
                ArrayList inputCountList = new ArrayList<>();
                inputCountList.add(input);
                inputCountMap.put(input.getValue(), inputCountList);
            } else {
                inputCountMap.get(input.getValue()).add(input);
            }
        }
        return inputCountMap;
    }
}
