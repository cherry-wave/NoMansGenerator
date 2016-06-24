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

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_pattern, parent, false);
            new ViewHolder(convertView, pattern);
        }

        return convertView;
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

        @OnCheckedChanged(R.id.pattern_active)
        void deActivate() {
            pattern.setActive(active.isChecked());
            SugarRecord.save(pattern);
        }

    }

}
