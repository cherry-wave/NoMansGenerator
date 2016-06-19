package cherry_wave.nmg.view.names;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Name;

public class NamesAdapter extends ArrayAdapter<Name> {

    private NamesFragment namesFragment;

    public NamesAdapter(NamesFragment namesFragment, List<Name> names) {
        super(namesFragment.getContext(), R.layout.list_item_name, names);
        this.namesFragment = namesFragment;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Name name = getItem(position);

        View view = LayoutInflater.from(getContext()).inflate(R.layout.list_item_name, parent, false);
        new ViewHolder(view, name);

        return view;
    }

    class ViewHolder {

        @BindView(R.id.name_characters)
        TextView characters;

        private Name name;

        public ViewHolder(View view, Name name) {
            ButterKnife.bind(this, view);
            this.name = name;
            characters.setText(name.getCharacters());
        }

        @OnClick(R.id.name_edit)
        void edit() {
            namesFragment.editName(name);
        }

        @OnClick(R.id.name_delete)
        void delete() {
            NameDeleteFragment nameDeleteDialog = NameDeleteFragment.newInstance(name);
            nameDeleteDialog.setTargetFragment(namesFragment, 0);
            nameDeleteDialog.show(namesFragment.getFragmentManager(), NameDeleteFragment.class.getCanonicalName());
        }

    }

}
