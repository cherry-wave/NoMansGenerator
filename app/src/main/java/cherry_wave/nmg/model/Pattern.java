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
@Setter
@NoArgsConstructor
public class Pattern {

    Long id;

    @Unique
    String characters;
    boolean active = true;

    @ParcelConstructor
    public Pattern(String characters) {
        this.characters = characters;
    }

}
