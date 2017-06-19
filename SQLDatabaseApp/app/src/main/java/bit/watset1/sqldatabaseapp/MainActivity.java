package bit.watset1.sqldatabaseapp;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

public class MainActivity extends AppCompatActivity {
    Resources resources;
    DatabaseManager dbManager;
    Spinner lastNameSpinner;
    Button btnSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resources = getResources();
        dbManager = new DatabaseManager(this);

        //populate spinner
        lastNameSpinner = (Spinner)findViewById(R.id.lastNameSpinner);
        setLastNameSpinner();

        //search lastname button handling
        btnSearch = (Button)findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(new ButtonOnClickHandler());
    }

    //Method for spinner adapter binding
    private void bindSpinnerAdapter(Spinner spinner, String[] array)
    {
        int layoutID = android.R.layout.simple_spinner_item;
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, layoutID, array);
        spinner.setAdapter(adapter);
    }

    //Method to populate the spinner by binding it with an array
    private void setLastNameSpinner()
    {
        String searchfield = resources.getString(R.string.main_searchfield_lastname);
        Set<String> fieldList = new TreeSet<String>();
        fieldList = dbManager.GetItemList(searchfield);
        String[] records = fieldList.toArray(new String[fieldList.size()]);
        bindSpinnerAdapter(lastNameSpinner, records);
    }

    //Method to display a set of search results
    private void displayPlayerRecords(Set<ArrayList<String>> recordSet)
    {
        ArrayList<Player> playerRecords = new ArrayList<Player>();

        for (ArrayList<String> record : recordSet)
            playerRecords.add(Parser.parsePlayerRecords(record));

        PlayerItemArrayAdapter playerDataAdapter = new PlayerItemArrayAdapter(this, R.layout.search_list_item, playerRecords);
        ListView lvPlayerData = (ListView)findViewById(R.id.lvPlayerData);
        lvPlayerData.setAdapter(playerDataAdapter);
    }

    //Method to set the result category titles
    private void setCategoryTitles()
    {
        TextView txtFirstNameTitle = (TextView)findViewById(R.id.txtFirstNameTitle);
        TextView txtLastNameTitle = (TextView)findViewById(R.id.txtLastNameTitle);
        TextView txtPositionTitle = (TextView)findViewById(R.id.txtPositionTitle);

        txtFirstNameTitle.setText(resources.getString(R.string.main_title_firstname));
        txtLastNameTitle.setText(resources.getString(R.string.main_title_lastname));
        txtPositionTitle.setText(resources.getString(R.string.main_title_position));
        LinearLayout categories = (LinearLayout)findViewById(R.id.layoutCategories);
        categories.setVisibility(View.VISIBLE);
    }


    public class ButtonOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            String searchfield = resources.getString(R.string.main_searchfield_lastname);
            String field = (String) lastNameSpinner.getSelectedItem();
            HashSet<ArrayList<String>> searchInfo = dbManager.getSearchInfo(field, searchfield);
            displayPlayerRecords(searchInfo);
            setCategoryTitles();
        }
    }

    //Add public inner class extending from ArrayAdapter
    public class PlayerItemArrayAdapter extends ArrayAdapter<Player>
    {
        public PlayerItemArrayAdapter(Context context, int resource, ArrayList<Player> objects)
        {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);

            View customView = inflater.inflate(R.layout.search_list_item, parent, false);

            TextView tvFirstName = (TextView) customView.findViewById(R.id.txtFirstName);
            TextView tvLastName = (TextView) customView.findViewById(R.id.txtLastName);
            TextView tvPosition = (TextView) customView.findViewById(R.id.txtPosition);

            Player currentItem = getItem(position);

            String firstName = currentItem.firstName;
            String lastName = currentItem.lastName;
            String mainPosition = currentItem.position;

            tvFirstName.setText(firstName);
            tvLastName.setText(lastName);
            tvPosition.setText(mainPosition);

            return customView;
        }
    }
}
