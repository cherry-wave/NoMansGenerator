package cherry_wave.nmg.view.syllables;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.widget.SimpleCursorAdapter;
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
import cherry_wave.nmg.NMGActivity;
import cherry_wave.nmg.NMGFragment;
import cherry_wave.nmg.NMGSwipeMenuCreator;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Syllable;
import lombok.Getter;

public class SyllablesActivity extends NMGActivity {

    @BindView(R.id.syllable_add)
    @Getter
    FloatingActionButton add;
    @BindView(R.id.syllables_list)
    SwipeMenuListView syllablesListView;
    private List<Syllable> syllables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_syllables);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
                        syllableDeleteDialog.show(getSupportFragmentManager(), SyllableDeleteFragment.class.getCanonicalName());
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
        syllableSaveDialog.show(getSupportFragmentManager(), SyllableSaveFragment.class.getCanonicalName());
    }

    public void updateSyllables() {
        syllables = SugarRecord.listAll(Syllable.class, "characters");

        SyllablesAdapter simpleCursorAdapter = new SyllablesAdapter(this, syllables);
        syllablesListView.setAdapter(simpleCursorAdapter);

        syllablesListView.setMenuCreator(new NMGSwipeMenuCreator(this));

        add.show();
    }
}