package cherry_wave.nmg.controller;

import android.content.Context;

import com.orm.SugarRecord;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Syllable;

public class SyllableUtil {

    public static final List<Syllable> getActiveSyllables(boolean shouldStartWidthVowel) {
        return SugarRecord.findWithQuery(Syllable.class, "SELECT * FROM SYLLABLE WHERE active = 1 AND starts_with_vowel = ?", shouldStartWidthVowel ? "1" : "0");
    }

    public static final String validate(Context context, Syllable syllable) {
        String characters = syllable.getCharacters();

        if (!characters.matches("[a-z]*")) {
            return context.getString(R.string.invalid_syllable_character);
        }

        if (characters.length() < 2) {
            return context.getString(R.string.invalid_syllable_length);
        }

        Pattern pattern = Pattern.compile("[aeiou]");
        Matcher matcher = pattern.matcher(characters);

        if(!matcher.find()) {
            return context.getString(R.string.invalid_syllable_vowel);
        }

        return context.getString(R.string.ok);
    }
}
