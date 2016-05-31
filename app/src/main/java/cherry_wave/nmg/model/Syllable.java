package cherry_wave.nmg.model;

import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table
@ToString
@Getter
@Parcel
public class Syllable {

    @Unique
    String characters;
    @Setter
    boolean active = true;
    boolean startsWithVowel;

    public Syllable() {
    }

    public void setCharacters(String characters) {
        this.characters = characters;
        startsWithVowel = this.characters.matches("[aeiou].*");
    }

}
