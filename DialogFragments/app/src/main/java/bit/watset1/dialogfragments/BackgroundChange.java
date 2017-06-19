package bit.watset1.dialogfragments;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by Emerson on 24/03/2017.
 */

public class BackgroundChange extends DialogFragment
{
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.background_change_layout, container, false);
        ListView lvAnimalList = (ListView) v.findViewById(R.id.lvImages);

        String[] animals = { "Antelope", "Dog", "Owl", "Tiger" };
        ArrayAdapter<String> animalsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, animals);
        lvAnimalList.setAdapter(animalsAdapter);
        ListViewHandler handler = new ListViewHandler();
        lvAnimalList.setOnItemClickListener(handler);

        return v;
    }

    public class ListViewHandler implements AdapterView.OnItemClickListener
    {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            String clickedItemString = (String)adapterView.getItemAtPosition(i);
            int imageId = 0;

            switch(clickedItemString)
            {
                case "Antelope":
                    imageId =  R.drawable.antelope;
                    break;

                case "Dog":
                    imageId =  R.drawable.dog;
                    break;

                case "Owl":
                    imageId =  R.drawable.owl;
                    break;

                case "Tiger":
                    imageId =  R.drawable.tiger;
                    break;
            }

            MainActivity main = (MainActivity) getActivity();
            main.ProcessData(imageId);
        }
    }
}
