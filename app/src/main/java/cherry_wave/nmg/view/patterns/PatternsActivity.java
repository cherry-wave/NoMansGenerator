package cherry_wave.nmg.view.patterns;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.Menu;
import android.view.MenuItem;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import com.orm.SugarRecord;

import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cherry_wave.nmg.NMGActivity;
import cherry_wave.nmg.NMGSwipeMenuCreator;
import cherry_wave.nmg.R;
import cherry_wave.nmg.view.InfoDialog;
import cherry_wave.nmg.view.TutorialActivity;
import cherry_wave.nmg.model.Pattern;
import lombok.Getter;

public class PatternsActivity extends NMGActivity {

    @BindView(R.id.pattern_add)
    @Getter
    FloatingActionButton add;
    @BindView(R.id.patterns_list)
    SwipeMenuListView patternsListView;
    private List<Pattern> patterns;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

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
                        PatternDeleteDialog patternDeleteDialog = PatternDeleteDialog.newInstance(pattern);
                        patternDeleteDialog.show(getSupportFragmentManager(), PatternDeleteDialog.class.getCanonicalName());
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

        String infoShown = "INFO_SHOWN_PATTERNS";
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        if (!sharedPreferences.getBoolean(infoShown, false)) {
            InfoDialog.newInstance(R.string.info_patterns).show(getSupportFragmentManager(), InfoDialog.class.getCanonicalName());

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(infoShown, true);
            editor.apply();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_patterns, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_tutorial) {
            Intent intent = new Intent(this, TutorialActivity.class);
            intent.putExtra(TutorialActivity.EXTRA_TEXT, R.string.tutorial_patterns);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @OnClick(R.id.pattern_add)
    void addPattern() {
        editPattern(null);
    }

    public void editPattern(Pattern pattern) {
        PatternSaveDialog patternSaveDialog = PatternSaveDialog.newInstance(pattern);
        patternSaveDialog.show(getSupportFragmentManager(), PatternSaveDialog.class.getCanonicalName());
    }

    public void updatePatterns() {
        patterns = SugarRecord.listAll(Pattern.class, "characters");

        PatternsAdapter patternsAdapter = new PatternsAdapter(getApplicationContext(), patterns);
        patternsListView.setAdapter(patternsAdapter);

        patternsListView.setMenuCreator(new NMGSwipeMenuCreator(this));

        add.show();
    }
}