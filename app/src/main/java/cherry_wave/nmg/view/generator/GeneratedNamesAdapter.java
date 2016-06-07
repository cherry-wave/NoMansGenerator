package cherry_wave.nmg.view.generator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.orm.SugarRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import butterknife.OnClick;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Name;
import cherry_wave.nmg.view.pattern.PatternDeleteFragment;
import cherry_wave.nmg.view.pattern.PatternsFragment;

public class GeneratedNamesAdapter extends ArrayAdapter<String> {

    public GeneratedNamesAdapter(Context context, List<String> generatedNames) {
        super(context, R.layout.list_item_generated_name, generatedNames);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String generatedName = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_generated_name, parent, false);
        new ViewHolder(view, generatedName);

        return view;
    }

    class ViewHolder {

        @BindView(R.id.generated_name_characters)
        TextView characters;
        @BindView(R.id.generated_name_save)
        CheckBox save;

        private String generatedName;

        public ViewHolder(View view, String generatedName) {
            ButterKnife.bind(this, view);
            this.generatedName = generatedName;
            characters.setText(generatedName);
        }

        @OnCheckedChanged(R.id.generated_name_save)
        void saveDelete() {
            Name name = new Name(generatedName);
            if(save.isChecked()) {
                SugarRecord.save(name);
            } else {
                SugarRecord.delete(name);
            }
        }

    }

}
