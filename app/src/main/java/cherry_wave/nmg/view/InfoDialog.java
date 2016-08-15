package cherry_wave.nmg.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.afollestad.materialdialogs.MaterialDialog;

import cherry_wave.nmg.R;

public class InfoDialog extends DialogFragment {

    private static final String ARG_CONTENT_RES = "contentRes";

    public static InfoDialog newInstance(int contentRes) {
        InfoDialog infoDialog = new InfoDialog();
        Bundle args = new Bundle();
        args.putInt(ARG_CONTENT_RES, contentRes);
        infoDialog.setArguments(args);
        return infoDialog;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(R.string.title_info)
                .neutralText(R.string.ok)
                .neutralColorRes(R.color.colorPrimary)
                .content(getArguments().getInt(ARG_CONTENT_RES));
        return builder.build();
    }

}
