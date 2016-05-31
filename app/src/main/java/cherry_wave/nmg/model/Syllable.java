package cherry_wave.nmg.model;

import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@ToString
@Getter
@Parcel
public class Syllable {

    Long id;

    @Unique
    String characters;
    @Setter
    boolean active = true;
    boolean startsWithVowel;

    public Syllable() {
    }

    public void setCharacters(String characters) {
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
