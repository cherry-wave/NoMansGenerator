package cherry_wave.nmg.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Syllable;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class SyllablesFragment extends NMGFragment implements SyllableDialogFragment.SyllableDialogListener {

    private static final String TAG = SyllablesFragment.class.getCanonicalName();

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
        updateSyllables();
    }

    @OnClick(R.id.syllable_add)
    void editSyllable() {
        SyllableDialogFragment syllableDialog = SyllableDialogFragment.newInstance(null);
        syllableDialog.setTargetFragment(this, 0);
        syllableDialog.show(getFragmentManager(), SyllableDialogFragment.class.getCanonicalName());
    }

    @Override
    public void onSyllableSave() {
        updateSyllables();
    }

    private void updateSyllables() {
        long count = SugarRecord.count(Syllable.class);
        List<Syllable> syllables = SugarRecord.listAll(Syllable.class);
        List<String> syllablesList = new ArrayList();
        for (Syllable syllable : syllables) {
            Log.v(TAG, syllable + "");
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, syllablesList.toArray(new String[syllablesList.size()]));
        this.syllables.setAdapter(adapter);
    }
}