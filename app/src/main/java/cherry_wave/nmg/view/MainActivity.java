package cherry_wave.nmg.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import butterknife.BindArray;
import butterknife.BindView;
import cherry_wave.nmg.NMGActivity;
import cherry_wave.nmg.NMGViewPager;
import cherry_wave.nmg.R;
import cherry_wave.nmg.view.generator.GeneratorFragment;
import cherry_wave.nmg.view.names.NamesFragment;
import cherry_wave.nmg.view.pattern.PatternsFragment;
import cherry_wave.nmg.view.syllables.SyllablesFragment;

public class MainActivity extends NMGActivity implements ViewPager.OnPageChangeListener {

    private SectionsPagerAdapter sectionsPagerAdapter;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    NMGViewPager viewPager;
    @BindArray(R.array.sections)
    String[] sections;
    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        toolbar.setTitle(R.string.app_title);
        setSupportActionBar(toolbar);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(sectionsPagerAdapter);

        tabs.setViewPager(viewPager);
        tabs.setOnPageChangeListener(this);
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
        Fragment fragment = sectionsPagerAdapter.getItem(position);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.detach(fragment).attach(fragment).commit();
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
        public int getCount() {
            return sections.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return sections[position];
        }

    }
}