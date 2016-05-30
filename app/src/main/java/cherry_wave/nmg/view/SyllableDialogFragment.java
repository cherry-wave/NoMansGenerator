package cherry_wave.nmg.view;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.widget.EditText;

import com.orm.SugarRecord;

import butterknife.BindView;
import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Syllable;

public class SyllableDialogFragment extends NMGDialogFragment {

    private static final String TAG = SyllableDialogFragment.class.getCanonicalName();
    private static final String ARG_CHARACTERS = "characters";

    private SyllableDialogListener syllableDialogListener;

    @BindView(R.id.syllable_editText)
    EditText charactersET;

    public interface SyllableDialogListener {
        public void onSyllableSave();
    }

    public static SyllableDialogFragment newInstance(String characters) {
        SyllableDialogFragment syllableDialog = new SyllableDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CHARACTERS, characters);
        syllableDialog.setArguments(args);
        return syllableDialog;
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        try {
            syllableDialogListener = (SyllableDialogListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException("Calling fragment must implement SyllableDialogListener interface");
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.title_syllable)
                .setView(inflate(R.layout.dialog_syllable))
                .setPositiveButton(R.string.save, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SugarRecord.save(new Syllable(charactersET.getText().toString()));
                        syllableDialogListener.onSyllableSave();
                    }
                })
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dismiss();
                    }
                });
        charactersET.setText(getArguments().getString(ARG_CHARACTERS));
        return builder.create();
    }

}
