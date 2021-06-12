package pl.lodz.p.it.zzpj.thesis.utils;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.lodz.p.it.zzpj.service.thesis.utils.ThesisFilter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ThesisFilterTests {

    @SneakyThrows
    private String cleanStringProxy(final String input) {
        Method cleanString = ThesisFilter.class.getDeclaredMethod("cleanString", String.class);
        cleanString.setAccessible(true);
        return (String) cleanString.invoke(null, input);
    }

    private <T, R> boolean areEqual(Map<T, R> first, Map<T, R> second) {
        if (first.size() != second.size()) {
            return false;
        }

        return first.entrySet()
                .stream()
                .allMatch(e -> e.getValue().equals(second.get(e.getKey())));
    }

    @Test
    public void shouldReturnValidString() {
        HashMap<String, String> list = new HashMap<>();
        list.put("1234567890`=][;'/.,\\|}{:\"?><+_)(*&^%$#@!~", "");
        list.put("Hello World", "helloworld");
        list.put("TEST", "test");
        list.put("tEśT", "tet");
        list.put("", "");
        list.put("-test", "test");
        list.put("test-", "test");
        list.put("-test-", "test");

        list.forEach((wrong, correct) -> {
            Assertions.assertEquals(correct, cleanStringProxy(wrong));
        });
    }

    @Test
    public void shouldReturnFilteredHashMap() {
        HashMap<String, Map<String, Integer>> list = new HashMap<>();
        list.put("Hello World", Map.of("hello", 1, "world", 1));
        list.put("Aa bb BB aa", Map.of("aa", 2, "bb", 2));
        list.put("1234567890`=][;'/.,\\|}{:\"?><+_)(*&^%$#@!~", Map.of());
        list.put("-test-test-", Map.of("test-test", 1));

        list.forEach((thesis, correct) -> {
            var genMap = ThesisFilter.filterWord(thesis);
            Assertions.assertTrue(areEqual(correct, genMap), correct.toString());
        });
    }
}
