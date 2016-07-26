package cherry_wave.nmg.view.names;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.orm.SugarRecord;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cherry_wave.nmg.NMGFragment;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Name;

public class NamesFragment extends NMGFragment {

    @BindView(R.id.name_add)
    FloatingActionButton add;
    @BindView(R.id.names_list)
    SwipeMenuListView namesListView;
    private List<Name> names;

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
                        NameDeleteFragment nameDeleteDialog = NameDeleteFragment.newInstance(name);
                        nameDeleteDialog.setTargetFragment(NamesFragment.this, 0);
                        nameDeleteDialog.show(getFragmentManager(), NameDeleteFragment.class.getCanonicalName());
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
        NameSaveFragment nameSaveDialog = NameSaveFragment.newInstance(name);
        nameSaveDialog.setTargetFragment(this, 0);
        nameSaveDialog.show(getFragmentManager(), NameSaveFragment.class.getCanonicalName());
    }

    public void updateNames() {
        names = SugarRecord.listAll(Name.class, "characters");

        List<String> namesCharacters = new ArrayList<>(names.size());
        for (Name name : names) {
            namesCharacters.add(name.getCharacters());
        }
        ArrayAdapter<String> namesAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, namesCharacters);
        namesListView.setAdapter(namesAdapter);

        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem edit = new SwipeMenuItem(getActivity().getApplicationContext());
                edit.setBackground(R.color.edit);
                edit.setIcon(android.R.drawable.ic_menu_edit);
                edit.setWidth(edit.getIcon().getMinimumWidth() * 2);
                menu.addMenuItem(edit);

                SwipeMenuItem delete = new SwipeMenuItem(getActivity().getApplicationContext());
                delete.setBackground(R.color.delete);
                delete.setIcon(android.R.drawable.ic_menu_delete);
                delete.setWidth(delete.getIcon().getMinimumWidth() * 2);
                menu.addMenuItem(delete);
            }
        };
        namesListView.setMenuCreator(swipeMenuCreator);

        add.show();
    }
}