package cherry_wave.nmg.view.syllables;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Switch;
import android.widget.TextView;

import com.orm.SugarRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Syllable;

public class SyllablesAdapter extends ArrayAdapter<Syllable> {

    private static final String TAG = SyllablesAdapter.class.getCanonicalName();

    private SyllablesFragment syllablesFragment;

    public SyllablesAdapter(SyllablesFragment syllablesFragment, List<Syllable> syllables) {
        super(syllablesFragment.getContext(), R.layout.list_item_syllable, syllables);
        this.syllablesFragment = syllablesFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Syllable syllable = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_syllable, parent, false);
        new ViewHolder(view, syllable);

        return view;
    }

    class ViewHolder {

        @BindView(R.id.syllable_characters)
        TextView characters;
        @BindView(R.id.syllable_active)
        Switch active;

        private Syllable syllable;

        public ViewHolder(View view, Syllable syllable) {
            ButterKnife.bind(this, view);
            this.syllable = syllable;
            characters.setText(syllable.getCharacters());
            active.setChecked(syllable.isActive());
        }

        @OnClick(R.id.syllable_edit)
        void edit() {
            syllablesFragment.editSyllable(syllable);
        }

        @OnClick(R.id.syllable_delete)
        void delete() {
            SugarRecord.delete(syllable);
            syllablesFragment.onSyllableSave();
        }

        @OnCheckedChanged(R.id.syllable_active)
        void deActivate() {
            syllable.setActive(active.isChecked());
            SugarRecord.save(syllable);
        }

    }

}
