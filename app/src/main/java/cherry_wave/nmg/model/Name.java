package cherry_wave.nmg.model;

import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@ToString
@Getter
@NoArgsConstructor
public class Name {

    private Long id;

    @Unique
    private String characters;
    @Setter
    private List<String> tags;

    public Name(String characters) {
        this.characters = characters;
    }

}
