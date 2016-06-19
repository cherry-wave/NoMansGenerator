package cherry_wave.nmg.view;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.google.gson.Gson;
import com.orm.SugarRecord;

import java.io.IOException;
import java.io.InputStream;

import butterknife.BindArray;
import butterknife.BindView;
import cherry_wave.nmg.NMGActivity;
import cherry_wave.nmg.NMGViewPager;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Pattern;
import cherry_wave.nmg.model.Syllable;
import cherry_wave.nmg.view.generator.GeneratorFragment;
import cherry_wave.nmg.view.names.NamesFragment;
import cherry_wave.nmg.view.pattern.PatternsFragment;
import cherry_wave.nmg.view.syllables.SyllablesFragment;
import lombok.Getter;

public class MainActivity extends NMGActivity implements ViewPager.OnPageChangeListener {

    private static final String TAG = MainActivity.class.getCanonicalName();
    private static final String INITIAL_IMPORT = "initialImport";

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    NMGViewPager viewPager;
    @BindArray(R.array.sections)
    String[] sections;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    private SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        initialImport();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar.setTitle(R.string.app_title);
        setSupportActionBar(toolbar);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs.setViewPager(viewPager);
        tabs.setOnPageChangeListener(this);
    }

    private void initialImport() {
        SharedPreferences sharedPreferences = getPreferences(Context.MODE_PRIVATE);
        if(!sharedPreferences.getBoolean(INITIAL_IMPORT, false)) {
            try {
                InputStream inputStream = getResources().openRawResource(R.raw.initial_import);
                int size = inputStream.available();
                byte[] buffer = new byte[size];
                inputStream.read(buffer);
                inputStream.close();
                Gson gson = new Gson();
                InitialImport initialImport = gson.fromJson(new String(buffer, "UTF-8"), InitialImport.class);
                for(String patternCharacters : initialImport.getPatternsCharacters()) {
                    SugarRecord.save(new Pattern(patternCharacters));
                }
                for(String syllableCharacters : initialImport.getSyllablesCharacters()) {
                    SugarRecord.save(new Syllable(syllableCharacters));
                }
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean(INITIAL_IMPORT, true);
                editor.apply();
            } catch (IOException e) {
                Log.e(TAG, e.getLocalizedMessage());
            }
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onPageSelected(int position) {
        sectionsPagerAdapter.notifyDataSetChanged();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }

    class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position) {
                case 0:
                    return new GeneratorFragment();
                case 1:
                    return new NamesFragment();
                case 2:
                    return new PatternsFragment();
                case 3:
                    return new SyllablesFragment();
                case 4:
                    return new SyllablesFragment();
            }
            return null;
        }

        @Override
        public int getItemPosition(Object object) {
            return POSITION_NONE;
        }

        @Override
        public int getCount() {
            return sections.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return sections[position];
        }

    }

    @Getter
    class InitialImport {
        private String[] patternsCharacters;
        private String[] syllablesCharacters;
    }

}
