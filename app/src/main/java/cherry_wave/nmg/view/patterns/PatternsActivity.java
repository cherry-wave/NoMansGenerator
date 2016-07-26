package cherry_wave.nmg.view.patterns;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.orm.SugarRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cherry_wave.nmg.NMGActivity;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Pattern;

public class PatternsActivity extends NMGActivity {

    @BindView(R.id.pattern_add)
    FloatingActionButton add;
    @BindView(R.id.patterns_list)
    SwipeMenuListView patternsListView;
    private List<Pattern> patterns;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patterns);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        patternsListView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                Pattern pattern = patterns.get(position);
                switch (index) {
                    case 0:
                        editPattern(pattern);
                        break;
                    case 1:
                        PatternDeleteFragment patternDeleteDialog = PatternDeleteFragment.newInstance(pattern);
                        patternDeleteDialog.show(getSupportFragmentManager(), PatternDeleteFragment.class.getCanonicalName());
                        break;
                }
                return false;
            }
        });

        patternsListView.setOnMenuStateChangeListener(new SwipeMenuListView.OnMenuStateChangeListener() {
            @Override
            public void onMenuOpen(int position) {
                add.hide();
            }

            @Override
            public void onMenuClose(int position) {
                add.show();
            }
        });

        updatePatterns();
    }

    @OnClick(R.id.pattern_add)
    void addPattern() {
        editPattern(null);
    }

    public void editPattern(Pattern pattern) {
        PatternSaveFragment patternSaveDialog = PatternSaveFragment.newInstance(pattern);
        patternSaveDialog.show(getSupportFragmentManager(), PatternSaveFragment.class.getCanonicalName());
    }

    public void updatePatterns() {
        patterns = SugarRecord.listAll(Pattern.class, "characters");

        PatternsAdapter patternsAdapter = new PatternsAdapter(getApplicationContext(), patterns);
        patternsListView.setAdapter(patternsAdapter);

        SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                SwipeMenuItem edit = new SwipeMenuItem(getApplicationContext());
                edit.setBackground(R.color.edit);
                edit.setIcon(android.R.drawable.ic_menu_edit);
                edit.setWidth(edit.getIcon().getMinimumWidth() * 2);
                menu.addMenuItem(edit);

                SwipeMenuItem delete = new SwipeMenuItem(getApplicationContext());
                delete.setBackground(R.color.delete);
                delete.setIcon(android.R.drawable.ic_menu_delete);
                delete.setWidth(delete.getIcon().getMinimumWidth() * 2);
                menu.addMenuItem(delete);
            }
        };
        patternsListView.setMenuCreator(swipeMenuCreator);

        add.show();
    }
}