package bit.watset1.iwant;

import android.app.FragmentManager;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.DatabaseUtils;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.util.ArrayList;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class AddEditActivity extends AppCompatActivity
{
    ProgressDialog pd;
    Resources resources;
    NotesDialog notesDialog;
    CustomSpinner spProductVendor;
    EditText etProductName;
    EditText etProductPrice;
    EditText etProductVendor;
    ImageView ivImage;
    Button btnNotes;
    ImageButton btnCancel;
    ImageButton btnDeleteItem;
    ImageButton btnSaveItem;
    ArrayList<String> vendors;
    ArrayList<String> inputList;
    String currentNotes;
    String imagePath;
    String processType;
    Boolean onOpen;
    ProductItem passedItem;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new);
        resources = getResources();
        pd = new ProgressDialog(this);

        //Initialise boolean to supress initial vendor selection
        onOpen = true;

        //Get process type
        Intent previousIntent = getIntent();
        Bundle passedData = previousIntent.getExtras();
        processType = passedData.getString(resources.getString(R.string.process_type));

        //Initialise empty notes, passedItem and imagePath
        currentNotes = "";
        passedItem = null;
        imagePath = "";

        //Set controls
        etProductName = (EditText)findViewById(R.id.etProductName);
        etProductVendor = (EditText)findViewById(R.id.etProductVendor);
        ivImage = (ImageView)findViewById(R.id.ivProductImage);

        //Set currency formatting
        etProductPrice = (EditText)findViewById(R.id.etProductPrice);
        CurrencyTextWatcher tw = new CurrencyTextWatcher();
        etProductPrice.addTextChangedListener(tw);
        etProductPrice.setRawInputType(Configuration.KEYBOARD_12KEY);

        //Set spinner
        spProductVendor = (CustomSpinner)findViewById(R.id.spProductVendor);
        vendors = this.getIntent().getExtras().getStringArrayList(resources.getString(R.string.vendors));
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item , vendors);
        spProductVendor.setAdapter(adapter);
        spProductVendor.setOnItemSelectedListener(new OnSpinnerItemSelected());
        if(VendorsExist())
            spProductVendor.setVisibility(View.VISIBLE);
        else
            spProductVendor.setVisibility(View.GONE);

        //Button Handling
        btnNotes = (Button)findViewById(R.id.btnNotes);
        btnCancel = (ImageButton)findViewById(R.id.btnCancel);
        btnDeleteItem = (ImageButton)findViewById(R.id.btnDelete);
        btnSaveItem = (ImageButton)findViewById(R.id.btnSave);
        btnNotes.setOnClickListener(new OnNotesClickedHandler());
        btnCancel.setOnClickListener(new OnCancelClickedHandler());
        btnDeleteItem.setOnClickListener(new OnDeleteClickedHandler());
        btnSaveItem.setOnClickListener(new OnSaveItemClickedHandler());

        if(processType.equals(resources.getString(R.string.process_type_add)))
        {
            //Start the camera
            startCamera();
        }
        else
        {
            //Retrieve pass item
            passedItem = (ProductItem)passedData.getSerializable(resources.getString(R.string.passed_data_item));

            //Set control text and visibility
            etProductName.setText(passedItem.ProductName);
            etProductVendor.setText(passedItem.ProductVendor);
            etProductPrice.setText(String.format(resources.getString(R.string.currency_format), passedItem.ProductPrice));
            currentNotes = passedItem.Notes;
            imagePath = passedItem.ImagePath;
            btnDeleteItem.setVisibility(View.VISIBLE);

            //Populate Image
            Bitmap bitmap = ImageProcessor.ProcessPhotoBitmap(passedItem.ImagePath);
            BitmapDrawable image = ImageProcessor.ConvertBitmapToDrawable(getResources(), bitmap);
            ivImage.setImageDrawable(image);
        }
    }

    //Method used by the dialog to update notes
    public void UpdateCurrentNotes(String updatedNotes)
    {
        currentNotes = updatedNotes;
    }

    //Method to check for existence of vendors
    private Boolean VendorsExist()
    {
        if(vendors.size() > 0)
            return true;
        else
            return false;
    }

    //Error checking for user input
    private Boolean InputErrorCheck()
    {
        //Initialise boolean to return
        Boolean correct = false;

        //Populate arraylist with user input from edit text fields
        inputList = new ArrayList<>();
        inputList.add(resources.getString(R.string.input_list_stub));
        inputList.add(etProductName.getText().toString());
        inputList.add(etProductVendor.getText().toString());
        inputList.add(currentNotes);
        inputList.add(etProductPrice.getText().toString());

        //Imagepath generated by passedData or camera
        inputList.add(imagePath);

        //For every error add error location
        ArrayList<Integer> errors = new ArrayList<>();
        int inputSize = inputList.size();
        for (int i = 0; i < inputSize; i++)
        {
            if ((inputList.get(i) == null || inputList.get(i).equals("")) && (i != EProductField.productNotes.ordinal())) //Checks all input except notes for a valid string
                errors.add(i);
        }
        //Get amount of errors
        int errorAmount = errors.size();
        if(errorAmount != 0) //If there are errors display feedback
        {
            ArrayList<String> errorStrings = new ArrayList<>();
            String errorString = "";
            for (int i = 0; i < errorAmount; i++)
                errorStrings.add((EProductField.values()[errors.get(i)]).toString());

            for(String str : errorStrings)
            {
                String[] words = str.split(resources.getString(R.string.input_error_regex));
                str = words[1].toLowerCase();
                String start = str.substring(0, 1);
                String end = str.substring(1);
                str = start.toUpperCase() + end;
                errorString += str + "\n";
            }
            //Display feedback - adjust for better readability
            Toast.makeText(this, resources.getString(R.string.input_error_feedback) + errorString, Toast.LENGTH_LONG).show();
        }
        else //If there are no errors return true
            correct = true;

        return correct;
    }

    //displays the captured image
    private void displayImage(BitmapDrawable bitmapDrawable)
    {
        ivImage.setImageDrawable(bitmapDrawable);
    }

    //Starts previous activity ending current activity
    private void returnToPrevious(String feedback)
    {
        Intent returnIntent = null;
        returnIntent = new Intent(AddEditActivity.this, ViewAllActivity.class);
        returnIntent.putExtra(resources.getString(R.string.addedit_feedback), feedback);
        startActivity(returnIntent);
        finish();
    }

    //Method to run camera intent
    private void startCamera()
    {
        Uri photoFileUri = ImageProcessor.PrepareCapture();
        Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoFileUri);
        startActivityForResult(imageCaptureIntent, 1);
    }

    //Delete the current item
    public void DeleteItem(int option)
    {
        if(option == BUTTON_POSITIVE)
        {
            showDialog(resources.getString(R.string.transition_processing_deletion));
            ProductDatabase productDatabase = new ProductDatabase(AddEditActivity.this);
            productDatabase.DeleteItem(passedItem.ProductId);
            String feedback = resources.getString(R.string.addedit_feedback_deleted);
            returnToPrevious(feedback);
        }
    }

    private void showDialog(String message)
    {
        pd.setMessage(message);
        pd.show();
    }

    //Overriding back press functionality - starts a new intro activity instance
    @Override
    public void onBackPressed()
    {
        showDialog(resources.getString(R.string.transition_loading));
        returnToPrevious(resources.getString(R.string.addedit_feedback_add_error));
    }

    //Processes returned camera information
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 1)
        {
            if(resultCode == RESULT_OK)
            {
                imagePath = ImageProcessor.photoFile.getPath();
                Bitmap bitmap = ImageProcessor.ProcessPhotoBitmap(imagePath);
                BitmapDrawable bitmapDrawable = ImageProcessor.ConvertBitmapToDrawable(getResources(), bitmap);
                displayImage(bitmapDrawable);
            }
            else
            {
                String feedback = resources.getString(R.string.addedit_feedback_photo_error);
                returnToPrevious(feedback);
            }
        }
    }

    //Inner class for currency formatting
    public class CurrencyTextWatcher implements TextWatcher
    {
        String current = "";

        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2){}

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2)
        {
            if(!charSequence.toString().equals(current))
            {
                etProductPrice.removeTextChangedListener(this);

                if(!charSequence.toString().equals(""))
                {
                    String cleanString = charSequence.toString().replaceAll("[$,.]", "");

                    double parsed = Double.parseDouble(cleanString);
                    String formatted = NumberFormat.getCurrencyInstance().format((parsed / 100));

                    current = formatted;
                    etProductPrice.setText(formatted);
                    etProductPrice.setSelection(formatted.length());
                }

                etProductPrice.addTextChangedListener(this);
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {}
    }

    //Listener for spinner when item is selected
    public class OnSpinnerItemSelected implements AdapterView.OnItemSelectedListener
    {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
        {
            if(!onOpen)
            {
                String selectedItem = (String) spProductVendor.getSelectedItem();
                etProductVendor.setText(selectedItem);
            }
            onOpen = false;
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView){}
    }

    //Listener for notes button click
    public class OnNotesClickedHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            notesDialog = new NotesDialog();
            Bundle noteArgs = new Bundle();
            noteArgs.putString(resources.getString(R.string.addedit_notes_string), currentNotes);
            notesDialog.setArguments(noteArgs);
            FragmentManager fm = getFragmentManager();
            notesDialog.show(fm, resources.getString(R.string.addedit_notes_reference));
        }
    }

    //Handler for when cancel button is clicked
    public class OnCancelClickedHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            showDialog(resources.getString(R.string.transition_loading));
               returnToPrevious(resources.getString(R.string.addedit_feedback_add_error)); //May change to view all (previous activity)
        }
    }

    //Handler for when delete button is clicked
    public class OnDeleteClickedHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            FragmentManager fm = getFragmentManager();
            ConfirmAlert confirmAlert = new ConfirmAlert();
            confirmAlert.show(fm, resources.getString(R.string.addedit_confirm_delete));
        }
    }

    //Handler for when save button is clicked
    public class OnSaveItemClickedHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            if(InputErrorCheck())
            {
                showDialog(resources.getString(R.string.transition_processing_addition));
                ProductDatabase productDatabase = new ProductDatabase(AddEditActivity.this);

                String productName = inputList.get(EProductField.productName.ordinal());
                productName = DatabaseUtils.sqlEscapeString(productName);

                String productVendor = inputList.get(EProductField.productVendor.ordinal());
                productVendor = DatabaseUtils.sqlEscapeString(productVendor);

                String productPrice = inputList.get(EProductField.productPrice.ordinal());
                String splitPrice = (String)productPrice.subSequence(1, (productPrice.length()));
                splitPrice = splitPrice.replace(",", "");
                Double price = Double.valueOf(splitPrice);

                String imagePath = inputList.get(EProductField.imagePath.ordinal());
                imagePath = DatabaseUtils.sqlEscapeString(imagePath);

                String productNotes = DatabaseUtils.sqlEscapeString(currentNotes);

                if(processType.equals(resources.getString(R.string.process_type_edit)))
                    productDatabase.DeleteItem(passedItem.ProductId);

                productDatabase.AddItem(productName, productVendor, productNotes, price, imagePath);
                String feedback = resources.getString(R.string.addedit_feedback_added);
                returnToPrevious(feedback);
            }
        }
    }
}
