package cherry_wave.nmg.view.syllables;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.orm.SugarRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Syllable;
import cherry_wave.nmg.NMGFragment;

public class SyllablesFragment extends NMGFragment {

    private static final String TAG = SyllablesFragment.class.getCanonicalName();

    @BindView(R.id.syllables_list)
    ListView syllables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflate(inflater, R.layout.fragment_syllables, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateSyllables();
    }

    @OnClick(R.id.syllable_add)
    void addSyllable() {
        editSyllable(null);
    }

    public void editSyllable(Syllable syllable) {
        SyllableSaveFragment syllableSaveDialog = SyllableSaveFragment.newInstance(syllable);
        syllableSaveDialog.setTargetFragment(this, 0);
        syllableSaveDialog.show(getFragmentManager(), SyllableSaveFragment.class.getCanonicalName());
    }

    public void updateSyllables() {
        List<Syllable> syllables = SugarRecord.listAll(Syllable.class, "characters");
        SyllablesAdapter syllablesAdapter = new SyllablesAdapter(this, syllables);
        this.syllables.setAdapter(syllablesAdapter);
    }
}