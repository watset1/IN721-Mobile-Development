package bit.watset1.welcometodunedin;

import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LinkFragment extends Fragment
{

    public LinkFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_link, container, false);
        return fragmentView;
    }
}
