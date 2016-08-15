package cherry_wave.nmg.view.names;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.orm.SugarRecord;

import org.parceler.Parcels;

import cherry_wave.nmg.R;
import cherry_wave.nmg.model.Name;

public class NameDeleteDialog extends DialogFragment {

    private static final String ARG_NAME = "name";

    private NamesFragment namesFragment;
    private Name name;

    public static NameDeleteDialog newInstance(Name name) {
        NameDeleteDialog nameDeleteDialog = new NameDeleteDialog();
        Bundle args = new Bundle();
        args.putParcelable(ARG_NAME, Parcels.wrap(name));
        nameDeleteDialog.setArguments(args);
        return nameDeleteDialog;
    }

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        namesFragment = (NamesFragment) getTargetFragment();
        name = Parcels.unwrap(getArguments().getParcelable(ARG_NAME));

        MaterialDialog.Builder builder = new MaterialDialog.Builder(getActivity())
                .content(R.string.confirm_delete_name)
                .autoDismiss(false)
                .positiveText(R.string.delete)
                .positiveColorRes(R.color.colorPrimary)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        SugarRecord.delete(name);
                        namesFragment.updateNames();
                        dialog.dismiss();
                    }
                })
                .negativeText(R.string.cancel)
                .negativeColorRes(R.color.colorPrimary)
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        namesFragment.getAdd().show();
                        dialog.dismiss();
                    }
                });
        return builder.build();
    }

}
