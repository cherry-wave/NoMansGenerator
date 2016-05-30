package cherry_wave.nmg.view;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternValidator {

    private static final Pattern PATTERN = Pattern.compile("\\{[XxVvCc][1-9](-[2-9])?\\}");

    public static final boolean isValid(String pattern) {
        Matcher matcher = PATTERN.matcher(pattern);
        return matcher.matches();
    }

}
