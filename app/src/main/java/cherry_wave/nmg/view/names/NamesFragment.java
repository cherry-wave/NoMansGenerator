package cherry_wave.nmg.view.names;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cherry_wave.nmg.NMGFragment;
import cherry_wave.nmg.NMGSwipeMenuCreator;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Name;
import cherry_wave.nmg.view.InfoDialog;
import lombok.Getter;

public class NamesFragment extends NMGFragment {

    @BindView(R.id.name_add)
    @Getter
    FloatingActionButton add;
    @BindView(R.id.names_list)
    SwipeMenuListView namesListView;
    private List<Name> names;

    private static final int MIN_NAMES_FOR_RATE_HINT = 15;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflate(inflater, R.layout.fragment_names, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        namesListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Name name = names.get(position);
                switch (index) {
                    case 0:
                        editName(name);
                        break;
                    case 1:
                        NameDeleteDialog nameDeleteDialog = NameDeleteDialog.newInstance(name);
                        nameDeleteDialog.setTargetFragment(NamesFragment.this, 0);
                        nameDeleteDialog.show(getFragmentManager(), NameDeleteDialog.class.getCanonicalName());
                        break;
                }
                return false;
            }
        });

        namesListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
                add.hide();
            }

            @Override
            public void onMenuClose(int position) {
                add.show();
            }
        });

        updateNames();
    }

    @OnClick(R.id.name_add)
    void addName() {
        editName(null);
    }

    public void editName(Name name) {
        NameSaveDialog nameSaveDialog = NameSaveDialog.newInstance(name);
        nameSaveDialog.setTargetFragment(this, 0);
        nameSaveDialog.show(getFragmentManager(), NameSaveDialog.class.getCanonicalName());
    }

    public void updateNames() {
        names = SugarRecord.listAll(Name.class, "characters");
        String rateHitShown = "RATE_HINT_SHOWN";
        SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
        if (!sharedPreferences.getBoolean(rateHitShown, false)) {
            if(names.size() >= MIN_NAMES_FOR_RATE_HINT) {
                RateHintDialog.newInstance().show(getFragmentManager(), InfoDialog.class.getCanonicalName());

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(rateHitShown, true);
                editor.apply();
            }
        }

        List<String> namesCharacters = new ArrayList<>(names.size());
        for (Name name : names) {
            namesCharacters.add(name.getCharacters());
        }
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, namesCharacters);
        namesListView.setAdapter(namesAdapter);

        namesListView.setMenuCreator(new NMGSwipeMenuCreator(getActivity()));

        add.show();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {
            String infoShown = "INFO_SHOWN_NAMES";
            SharedPreferences sharedPreferences = getActivity().getPreferences(Context.MODE_PRIVATE);
            if (!sharedPreferences.getBoolean(infoShown, false)) {
                InfoDialog.newInstance(R.string.info_names).show(getFragmentManager(), InfoDialog.class.getCanonicalName());

                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(infoShown, true);
                editor.apply();
            }
        }
    }
}