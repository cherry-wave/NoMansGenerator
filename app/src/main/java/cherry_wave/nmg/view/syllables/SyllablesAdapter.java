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

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_syllable, parent, false);
            new ViewHolder(convertView, syllable);
        }

        return convertView;
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

        @OnCheckedChanged(R.id.syllable_active)
        void deActivate() {
            syllable.setActive(active.isChecked());
            SugarRecord.save(syllable);
        }

    }

}
