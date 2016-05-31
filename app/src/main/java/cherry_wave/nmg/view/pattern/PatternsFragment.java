package cherry_wave.nmg.view.pattern;

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
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Pattern;
import cherry_wave.nmg.view.NMGFragment;

public class PatternsFragment extends NMGFragment {

    private static final String TAG = PatternsFragment.class.getCanonicalName();

    @BindView(R.id.patterns_list)
    ListView patterns;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflate(inflater, R.layout.fragment_patterns, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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
        List<Pattern> pattern = SugarRecord.listAll(Pattern.class, "characters");
        PatternsAdapter patternsAdapter = new PatternsAdapter(this, pattern);
        this.patterns.setAdapter(patternsAdapter);
    }
}