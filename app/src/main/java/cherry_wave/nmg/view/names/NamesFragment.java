package cherry_wave.nmg.view.names;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Name;
import cherry_wave.nmg.NMGFragment;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NamesFragment extends NMGFragment {
    
    @BindView(R.id.names_list)
    ListView names;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflate(inflater, R.layout.fragment_names, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updatePatterns();
    }

    public void updatePatterns() {
        List<Name> names = SugarRecord.listAll(Name.class, "characters");
        List<String> namesList = new ArrayList();
        for (Name name : names) {
            namesList.add(name.getCharacters());
        }
        ArrayAdapter<String> namesAdapter = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, namesList);
        this.names.setAdapter(namesAdapter);
    }
}