package bit.watset1.sqldatabaseapp;

import android.app.Activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;


/**
 * Created by Emerson on 7/04/2017.
 */

public class DatabaseManager
{
    final String FIRSTNAME = "firstName";
    final String LASTNAME = "lastName";
    final String MAINPOSITION = "mainPosition";
    final String ISEQUALTO = " == ";
    final String SINGLEQUOTE = "'";

    HighlandersDatabase highlandersDatabase;

    public DatabaseManager(Activity activity)
    {
        highlandersDatabase = new HighlandersDatabase(activity);
    }

    public HashSet<ArrayList<String>> getSearchInfo(String selectedItem, String field)
    {
        ArrayList<String> arguments = new ArrayList<String>(Arrays.asList(field, ISEQUALTO, SINGLEQUOTE + selectedItem + SINGLEQUOTE));
        ArrayList<String> fieldsToReturn = new ArrayList<String>(Arrays.asList(FIRSTNAME, LASTNAME, MAINPOSITION));

        HashSet<ArrayList<String>> recordSet = highlandersDatabase.searchDatabase(arguments,fieldsToReturn);

        return recordSet;
    }

    public Set<String> GetItemList(String field)
    {
        return highlandersDatabase.getSingleFieldRecords(field);
    }

}
