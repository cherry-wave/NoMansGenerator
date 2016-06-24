package cherry_wave.nmg.view.syllables;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.orm.SugarRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cherry_wave.nmg.NMGFragment;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Syllable;

public class SyllablesFragment extends NMGFragment {

    @BindView(R.id.syllable_add)
    FloatingActionButton add;
    @BindView(R.id.syllables_list)
    SwipeMenuListView syllablesListView;
    private List<Syllable> syllables;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflate(inflater, R.layout.fragment_syllables, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        syllablesListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Syllable syllable = syllables.get(position);
                switch (index) {
                    case 0:
                        editSyllable(syllable);
                        break;
                    case 1:
                        SyllableDeleteFragment syllableDeleteDialog = SyllableDeleteFragment.newInstance(syllable);
                        syllableDeleteDialog.setTargetFragment(SyllablesFragment.this, 0);
                        syllableDeleteDialog.show(getFragmentManager(), SyllableDeleteFragment.class.getCanonicalName());
                        break;
                }
                return false;
            }
        });

        syllablesListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
                add.hide();
            }

            @Override
            public void onMenuClose(int position) {
                add.show();
            }
        });

        updateSyllables();
    }

    @OnClick(R.id.syllable_add)
    void addSyllable() {
        editSyllable(null);
    }

    public void editSyllable(Syllable syllable) {
        SyllableSaveFragment syllableSaveDialog = SyllableSaveFragment.newInstance(syllable);
        syllableSaveDialog.setTargetFragment(this, 0);
        syllableSaveDialog.show(getFragmentManager(), SyllableSaveFragment.class.getCanonicalName());
    }

    public void updateSyllables() {
        syllables = SugarRecord.listAll(Syllable.class, "characters");

        SyllablesAdapter syllablesAdapter = new SyllablesAdapter(this, syllables);
        syllablesListView.setAdapter(syllablesAdapter);

        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem edit = new SwipeMenuItem(getActivity().getApplicationContext());
                edit.setBackground(R.color.colorAccent);
                edit.setIcon(android.R.drawable.ic_menu_edit);
                edit.setWidth(edit.getIcon().getMinimumWidth() * 2);
                menu.addMenuItem(edit);

                SwipeMenuItem delete = new SwipeMenuItem(getActivity().getApplicationContext());
                delete.setIcon(android.R.drawable.ic_menu_delete);
                delete.setWidth(delete.getIcon().getMinimumWidth() * 2);
                menu.addMenuItem(delete);
            }
        };
        syllablesListView.setMenuCreator(swipeMenuCreator);

        add.show();
    }
}