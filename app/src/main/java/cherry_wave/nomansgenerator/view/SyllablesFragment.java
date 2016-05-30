package cherry_wave.nomansgenerator.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import butterknife.BindView;
import cherry_wave.nomansgenerator.R;
import cherry_wave.nomansgenerator.model.Syllable;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SyllablesFragment extends NMGFragment {

    @BindView(R.id.syllables_list)
    ListView syllables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflate(inflater, R.layout.fragment_syllables, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Iterator<Syllable> syllableIterator = SugarRecord.findAll(Syllable.class);
        List<String> syllablesList = new ArrayList();
        while (syllableIterator.hasNext()) {
            syllablesList.add(syllableIterator.next().getCharacters());
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, syllablesList.toArray(new String[syllablesList.size()]));
        syllables.setAdapter(adapter);
    }
}