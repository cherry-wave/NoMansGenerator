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
@Setter
@Parcel
@NoArgsConstructor
public class Name {

    Long id;

    @Unique
    String characters;
    List<String> tags;

    @ParcelConstructor
    public Name(String characters) {
        this.characters = characters;
    }

}
