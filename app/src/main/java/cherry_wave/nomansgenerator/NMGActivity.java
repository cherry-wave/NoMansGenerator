package cherry_wave.nomansgenerator;

import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

public class NMGActivity extends AppCompatActivity {

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        ButterKnife.bind(this);
    }
}
