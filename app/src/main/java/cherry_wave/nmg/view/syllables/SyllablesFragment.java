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
import cherry_wave.nmg.view.NMGFragment;
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
    void addSyllable() {
        editSyllable(null);
    }

    public void editSyllable(Syllable syllable) {
        SyllableDialogFragment syllableDialog = SyllableDialogFragment.newInstance(syllable);
        syllableDialog.setTargetFragment(this, 0);
        syllableDialog.show(getFragmentManager(), SyllableDialogFragment.class.getCanonicalName());
    }

    @Override
    public void onSyllableSave() {
        updateSyllables();
    }

    private void updateSyllables() {
        List<Syllable> syllables = SugarRecord.listAll(Syllable.class);
        SyllablesAdapter syllablesAdapter = new SyllablesAdapter(this, syllables);
        this.syllables.setAdapter(syllablesAdapter);
    }
}