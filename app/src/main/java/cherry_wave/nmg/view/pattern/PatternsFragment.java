package cherry_wave.nmg.view.pattern;

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
import cherry_wave.nmg.model.Pattern;

public class PatternsFragment extends NMGFragment {

    @BindView(R.id.pattern_add)
    FloatingActionButton add;
    @BindView(R.id.patterns_list)
    SwipeMenuListView patternsListView;
    private List<Pattern> patterns;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflate(inflater, R.layout.fragment_patterns, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

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
                        patternDeleteDialog.setTargetFragment(PatternsFragment.this, 0);
                        patternDeleteDialog.show(getFragmentManager(), PatternDeleteFragment.class.getCanonicalName());
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
        patternSaveDialog.setTargetFragment(this, 0);
        patternSaveDialog.show(getFragmentManager(), PatternSaveFragment.class.getCanonicalName());
    }

    public void updatePatterns() {
        patterns = SugarRecord.listAll(Pattern.class, "characters");

        PatternsAdapter patternsAdapter = new PatternsAdapter(this, patterns);
        patternsListView.setAdapter(patternsAdapter);

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
        patternsListView.setMenuCreator(swipeMenuCreator);

        add.show();
    }
}