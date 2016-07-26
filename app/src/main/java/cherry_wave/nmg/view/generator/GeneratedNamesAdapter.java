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

public class GeneratedNamesAdapter extends ArrayAdapter<String> {

    public GeneratedNamesAdapter(Context context, String[] generatedNames) {
        super(context, R.layout.list_item_generated_name, generatedNames);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String generatedName = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_generated_name, parent, false);
            new ViewHolder(convertView, generatedName);
        }

        return convertView;
    }

    class ViewHolder {

        @BindView(R.id.generated_name_characters)
        TextView characters;
        @BindView(R.id.generated_name_save)
        CheckBox save;

        private Name generatedName;

        public ViewHolder(View view, String generatedName) {
            ButterKnife.bind(this, view);
            this.generatedName = new Name(generatedName);
            characters.setText(generatedName);
        }

        @OnCheckedChanged(R.id.generated_name_save)
        void saveDelete() {
            if(save.isChecked()) {
                SugarRecord.save(generatedName);
            } else {
                SugarRecord.delete(generatedName);
            }
        }

    }

}
