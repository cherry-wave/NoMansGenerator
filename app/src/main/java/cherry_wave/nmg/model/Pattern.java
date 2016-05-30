package cherry_wave.nmg.model;

import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@ToString
@Getter
@NoArgsConstructor
public class Pattern {

    private Long id;

    @Unique
    private String characters;
    @Setter
    private boolean active = true;

    public Pattern(String characters) {
        this.characters = characters;
    }

    public static final boolean isValid(String pattern) {
        return pattern.matches("\\{[XxVvCc][1-9](-[2-9])?\\}");
    }

}
