package cherry_wave.nomansgenerator.model;

import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table
@ToString
@Getter
public class Name {

    private Long id;

    @Unique
    @Setter
    String characters;
    @Setter
    List<Tag> tags;

}
