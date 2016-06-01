package cherry_wave.nmg.model;

import com.orm.dsl.Table;
import com.orm.dsl.Unique;

import org.parceler.Parcel;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Table
@ToString
@Getter
@Parcel
@Setter
public class Pattern {

    private Long id;

    @Unique
    String characters;
    boolean active = true;

    public Pattern() {
    }

}
