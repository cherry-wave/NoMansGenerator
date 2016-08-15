package cherry_wave.nmg.view.names;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import cherry_wave.nmg.R;

public class RateHintDialog extends DialogFragment {

    private static final String MARKET_LINK = "market://details?id=";
    private static final String BROWSER_LINK = "https://play.google.com/store/apps/details?id=";

    public static RateHintDialog newInstance() {
        return new RateHintDialog();
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(R.string.title_info)
                .content(R.string.rate_hint)
                .positiveText(R.string.rate_hint_yes)
                .positiveColorRes(R.color.colorPrimary)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        final String appPackageName = getActivity().getPackageName();
                        try {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(MARKET_LINK + appPackageName)));
                        } catch (ActivityNotFoundException activityNotFoundException) {
                            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(BROWSER_LINK + appPackageName)));
                        }
                    }
                })
                .negativeText(R.string.rate_hint_no)
                .negativeColorRes(R.color.colorPrimary);
        return builder.build();
    }
}
