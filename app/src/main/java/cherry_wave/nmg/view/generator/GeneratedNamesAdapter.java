package cherry_wave.nmg.view.generator;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.orm.SugarRecord;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnCheckedChanged;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Name;

public class GeneratedNamesAdapter extends ArrayAdapter<Name> {

    public GeneratedNamesAdapter(Context context, Name[] names) {
        super(context, R.layout.list_item_generated_name, names);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Name name = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_generated_name, parent, false);
        }

        new ViewHolder(convertView, name);
        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.generated_name_characters)
        TextView characters;
        @BindView(R.id.generated_name_save)
        CheckBox save;

        private Name name;

        public ViewHolder(View view, Name name) {
            ButterKnife.bind(this, view);
            this.name = name;
            characters.setText(name.getCharacters());
            // lookup if name is saved
            save.setChecked(SugarRecord.findById(Name.class, name.getId()) != null);
        }

        @OnCheckedChanged(R.id.generated_name_save)
        void deActivate() {
            if (save.isChecked()) {
                SugarRecord.save(name);
            } else {
                SugarRecord.delete(name);
            }
        }

    }

}
