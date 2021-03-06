package cherry_wave.nmg.view.syllables;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.orm.SugarRecord;

import org.parceler.Parcels;

import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Syllable;

public class SyllableDeleteDialog extends DialogFragment {

    private static final String ARG_SYLLABLE = "syllable";

    private SyllablesActivity syllablesActivity;
    private Syllable syllable;

    public static SyllableDeleteDialog newInstance(Syllable syllable) {
        SyllableDeleteDialog syllableDialog = new SyllableDeleteDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SYLLABLE, Parcels.wrap(syllable));
        syllableDialog.setArguments(args);
        return syllableDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        syllablesActivity = (SyllablesActivity) getActivity();
        syllable = Parcels.unwrap(getArguments().getParcelable(ARG_SYLLABLE));

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .content(R.string.confirm_delete_syllable)
                .autoDismiss(false)
                .positiveText(R.string.delete)
                .positiveColorRes(R.color.colorPrimary)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SugarRecord.delete(syllable);
                        syllablesActivity.updateSyllables();
                        dialog.dismiss();
                    }
                })
                .negativeText(R.string.cancel)
                .negativeColorRes(R.color.colorPrimary)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        syllablesActivity.getAdd().show();
                        dialog.dismiss();
                    }
                });
        final MaterialDialog materialDialog = builder.build();

        return materialDialog;
    }

}
