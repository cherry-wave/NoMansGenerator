package cherry_wave.nmg.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import butterknife.BindView;
import cherry_wave.nmg.R;
import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GeneratorFragment extends NMGFragment implements SwipeRefreshLayout.OnRefreshListener {

    private static final String TAG = GeneratorFragment.class.getCanonicalName();

    @BindView(R.id.generator_refresh)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.generator_list)
    ListView names;
    @BindView(R.id.generator_empty_state)
    TextView generatorEmptyState;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflate(inflater, R.layout.fragment_generator, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }

    @Override
    public void onRefresh() {
        String[] pattern = {"{X5}, {V1}, {c1-5}"};
        int patternIndex = 0 + (int) (Math.random() * pattern.length);
        Log.v(TAG, "Used pattern: " + pattern[patternIndex]);

        String[] syllables = {"ab", "ec", "id", "of", "ug"};
        int syllablesIndex = 0 + (int) (Math.random() * syllables.length);
        Log.v(TAG, "Used syllable: " + syllables[syllablesIndex]);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, syllables);
        names.setAdapter(adapter);
        generatorEmptyState.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }
}