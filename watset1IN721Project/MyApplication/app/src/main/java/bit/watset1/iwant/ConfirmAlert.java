package bit.watset1.iwant;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Created by Emerson on 13/06/2017.
 */

//Class to display and return confirmation of deletion of item
public class ConfirmAlert extends DialogFragment
{
    final String CONFIRM_DELETE = "Delete this item?";
    final String CONFIRM_YES = "Yes";
    final String CONFIRM_NO = "No";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setMessage(CONFIRM_DELETE);
        alertDialog.setButton(BUTTON_NEGATIVE, CONFIRM_NO, new AlertHandler());
        alertDialog.setButton(BUTTON_POSITIVE, CONFIRM_YES, new AlertHandler());

        return alertDialog;
    }

    public class AlertHandler implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialogInterface, int i)
        {
            AddEditActivity main = (AddEditActivity) getActivity();
            main.DeleteItem(i);
        }
    }
}
