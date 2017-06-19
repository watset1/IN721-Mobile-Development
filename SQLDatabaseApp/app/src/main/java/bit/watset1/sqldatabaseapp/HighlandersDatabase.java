package bit.watset1.sqldatabaseapp;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Emerson on 6/04/2017.
 */

public class HighlandersDatabase
{
    SQLiteDatabase highlandersDb;

    public HighlandersDatabase(Activity activity)
    {
        highlandersDb = activity.openOrCreateDatabase("highlanders", MODE_PRIVATE, null);
        createTables();
        seedDatabase();
    }

    private void createTables()
    {
        String dropQuery = "DROP TABLE IF EXISTS tblPlayer";
        highlandersDb.execSQL(dropQuery);

        String createQuery = "CREATE TABLE IF NOT EXISTS tblPlayer(" +
                             "playerID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                             "firstName TEXT NOT NULL, " +
                             "lastName TEXT NOT NULL, " +
                             "mainPosition TEXT NOT NULL);";
        highlandersDb.execSQL(createQuery);
        seedDatabase();
    }


    private void seedDatabase()
    {
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Luke', 'Whitelock', 'Lock')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Tom', 'Franklin', 'Lock')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Ryan', 'Tongia', 'Flanker')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Sio', 'Tomkinson', 'Centre')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Jackson', 'Hemopo', 'Lock')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Malakai', 'Fekitoa', 'Centre')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Aaron', 'Smith', 'Scrum Half')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Jason', 'Emery', 'Centre')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Ross', 'Geldenhuys', 'Prop')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Patrick', 'Osbourne', 'Winger')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Lee', 'Allan', 'Flanker')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Josh', 'Hohneck', 'Prop')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Hayden', 'Parker', 'Fly Half')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Sam', 'Anderson-Heather', 'Hooker')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Alex', 'Ainley', 'Lock')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Nasi', 'Manu', 'Number 8')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Joe', 'Latta', 'Lock')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Lima', 'Sopoaga', 'Fly Half')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Ben', 'Smith', 'Fullback')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Marty', 'Banks', 'Fullback')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Pingi', 'Tala apitaga', 'Prop')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Daniel', 'Pryor', 'Flanker')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Ash', 'Dixon', 'Hooker')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Liam', 'Squire', 'Number 8')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Richard', 'Buckman', 'Centre')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Scott', 'Eade', 'Scrum Half')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Ma afu', 'Fia', 'Prop')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Teihorangi', 'Walden', 'Centre')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'John', 'Hardie', 'Flanker')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Josh', 'Renton', 'Scrum Half')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'James', 'Lentjes', 'Flanker')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Fumiaki', 'Tanaka', 'Scrum Half')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Elliot', 'Dixon', 'Number 8')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Kurt', 'Baker', 'Flanker')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Liam', 'Coltman', 'Hooker')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Kane', 'Hames', 'Prop')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Waisake', 'Naholo', 'Winger')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Daniel', 'Lienert-Brown', 'Prop')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Rob', 'Thompson', 'Centre')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Shane', 'Christie', 'Flanker')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Mark', 'Reddish', 'Lock')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Brendon', 'Edmonds', 'Prop')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Greg', 'Pleasants-Tate', 'Prop')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Shaun', 'Treebie', 'Centre')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Joe', 'Wheeler', 'Flanker')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Matt', 'Faddes', 'Centre')");
        highlandersDb.execSQL("INSERT INTO tblPlayer VALUES(null, 'Gareth', 'Evans', 'Flanker')");
    }

    public HashSet<ArrayList<String>> searchDatabase(ArrayList<String> arguments, ArrayList<String> fieldsToReturn)
    {
        HashSet<ArrayList<String>> recordHashSet = new HashSet<ArrayList<String>>();
        String selectQuery = "SELECT * FROM tblPlayer WHERE ";

        for (int i = 0; i < arguments.size(); i++)
            selectQuery += arguments.get(i) + " ";

        Cursor recordSet = highlandersDb.rawQuery(selectQuery, null);
        int recordCount = recordSet.getCount();
        recordSet.moveToFirst();

        for (int r = 0; r < recordCount; r++)
        {
            ArrayList<String> record = new ArrayList<String>();
            for (String returnField : fieldsToReturn)
            {
                int columnIndex = recordSet.getColumnIndex(returnField);
                record.add(recordSet.getString(columnIndex));
            }
            recordHashSet.add(record);

            recordSet.moveToNext();
        }

        recordSet.close();
        return recordHashSet;
    }

    public Set<String> getSingleFieldRecords(String field)
    {
        TreeSet<String> fieldList = new TreeSet<String>();
        String selectQuery = "SELECT " + field + " FROM tblPlayer";

        Cursor recordSet = highlandersDb.rawQuery(selectQuery, null);
        int recordCount = recordSet.getCount();
        recordSet.moveToFirst();

        for (int r = 0; r < recordCount; r++)
        {
            int columnIndex = recordSet.getColumnIndex(field);
            fieldList.add(recordSet.getString(columnIndex));

            recordSet.moveToNext();
        }

        recordSet.close();
        return fieldList;
    }
}
