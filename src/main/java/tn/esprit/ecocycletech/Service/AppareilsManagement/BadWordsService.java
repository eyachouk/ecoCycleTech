package tn.esprit.ecocycletech.Service.AppareilsManagement;

import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BadWordsService {

    private final Set<String> badWords = new HashSet<>();

    public BadWordsService() {
        badWords.add("shit");
        badWords.add("fuck");
        badWords.add("stupid");
    }

    public void addBadWord(String word) {
        badWords.add(word.toLowerCase());
    }

    public void removeBadWord(String word) {
        badWords.remove(word.toLowerCase());
    }

    public String filter(String content) {
        String filtered = content;

        // Parcourir chaque mot de la liste et le filtrer dans le texte
        for (String word : badWords) {
            String regex = "(?i)\\b" + Pattern.quote(word) + "\\b"; // Case insensitive & whole word match
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(filtered);

            // Remplacer chaque occurrence du mauvais mot par des astérisques de même longueur
            while (matcher.find()) {
                String replacement = "*".repeat(matcher.group().length());
                filtered = filtered.replace(matcher.group(), replacement);
            }
        }

        return filtered;
    }
}
