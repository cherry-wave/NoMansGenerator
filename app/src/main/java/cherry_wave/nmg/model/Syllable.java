package cherry_wave.nmg.model;

import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table
@ToString
@Getter
public class Syllable {

    private Long id;

    @Unique
    String characters;
    @Setter
    boolean active = true;
    @Setter
    boolean startsWithVowel;

    public Syllable(String characters) {
        this.characters = characters;
    }
}
