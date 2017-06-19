package bit.watset1.fragmentspractical;


import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListViewFragment extends Fragment {


    public ListViewFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View fragmentView = inflater.inflate(R.layout.fragment_list_view, container, false);

        ListView lvAnimalList = (ListView) fragmentView.findViewById(R.id.lvAnimalList);

        String[] animals = {"Antelope", "Buffalo", "Coyote", "Donkey", "Emu", "Ferret", "Gorilla", "Heron" };
        ArrayAdapter<String> animalsAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, animals);
        lvAnimalList.setAdapter(animalsAdapter);

        return fragmentView;
    }

}
