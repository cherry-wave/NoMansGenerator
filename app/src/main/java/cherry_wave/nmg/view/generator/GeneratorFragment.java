package cherry_wave.nmg.view.generator;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import cherry_wave.nmg.R;
import cherry_wave.nmg.controller.PatternUtils;
import cherry_wave.nmg.controller.SyllableUtils;
import cherry_wave.nmg.model.Pattern;
import cherry_wave.nmg.model.Syllable;
import cherry_wave.nmg.NMGFragment;

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
        return inflate(inflater, R.layout.fragment_generator, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent);
    }

    @Override
    public void onRefresh() {
        List<Pattern> activePatterns = PatternUtils.getActivePatterns();
        boolean patternsContainConsonantStart = PatternUtils.containsStart(activePatterns, PatternUtils.Start.CONSONANT);
        boolean patternsContainVowelStart = PatternUtils.containsStart(activePatterns, PatternUtils.Start.VOWEL);

        List<Syllable> consonantSyllables = SyllableUtils.getActiveSyllables(false);
        List<Syllable> vowelSyllables = SyllableUtils.getActiveSyllables(true);

        if (consonantSyllables.isEmpty() && vowelSyllables.isEmpty()) {
            GeneratorInfoFragment.newInstance(R.string.generator_info_no_syllables).show(getFragmentManager(), GeneratorInfoFragment.class.getCanonicalName());
        } else if (activePatterns.isEmpty()) {
            GeneratorInfoFragment.newInstance(R.string.generator_info_no_patterns).show(getFragmentManager(), GeneratorInfoFragment.class.getCanonicalName());
        } else if (!patternsContainVowelStart && consonantSyllables.isEmpty()) {
            GeneratorInfoFragment.newInstance(R.string.generator_info_no_consonant_syllables).show(getFragmentManager(), GeneratorInfoFragment.class.getCanonicalName());
        } else if (!patternsContainConsonantStart && vowelSyllables.isEmpty()) {
            GeneratorInfoFragment.newInstance(R.string.generator_info_no_vowel_syllables).show(getFragmentManager(), GeneratorInfoFragment.class.getCanonicalName());
        } else {
            List<String> generatedNames = new ArrayList(10);
            Random anyRandom = new Random();
            Random consonantRandom = new Random();
            Random vowelRandom = new Random();

            for (int i = 1; i <= 10; i++) {
                Pattern pattern = activePatterns.get((int) (Math.random() * activePatterns.size()));
                String[] subPatterns = pattern.getCharacters().split("\\{");
                StringBuilder name = new StringBuilder();
                for (String subPattern : subPatterns) {
                    int indexOfClose = subPattern.indexOf('}');
                    if (indexOfClose == -1) {
                        continue;
                    }
                    String append = subPattern.substring(indexOfClose + 1);
                    subPattern = subPattern.substring(0, indexOfClose);

                    // set together the starting syllable
                    String startSyllable;
                    if (PatternUtils.startsWith(subPattern, PatternUtils.Start.CONSONANT)) {
                        startSyllable = consonantSyllables.get(consonantRandom.nextInt(consonantSyllables.size())).getCharacters();
                    } else if (PatternUtils.startsWith(subPattern, PatternUtils.Start.VOWEL)) {
                        startSyllable = vowelSyllables.get(vowelRandom.nextInt(vowelSyllables.size())).getCharacters();
                    } else {
                        if(anyRandom.nextInt(1) == 0) {
                            startSyllable = consonantSyllables.get(consonantRandom.nextInt(consonantSyllables.size())).getCharacters();
                        } else {
                            startSyllable = vowelSyllables.get(vowelRandom.nextInt(vowelSyllables.size())).getCharacters();
                        }
                    }
                    if(PatternUtils.startsWithUppercase(subPattern)) {
                        startSyllable = startSyllable.substring(0, 1).toUpperCase() + startSyllable.substring(1);
                    }
                    name.append(startSyllable);

                    // add following syllables
                    int to = PatternUtils.getRangeTo(subPattern);
                    for (int from = 1; from < to; from++) {
                        String syllable;
                        if(anyRandom.nextInt(1) == 0) {
                            syllable = consonantSyllables.get(consonantRandom.nextInt(consonantSyllables.size())).getCharacters();
                        } else {
                            syllable = vowelSyllables.get(vowelRandom.nextInt(vowelSyllables.size())).getCharacters();
                        }
                        name.append(syllable);
                    }

                    // add non pattern content
                    name.append(append);
                }
                generatedNames.add(name.toString());
            }

            GeneratedNamesAdapter generatedNamesAdapter = new GeneratedNamesAdapter(getContext(), generatedNames);
            names.setAdapter(generatedNamesAdapter);

            generatorEmptyState.setVisibility(View.GONE);
        }
        swipeRefreshLayout.setRefreshing(false);
    }
}