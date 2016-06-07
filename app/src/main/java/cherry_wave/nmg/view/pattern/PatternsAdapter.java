package cherry_wave.nmg.view.pattern;

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
import cherry_wave.nmg.model.Pattern;

public class PatternsAdapter extends ArrayAdapter<Pattern> {

    private PatternsFragment patternsFragment;

    public PatternsAdapter(PatternsFragment patternsFragment, List<Pattern> patterns) {
        super(patternsFragment.getContext(), R.layout.list_item_pattern, patterns);
        this.patternsFragment = patternsFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Pattern pattern = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_pattern, parent, false);
        new ViewHolder(view, pattern);

        return view;
    }

    class ViewHolder {

        @BindView(R.id.pattern_characters)
        TextView characters;
        @BindView(R.id.pattern_active)
        Switch active;

        private Pattern pattern;

        public ViewHolder(View view, Pattern pattern) {
            ButterKnife.bind(this, view);
            this.pattern = pattern;
            characters.setText(pattern.getCharacters());
            active.setChecked(pattern.isActive());
        }

        @OnClick(R.id.pattern_edit)
        void edit() {
            patternsFragment.editPattern(pattern);
        }

        @OnClick(R.id.pattern_delete)
        void delete() {
            PatternDeleteFragment patternDeleteDialog = PatternDeleteFragment.newInstance(pattern);
            patternDeleteDialog.setTargetFragment(patternsFragment, 0);
            patternDeleteDialog.show(patternsFragment.getFragmentManager(), PatternDeleteFragment.class.getCanonicalName());
        }

        @OnCheckedChanged(R.id.pattern_active)
        void deActivate() {
            pattern.setActive(active.isChecked());
            SugarRecord.save(pattern);
        }

    }

}
