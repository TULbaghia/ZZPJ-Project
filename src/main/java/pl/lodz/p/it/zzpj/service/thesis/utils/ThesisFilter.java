package pl.lodz.p.it.zzpj.service.thesis.utils;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class ThesisFilter {
    public static HashMap<String, Integer> filterWord(String thesis) {
        HashMap<String, Integer> wordsCount = new HashMap<>();
        List<String> words = Arrays.asList(thesis.split(" "));
        words = words.stream().map(ThesisFilter::cleanString).filter(x -> !"".equals(x) && x.length() > 1).collect(Collectors.toList());
        words.forEach(x -> {
            if (wordsCount.containsKey(x)) {
                wordsCount.put(x, wordsCount.get(x) + 1);
            } else {
                wordsCount.put(x, 1);
            }
        });
        return wordsCount;
    }

    private static String cleanString(String word) {
        String clean = word;
        if (clean != null) {
            clean = clean.replaceAll("[^a-zA-Z\\-]", "");
            clean = clean.toLowerCase();
            if (clean.startsWith("-")) {
                clean = clean.substring(1);
            }
            if (clean.endsWith("-")) {
                clean = removeLastCharRegex(clean);
            }
        }
        return clean;
    }

    private static String removeLastCharRegex(String s) {
        return (s == null) ? null : s.replaceAll(".$", "");
    }
}
