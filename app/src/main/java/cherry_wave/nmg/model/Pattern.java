package cherry_wave.nmg.model;

import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table
@ToString
@Getter
public class Pattern {

    private Long id;

    @Unique
    String characters;
    @Setter
    boolean active = true;

    public Pattern(String characters) {
        this.characters = characters;
    }
}
