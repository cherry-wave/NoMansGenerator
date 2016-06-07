package cherry_wave.nmg.model;

import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import org.parceler.Parcel;
import org.parceler.ParcelConstructor;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table
@ToString
@Getter
@Parcel
@NoArgsConstructor
public class Name {

    private Long id;

    @Unique
    private String characters;
    @Setter
    private List<String> tags;

    @ParcelConstructor
    public Name(String characters) {
        this.characters = characters;
    }

}
