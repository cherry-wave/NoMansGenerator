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

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import cherry_wave.nmg.R;
import cherry_wave.nmg.controller.PatternUtil;
import cherry_wave.nmg.controller.SyllableUtil;
import cherry_wave.nmg.model.Pattern;
import cherry_wave.nmg.model.Syllable;

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
        List<Pattern> patterns = PatternUtil.getActivePatterns();

        List<Syllable> consonantSyllables = SyllableUtil.getActiveSyllables(false);
        List<Syllable> vowelSyllables = SyllableUtil.getActiveSyllables(true);

        List<String> generatedNames = new ArrayList(10);

        for (int i = 1; i <= 10; i++) {
            Pattern usedPattern = patterns.get((int) (Math.random() * patterns.size()));
            Log.v(TAG, "usedPattern: " + usedPattern);
//            char startsWith = usedPattern.charAt(1);
//            int rangeFrom = Character.getNumericValue(usedPattern.charAt(2));
//            int rangeTo = usedPattern.length() == 6 ? Character.getNumericValue(usedPattern.charAt(4)) : rangeFrom;
//            StringBuilder nameBuilder = new StringBuilder();
//            while (rangeFrom <= rangeTo) {
//                String characters;
//                switch (startsWith) {
//                    case 'C':
//                    case 'c':
//                        characters = consonantSyllables.get((int) (Math.random() * consonantSyllables.size())).getCharacters();
//                        break;
//                    case 'V':
//                    case 'v':
//                        characters = vowelSyllables.get((int) (Math.random() * vowelSyllables.size())).getCharacters();
//                        break;
//                    default:
//                        if (((int) (Math.random() * 1)) == 0) {
//                            characters = vowelSyllables.get((int) (Math.random() * vowelSyllables.size())).getCharacters();
//                        } else {
//                            characters = vowelSyllables.get((int) (Math.random() * vowelSyllables.size())).getCharacters();
//                        }
//                        break;
//                }
//
//                if (rangeFrom == Character.getNumericValue(usedPattern.charAt(2)) && !Character.isLowerCase(startsWith)) {
//                    characters = characters.substring(0, 1).toUpperCase() + characters.substring(1);
//                }
//
//                nameBuilder.append(characters);
//                rangeFrom++;
//            }
//            generatedNames.add(nameBuilder.toString());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, generatedNames);
        names.setAdapter(adapter);
        generatorEmptyState.setVisibility(View.GONE);
        swipeRefreshLayout.setRefreshing(false);
    }
}