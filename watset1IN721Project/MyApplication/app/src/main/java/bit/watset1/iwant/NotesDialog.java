package bit.watset1.iwant;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

/**
 * Created by Emerson on 6/06/2017.
 */

public class NotesDialog extends DialogFragment
{
    String currentNotes;
    EditText etDialogNotes;
    Button btnSaveNotes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.dialog_notes, container, false);
        etDialogNotes = (EditText)v.findViewById(R.id.etEditNotes);
        btnSaveNotes = (Button)v.findViewById(R.id.btnSaveNotes);
        currentNotes = getArguments().getString(getResources().getString(R.string.addedit_notes_string));
        etDialogNotes.setText(currentNotes);
        btnSaveNotes.setOnClickListener(new OnSavedClick());

        return v;
    }

    public class OnSavedClick implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            currentNotes = etDialogNotes.getText().toString();
            AddEditActivity act = (AddEditActivity)getActivity();
            act.UpdateCurrentNotes(currentNotes);
            dismiss();
        }
    }
}
