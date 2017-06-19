package bit.watset1.welcometodunedin;


import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


/**
 * A simple {@link Fragment} subclass.
 */
public class NavigationFragment extends Fragment {
    View fragmentView;
    Resources resourceResolver;
    ListView lvLinksList;

    public NavigationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        resourceResolver = getResources();
        fragmentView = inflater.inflate(R.layout.fragment_navigation, container, false);
        lvLinksList = (ListView) fragmentView.findViewById(R.id.lvLinksList);
        ListViewHandler listViewHandler = new ListViewHandler();

        String[] links = { "Activities", "Shopping", "Dining", "Services" };
        ArrayAdapter<String> linksAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, links);
        lvLinksList.setAdapter(linksAdapter);
        lvLinksList.setOnItemClickListener(listViewHandler);
        return fragmentView;
    }

    public class ListViewHandler implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String clickedItemString = (String)adapterView.getItemAtPosition(i);
            int imageId = 0;

            switch(clickedItemString)
            {
                case "Activities":
                    imageId =  R.drawable.activities;
                    break;

                case "Shopping":
                    imageId =  R.drawable.shopping;
                    break;

                case "Dining":
                    imageId =  R.drawable.dining;
                    break;

                case "Services":
                    imageId =  R.drawable.services;
                    break;
            }


            TextView txtTitle = (TextView)getActivity().findViewById(R.id.txtTitle);
            ImageView ivImage = (ImageView)getActivity().findViewById(R.id.ivScreenImage);
            txtTitle.setText(clickedItemString);
            ivImage.setImageResource(imageId);
        }
    }
}
