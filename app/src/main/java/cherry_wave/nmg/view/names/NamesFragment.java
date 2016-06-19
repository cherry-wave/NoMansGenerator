package cherry_wave.nmg.view.names;

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
import cherry_wave.nmg.NMGFragment;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Name;

public class NamesFragment extends NMGFragment {

    @BindView(R.id.names_list)
    ListView names;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflate(inflater, R.layout.fragment_names, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateNames();
    }

    @OnClick(R.id.name_add)
    void addName() {
        editName(null);
    }

    public void editName(Name name) {
        NameSaveFragment nameSaveDialog = NameSaveFragment.newInstance(name);
        nameSaveDialog.setTargetFragment(this, 0);
        nameSaveDialog.show(getFragmentManager(), NameSaveFragment.class.getCanonicalName());
    }

    public void updateNames() {
        List<Name> names = SugarRecord.listAll(Name.class, "characters");
        NamesAdapter namesAdapter = new NamesAdapter(this, names);
        this.names.setAdapter(namesAdapter);
    }
}