package bit.watset1.complexdata;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by Emerson on 31/03/2017.
 */

public class TeamImage  extends DialogFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.team_image_layout, container, false);
        int imageId = getArguments().getInt("imageId");
        ImageView ivTeamImage = (ImageView)v.findViewById(R.id.ivDialog);
        ivTeamImage.setImageResource(imageId);
        return v;
    }



}
