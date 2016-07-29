package cherry_wave.nmg.controller;

import android.content.Context;

import com.orm.query.Condition;
import com.orm.query.Select;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Random;
import java.util.regex.Matcher;

import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Pattern;

public class PatternUtils {

    public enum Start {
        CONSONANT,
        VOWEL;
    }

    public static List<Pattern> getActivePatterns() {
        return Select.from(Pattern.class)
                .where(Condition.prop("active").eq(1))
                .list();
    }

    public static String validate(Context context, Pattern pattern) {
        String characters = pattern.getCharacters();

        java.util.regex.Pattern regexPattern = java.util.regex.Pattern.compile("(\\{.*\\})+");
        Matcher matcher = regexPattern.matcher(characters);

        if (!matcher.find()) {
            return context.getString(R.string.invalid_pattern_no_braces);
        }

        if (StringUtils.countMatches(characters, "{") != StringUtils.countMatches(characters, "}")) {
            return context.getString(R.string.invalid_pattern_uneven_braces);
        }

        String[] subPatterns = characters.split("\\{");
        for (String subPattern : subPatterns) {
            int indexOfClose = subPattern.indexOf('}');
            if (indexOfClose == -1) {
                continue;
            }
            subPattern = subPattern.substring(0, indexOfClose);

            if (!subPattern.matches("[XxVvCc].*")) {
                return context.getString(R.string.invalid_pattern_start);
            }

            if (!subPattern.matches("[XxVvCc][1-9].*")) {
                return context.getString(R.string.invalid_pattern_length);
            }

            if (!subPattern.matches("[XxVvCc][1-9](-[1-9])?")) {
                return context.getString(R.string.invalid_pattern_range);
            }
        }

        return context.getString(R.string.ok);
    }

    public static boolean containsStart(List<Pattern> patterns, Start start) {
        java.util.regex.Pattern anyPattern = java.util.regex.Pattern.compile("[Xx]");
        java.util.regex.Pattern specificPattern = java.util.regex.Pattern.compile("[Cc]");
        if(Start.VOWEL.equals(start)) {
            specificPattern = java.util.regex.Pattern.compile("[Vv]");
        }
        for (Pattern pattern : patterns) {
            Matcher matcher = anyPattern.matcher(pattern.getCharacters());
            if(!matcher.find()) {
                matcher = specificPattern.matcher(pattern.getCharacters());
                if(!matcher.find()) {
                    return false;
                }
            }
        }
        return true;
    }

    public static boolean containsStart(Pattern pattern, Start start) {
        java.util.regex.Pattern anyPattern = java.util.regex.Pattern.compile("[Xx]");
        java.util.regex.Pattern specificPattern = java.util.regex.Pattern.compile("[Cc]");
        if(Start.VOWEL.equals(start)) {
            specificPattern = java.util.regex.Pattern.compile("[Vv]");
        }
        Matcher matcher = anyPattern.matcher(pattern.getCharacters());
        if(!matcher.find()) {
            matcher = specificPattern.matcher(pattern.getCharacters());
            if(!matcher.find()) {
                return false;
            }
        }
        return true;
    }

    public static boolean startsWith(String subPattern, Start start) {
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile("[Cc].*");
        if(Start.VOWEL.equals(start)) {
            pattern = java.util.regex.Pattern.compile("[Vv].*");
        }
        Matcher matcher = pattern.matcher(subPattern);
        return matcher.matches();
    }

    public static boolean startsWithUppercase(String subPattern) {
        char start = subPattern.charAt(0);
        return Character.getType(start) == Character.UPPERCASE_LETTER;
    }

    public static int getRangeFrom(String subPattern) {
        return Character.getNumericValue(subPattern.charAt(1));
    }

    public static int getRangeTo(String subPattern) {
        int from = getRangeFrom(subPattern);
        if(subPattern.length() == 2) {
            return from;
        }
        int to = Character.getNumericValue(subPattern.charAt(3));
        return new Random().nextInt((to - from) + 1) + from;
    }
}
