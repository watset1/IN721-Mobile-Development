package bit.watset1.alertdialogpractical;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;

import static android.content.DialogInterface.BUTTON_NEGATIVE;
import static android.content.DialogInterface.BUTTON_POSITIVE;

/**
 * Created by Emerson on 29/03/2017.
 */

public class ConfirmAlert extends DialogFragment
{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog alertDialog = new AlertDialog.Builder(getActivity()).create();
        alertDialog.setIcon(R.drawable.pizzaicon);
        alertDialog.setMessage("Pizza?");
        alertDialog.setButton(BUTTON_NEGATIVE, "No", new AlertHandler());
        alertDialog.setButton(BUTTON_POSITIVE, "Yes", new AlertHandler());



        return alertDialog;
    }

    public class AlertHandler implements DialogInterface.OnClickListener
    {
        @Override
        public void onClick(DialogInterface dialogInterface, int i)
        {
            MainActivity main = (MainActivity) getActivity();
            main.ShowImage(i);
        }
    }
}
