package cherry_wave.nmg.view.syllables;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.daimajia.swipe.SwipeLayout;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Syllable;

public class SyllablesAdapter extends ArrayAdapter<Syllable> {

    private static final String TAG = SyllablesAdapter.class.getCanonicalName();

    @BindView(R.id.swipe)
    SwipeLayout swipeLayout;

    public SyllablesAdapter(Context context, List<Syllable> syllables) {
        super(context, R.layout.syllable_list_item, syllables);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        View view = LayoutInflater.from(getContext()).inflate(R.layout.syllable_list_item, parent, false);
        holder = new ViewHolder(view);

//        holder.text.setText("This is a text for the image number: "+position);

        return view;
    }

    static class ViewHolder {

        public ViewHolder(View view) {
            ButterKnife.bind(view);
        }

        @OnClick(R.id.delete)
        void delete() {
            Log.v(TAG, "delete");
        }

    }

}
