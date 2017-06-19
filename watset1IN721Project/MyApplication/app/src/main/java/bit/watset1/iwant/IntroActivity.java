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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class IntroActivity extends AppCompatActivity
{
    Resources resources;
    ProductDatabase productDatabase;
    Button btnAddNew;
    Button btnViewAll;
    ListView lvRecentItems;
    TextView tvDefaultMessage;
    ArrayList<ProductItem> items;
    ArrayList<ProductItem> itemsLimited;
    ArrayList<String> vendors;
    Bundle dataToPass;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        //Set controls
        dataToPass = new Bundle();
        resources = getResources();
        btnAddNew = (Button)findViewById(R.id.btnAdd);
        btnViewAll = (Button)findViewById(R.id.btnView);
        lvRecentItems = (ListView)findViewById(R.id.lvRecent);
        tvDefaultMessage = (TextView)findViewById(R.id.tvDefault);

        //Create database instance
        productDatabase = new ProductDatabase(this);

        //Testing - Delete tables
        //productDatabase.DeleteAll();

        //Populate recent items
        items = productDatabase.RetrieveAllItems();

        //Add vendors to instancedState
        vendors = productDatabase.RetrieveVendors();
        dataToPass.putStringArrayList(resources.getString(R.string.vendors), vendors);

        //Limit number of recent items
        itemsLimited = limitItems(resources.getInteger(R.integer.recent_items_limit));

        //Display default message if no items in db
        if(items.size() == 0)
            tvDefaultMessage.setVisibility(View.VISIBLE);
        else
            tvDefaultMessage.setVisibility(View.INVISIBLE);

        //Button Handling
        btnAddNew.setOnClickListener(new AddNewOnClickHandler());
        btnViewAll.setOnClickListener(new ViewAllOnClickHandler());

        //Display recent items
        ProductItemArrayAdapter adapter = new ProductItemArrayAdapter(this, R.layout.product_item, itemsLimited);
        lvRecentItems.setAdapter(adapter);
    }

    //Limit the items in the listview
    private ArrayList<ProductItem> limitItems(int limit)
    {
        ArrayList<ProductItem> itemsLimited = new ArrayList<>();
        int highLimit = items.size() - 1;
        int total = 0;
        while(highLimit > -1 && total < limit)
        {
            itemsLimited.add(items.get(highLimit));
            highLimit--;
            total++;
        }
        return itemsLimited;
    }

    //Process the data, starting add edit activity
    public void ProcessItem(String processType)
    {
        Intent addNewIntent = new Intent(IntroActivity.this, AddEditActivity.class);
        dataToPass.putString(resources.getString(R.string.process_type), processType);
        addNewIntent.putExtras(dataToPass);
        startActivity(addNewIntent);
        finish();
    }

    //Custom ArrayAdapter
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
            BitmapDrawable image = ImageProcessor.ConvertBitmapToDrawable(resources, bitmap);

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

    //Code run on listitem click
    public class ListItemOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            //Retrieve product item from item list...
            int itemIndex = (int)view.getTag();
            ProductItem item = itemsLimited.get(itemIndex);
            dataToPass.putSerializable(resources.getString(R.string.passed_data_item), item);
            ProcessItem(resources.getString(R.string.process_type_edit));
        }
    }

    //Method run on add new button click
    public class AddNewOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            ProcessItem(resources.getString(R.string.process_type_add));
        }
    }

    //Code run on view all button click
    public class ViewAllOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            ProgressDialog pd = new ProgressDialog(IntroActivity.this);
            pd.setMessage(resources.getString(R.string.transition_loading));
            pd.show();
            Intent viewAllIntent = new Intent(IntroActivity.this, ViewAllActivity.class);
            startActivity(viewAllIntent);
            finish();
        }
    }
}
