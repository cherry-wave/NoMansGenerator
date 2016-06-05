package cherry_wave.nmg.controller;

import android.content.Context;

import com.orm.SugarRecord;

import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.regex.Matcher;

import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Pattern;

public class PatternUtil {

    private static final String TAG = PatternUtil.class.getCanonicalName();

    public static final List<Pattern> getActivePatterns() {
        return SugarRecord.findWithQuery(Pattern.class, "SELECT * FROM PATTERN WHERE active = ?", "1");
    }

    public static final String validate(Context context, Pattern pattern) {
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
            if(indexOfClose == -1) {
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

    public static final boolean hasVowelStart(Pattern pattern) {
        return pattern.getCharacters().matches("V|v");
    }

    public static final boolean hasConsonantStart(Pattern pattern) {
        return pattern.getCharacters().matches("C|c");
    }
}
