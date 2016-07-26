package cherry_wave.nmg.controller;

import android.content.Context;

import com.orm.SugarRecord;
import com.orm.query.Condition;
import com.orm.query.Select;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Syllable;

public class SyllableUtils {

    public static List<Syllable> getActiveSyllables(boolean shouldStartWidthVowel) {
        return Select.from(Syllable.class)
                .where(Condition.prop("active").eq(1),
                        Condition.prop("starts_with_vowel").eq(shouldStartWidthVowel ? 1 : 0))
                .list();
    }

    public static String validate(Context context, Syllable syllable) {
        String characters = syllable.getCharacters();

        if (characters.length() < 2) {
            return context.getString(R.string.invalid_syllable_length);
        }

        if (!characters.matches("[a-z]*")) {
            return context.getString(R.string.invalid_syllable_character);
        }

        Pattern pattern = Pattern.compile("[aeiou]");
        Matcher matcher = pattern.matcher(characters);

        if (!matcher.find()) {
            return context.getString(R.string.invalid_syllable_vowel);
        }

        return context.getString(R.string.ok);
    }
}
