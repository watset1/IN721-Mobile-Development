package bit.watset1.iwant;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class ViewAllActivity extends AppCompatActivity
{
    ProgressDialog pd;
    Resources resources;
    Button btnAddNew;
    Button btnSearch;
    ListView lvViewAll;
    TextView tvFeedback;
    EditText etSearch;
    Bundle dataToPass;
    ArrayList<ProductItem> items;
    ArrayList<String> vendors;
    ProductDatabase productDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_all);
        resources = getResources();
        pd = new ProgressDialog(this);

        //Initialise data passing bundle
        dataToPass = new Bundle();

        //Create database instance
        productDatabase = new ProductDatabase(this);

        lvViewAll = (ListView)findViewById(R.id.lvViewAll);
        etSearch = (EditText)findViewById(R.id.etSearch);
        tvFeedback = (TextView)findViewById(R.id.tvFeedback);

        //Populate any feedback meesages
        Intent intent = getIntent();
        String feedback = intent.getStringExtra(resources.getString(R.string.addedit_feedback));
        tvFeedback.setText(feedback);

        //Button handling
        btnSearch = (Button)findViewById(R.id.btnGo);
        btnSearch.setOnClickListener(new SearchOnClickHandler());
        btnAddNew = (Button)findViewById(R.id.btnAdd);
        btnAddNew.setOnClickListener(new AddNewOnClickHandler());

        //Pass vendor info
        vendors = productDatabase.RetrieveVendors();
        dataToPass.putStringArrayList(resources.getString(R.string.vendors), vendors);

        //Populate items
        items = productDatabase.RetrieveAllItems();

        //Display all items
        ProductItemArrayAdapter adapter = new ProductItemArrayAdapter(this, R.layout.product_item, items);
        lvViewAll.setAdapter(adapter);
    }

    //Toggle search button enability
    private void toggleSearchButton()
    {
        btnSearch.setEnabled(!btnSearch.isEnabled());
    }

    //Retrieves product information based on the search parameters
    private void search()
    {
        String searchQuery = etSearch.getText().toString();
        if(searchQuery.length() > 0)
            items = productDatabase.RetrieveSearch(searchQuery);
    }

    //Displays the search results in the listview
    private void displaySearchResults()
    {
        ProductItemArrayAdapter adapter = new ProductItemArrayAdapter(this, R.layout.product_item, items);
        lvViewAll.setAdapter(adapter);
    }

    //Return to intro screen
    private void returnToIntro()
    {
        Intent returnIntent  = new Intent(ViewAllActivity.this, IntroActivity.class);
        startActivity(returnIntent);
        finish();
    }

    //Processes the intent type and starts add edit activity
    public void ProcessItem(String processType)
    {
        Intent addNewIntent = new Intent(ViewAllActivity.this, AddEditActivity.class);
        dataToPass.putString(resources.getString(R.string.process_type), processType);
        addNewIntent.putExtras(dataToPass);
        startActivity(addNewIntent);
        finish();
    }

    private void showDialog(String message)
    {
        pd.setMessage(message);
        pd.show();
    }

    @Override
    public void onBackPressed()
    {
        showDialog(resources.getString(R.string.transition_loading));
        returnToIntro();
    }

    //Custom array adapter for product items
    public class ProductItemArrayAdapter extends ArrayAdapter<ProductItem>
    {
        public ProductItemArrayAdapter(Context context, int resource, ArrayList<ProductItem> objects)
        {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());

            View customView = inflater.inflate(R.layout.product_item, parent, false);

            ImageView ivImage = (ImageView)customView.findViewById(R.id.ivImage);
            TextView tvProductName = (TextView)customView.findViewById(R.id.tvProductName);
            TextView tvProductPrice = (TextView)customView.findViewById(R.id.tvProductCost);

            ProductItem currentItem = getItem(position);
            Bitmap bitmap = ImageProcessor.ProcessPhotoBitmap(currentItem.ImagePath);
            BitmapDrawable image = ImageProcessor.ConvertBitmapToDrawable(getResources(), bitmap);

            customView.setTag(position);
            if(image != null)
            {
                ivImage.setImageDrawable(image);
                ivImage.setRotation(90f);
            }

            tvProductName.setText(currentItem.ProductName);
            String price = String.format(resources.getString(R.string.currency_format), currentItem.ProductPrice);
            tvProductPrice.setText("$" + price);

            customView.setOnClickListener(new ListItemOnClickHandler());
            return customView;
        }
    }

    //Code run on single item clicked in listview
    public class ListItemOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            //Need to retrieve product item from item list...
            int itemIndex = (int)view.getTag();
            ProductItem item = items.get(itemIndex);
            dataToPass.putSerializable(resources.getString(R.string.passed_data_item), item);
            ProcessItem(resources.getString(R.string.process_type_edit));
        }
    }

    //Code to run on add new button clicked
    public class AddNewOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            ProcessItem(resources.getString(R.string.process_type_add));
        }
    }

    //Code to run on search button click
    public class SearchOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            SearchTask search = new SearchTask();
            search.execute();
        }
    }

    //Async task to process database retrieval of search parameters
    public class SearchTask extends AsyncTask<Void, Void, Void>
    {
        @Override
        protected void onPreExecute()
        {
            toggleSearchButton();
            pd = new ProgressDialog(ViewAllActivity.this);
            pd.setMessage(resources.getString(R.string.fetch_loading_message));
            pd.show();
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... voids)
        {
            search();
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid)
        {
            pd.dismiss();
            toggleSearchButton();
            displaySearchResults();
        }
    }
}
