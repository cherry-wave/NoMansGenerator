package cherry_wave.nmg.view.syllables;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.text.InputType;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.orm.SugarRecord;

import org.parceler.Parcels;

import cherry_wave.nmg.R;
import cherry_wave.nmg.controller.SyllableUtils;
import cherry_wave.nmg.model.Syllable;

public class SyllableSaveDialog extends DialogFragment {

    private static final String ARG_SYLLABLE = "syllable";

    private SyllablesActivity syllablesActivity;
    private Syllable syllable;

    public static SyllableSaveDialog newInstance(Syllable syllable) {
        SyllableSaveDialog syllableDialog = new SyllableSaveDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_SYLLABLE, Parcels.wrap(syllable));
        syllableDialog.setArguments(args);
        return syllableDialog;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        syllablesActivity = (SyllablesActivity) getActivity();
        syllable = Parcels.unwrap(getArguments().getParcelable(ARG_SYLLABLE));
        if (syllable == null) {
            syllable = new Syllable("");
        }

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .title(R.string.title_save_syllable)
                .autoDismiss(false)
                .positiveText(R.string.save)
                .positiveColorRes(R.color.colorPrimary)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        EditText editText = dialog.getInputEditText();
                        syllable.setCharacters(editText.getText().toString());
                        if (isValid(editText)) {
                            save();
                            dialog.dismiss();
                        }
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
                })
                .inputType(InputType.TYPE_CLASS_TEXT)
                .input(null, syllable.getCharacters(), new MaterialDialog.InputCallback() {
                    @Override
                    public void onInput(@NonNull MaterialDialog dialog, CharSequence input) {
                    }
                });
        return builder.build();
    }

    private boolean isValid(EditText editText) {
        editText.setError(null);
        String validateResponse = SyllableUtils.validate(getContext(), syllable);
        if (!validateResponse.equals(getString(R.string.ok))) {
            editText.setError(validateResponse);
            editText.requestFocus();
            return false;
        }
        return true;
    }

    private void save() {
        SugarRecord.save(syllable);
        syllablesActivity.updateSyllables();
    }

}
