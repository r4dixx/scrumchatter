/**
 * Copyright 2013 Carmen Alvarez
 *
 * This file is part of Scrum Chatter.
 *
 * Scrum Chatter is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 2 of the License, or
 * (at your option) any later version.
 *
 * Scrum Chatter is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Scrum Chatter. If not, see <http://www.gnu.org/licenses/>.
 */
package ca.rmen.android.scrumchatter.ui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import ca.rmen.android.scrumchatter.Constants;

/**
 * A dialog fragment with a title, message, ok and cancel buttons.
 */
public class ScrumChatterConfirmDialogFragment extends DialogFragment {

    private static final String TAG = Constants.TAG + "/" + ScrumChatterConfirmDialogFragment.class.getSimpleName();

    /**
     * An activity which contains a confirmation dialog fragment should implement this interface to be notified if the user clicks ok on the dialog.
     */
    public interface ScrumChatterDialogButtonListener {
        void onOkClicked(int actionId, Bundle extras);
    }

    public ScrumChatterConfirmDialogFragment() {
        super();
    }

    /**
     * @return an AlertDialog with a title, message, ok, and cancel buttons.
     */
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Log.v(TAG, "onCreateDialog: savedInstanceState = " + savedInstanceState);

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        Bundle arguments = getArguments();
        builder.setTitle(arguments.getString(ScrumChatterDialogFragmentFactory.EXTRA_TITLE)).setMessage(
                arguments.getString(ScrumChatterDialogFragmentFactory.EXTRA_MESSAGE));
        final int actionId = arguments.getInt(ScrumChatterDialogFragmentFactory.EXTRA_ACTION_ID);
        final Bundle extras = arguments.getBundle(ScrumChatterDialogFragmentFactory.EXTRA_EXTRAS);
        OnClickListener positiveListener = null;
        if (getActivity() instanceof ScrumChatterDialogButtonListener) {
            positiveListener = new OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ((ScrumChatterDialogButtonListener) getActivity()).onOkClicked(actionId, extras);
                }
            };
        }
        builder.setNegativeButton(android.R.string.cancel, null);
        builder.setPositiveButton(android.R.string.ok, positiveListener);
        final AlertDialog dialog = builder.create();
        DialogStyleHacks.styleDialog(getActivity(), dialog);
        return dialog;

    }
}