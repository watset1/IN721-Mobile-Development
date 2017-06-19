package bit.watset1.complexdata;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class TeamsActivity extends AppCompatActivity {
    ArrayList<Team> teamArray;
    Bundle savedInstanceState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.savedInstanceState = savedInstanceState;
        Intent mainIntent = getIntent();
        teamArray =  mainIntent.getParcelableArrayListExtra("teams");
        TeamArrayAdapter teamAdapter = new TeamArrayAdapter(this, R.layout.custom_listview_item, teamArray);
        ListView teamList = (ListView)findViewById(R.id.lvDivisions);
        ListViewHandler listHandler = new ListViewHandler();
        teamList.setAdapter(teamAdapter);
        teamList.setOnItemClickListener(listHandler);
    }

    //Add public inner class extending from ArrayAdapter
    public class TeamArrayAdapter extends ArrayAdapter<Team>
    {

        public TeamArrayAdapter(Context context, int resource, ArrayList<Team> objects)
        {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = LayoutInflater.from(TeamsActivity.this);

            View customView = inflater.inflate(R.layout.custom_listview_item, parent, false);

            ImageView itemImageView = (ImageView) customView.findViewById(R.id.ivItemImage);
            TextView itemTextView = (TextView) customView.findViewById(R.id.txtItemText);

            Team currentTeam = getItem(position);
            int imageId = currentTeam.getLogo();
            itemImageView.setTag(imageId);
            itemImageView.setImageResource(imageId);
            itemTextView.setText(currentTeam.getName());

            return customView;
        }
    }

    public class ListViewHandler implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
        {
            int imageId = (int)((ImageView)view.findViewById(R.id.ivItemImage)).getTag();
            Bundle teamData = new Bundle();
            teamData.putInt("imageId", imageId);
            TeamImage teamImage = new TeamImage();
            teamImage.setArguments(teamData);
            FragmentManager fm = getFragmentManager();
            teamImage.show(fm, "showImage");
        }
    }
}
