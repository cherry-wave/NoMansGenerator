package cherry_wave.nmg.model;

import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table
@ToString
@Getter
public class Tag {

    private Long id;

    @Unique
    @Setter
    String characters;

    public Tag(String characters) {
        this.characters = characters;
    }

}
