package tn.esprit.ecocycletech.Service.Notifications;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.regex.*;
import java.util.stream.*;

@Service
public class SmartBadWordFilterService {

    @Value("${app.bad-words}")
    private String badWordsProperty;

    private List<Pattern> badWordPatterns;

    /** common “leet” substitutions */
    private static final Map<Character,String> LEET = Map.of(
            'a', "a4@",
            'e', "e3",
            'i', "i1!|",
            'o', "o0",
            's', "s5$",
            't', "t7+"
    );

    @PostConstruct
    public void init() {
        List<String> words = Arrays.stream(badWordsProperty.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());

        badWordPatterns = words.stream()
                .map(this::buildPatternFor)
                .collect(Collectors.toList());
    }

    /** Build a fuzzy regex for one bad word */
    private Pattern buildPatternFor(String word) {
        StringBuilder rx = new StringBuilder();
        rx.append("(?i)");              // case-insensitive
        for (char c : word.toCharArray()) {
            String variants = LEET.getOrDefault(
                    Character.toLowerCase(c),
                    String.valueOf(c)
            );
            // build character‐class, e.g. [sS5\$]
            String charClass = variants.chars()
                    .mapToObj(ch -> Pattern.quote(Character.toString((char)ch)))
                    .collect(Collectors.joining());
            rx.append("(?:[").append(charClass).append("]+)");
            // allow any non‐word chars in between (punctuation/spaces)
            rx.append("\\W*?");
        }
        return Pattern.compile(rx.toString());
    }

    /** Returns true if any forbidden word is found */
    public boolean containsBadWords(String input) {
        if (input == null) return false;
        return badWordPatterns.stream()
                .anyMatch(p -> p.matcher(input).find());
    }

    /**
     * Replaces every occurrence of a bad‐word (or its leet variant)
     * with the same number of '*' as the matched characters.
     */
    public String sanitize(String input) {
        if (input == null) return null;
        String result = input;
        for (Pattern p : badWordPatterns) {
            Matcher m = p.matcher(result);
            StringBuffer sb = new StringBuffer();
            while (m.find()) {
                String stars = "*".repeat(m.group().length());
                m.appendReplacement(sb, stars);
            }
            m.appendTail(sb);
            result = sb.toString();
        }
        return result;
    }
}
