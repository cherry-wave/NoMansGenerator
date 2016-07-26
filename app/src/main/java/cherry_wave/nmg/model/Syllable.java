package cherry_wave.nmg.model;

import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@ToString
@Getter
@Parcel
@NoArgsConstructor
public class Syllable {

    Long id;

    @Unique
    String characters;
    @Setter
    boolean active = true;
    boolean startsWithVowel;

    @ParcelConstructor
    public Syllable(String characters) {
        setCharacters(characters);
    }

    public void setCharacters(String characters) {
        this.characters = characters;
        startsWithVowel = this.characters.matches("[aeiou].*");
    }

}
