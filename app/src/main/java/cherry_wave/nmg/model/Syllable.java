package cherry_wave.nmg.model;

import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@ToString
@Getter
@NoArgsConstructor
public class Syllable {

    private Long id;

    @Unique
    private String characters;
    @Setter
    private boolean active = true;
    private boolean startsWithVowel;

    public Syllable(String characters) {
        this.characters = characters.toLowerCase();
        startsWithVowel = this.characters.matches("[aeiou].*");
    }

    public static boolean isValid(String characters) {
        Pattern pattern = Pattern.compile("[aeiou]");
        Matcher matcher = pattern.matcher(characters);

        if (!characters.matches("[a-zA-Z]{2,}") || !matcher.find()) {
            return false;
        }
        return true;
    }

}
