package bit.watset1.iwant;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;

import java.text.DecimalFormat;
import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Emerson on 6/06/2017.
 */

public class ProductDatabase
{
    SQLiteDatabase ProductDb;

    public ProductDatabase(Activity activity)
    {
        ProductDb = activity.openOrCreateDatabase("iwantproducts", MODE_PRIVATE, null);
        CreateTables();
    }

    public void CreateTables()
    {
        String createQuery = "CREATE TABLE IF NOT EXISTS tblProduct(" +
                "productId INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "productName TEXT NOT NULL, " +
                "productVendor TEXT NOT NULL, " +
                "productNotes TEXT NOT NULL," +
                "productPrice REAL NOT NULL," +
                "imagePath TEXT NOT NULL);";
        ProductDb.execSQL(createQuery);
    }

    public void DeleteAll()
    {
        String dropQuery = "DROP TABLE IF EXISTS tblProduct";
        ProductDb.execSQL(dropQuery);
        CreateTables();
    }


    public void AddItem(String productName, String productVendor, String productNotes, Double productPrice, String imagePath)
    {
        String insertQuery = "INSERT INTO tblProduct VALUES(null, " + productName + ", " + productVendor + ", " + productNotes + ", " + productPrice + ", " + imagePath + ");";
        ProductDb.execSQL(insertQuery);
    }

    public void DeleteItem(int productId)
    {
        String deleteQuery = "DELETE FROM tblProduct WHERE productId = " + productId + ";";
        ProductDb.execSQL(deleteQuery);
    }

    public ArrayList<ProductItem> RetrieveAllItems()
    {
        ArrayList<ProductItem> items = new ArrayList<ProductItem>();
        String selectQuery = "SELECT * FROM tblProduct";

        Cursor recordSet = ProductDb.rawQuery(selectQuery, null);
        int recordCount = recordSet.getCount();
        recordSet.moveToFirst();

        for (int r = 0; r < recordCount; r++)
        {
            int productId = recordSet.getInt(EProductField.productId.ordinal());
            String productName = recordSet.getString(EProductField.productName.ordinal());
            String productVendor = recordSet.getString(EProductField.productVendor.ordinal());
            String productNotes = recordSet.getString(EProductField.productNotes.ordinal());
            Double productPrice = recordSet.getDouble(EProductField.productPrice.ordinal());
            String imagePath = recordSet.getString(EProductField.imagePath.ordinal());

            items.add(new ProductItem(productId, imagePath, productName, productVendor, productNotes, productPrice));

            recordSet.moveToNext();
        }

        recordSet.close();
        return items;
    }

    public ArrayList<ProductItem> RetrieveSearch(String query)
    {
        ArrayList<ProductItem> items = new ArrayList<ProductItem>();
        String[] words = query.split(" ");
        String search = "";

        int nWords = words.length;
        for (int i = 0; i < nWords; i++)
        {
            if(!words[i].equals(""))
            {
                search += "productName LIKE '%" + words[i] + "%' OR productVendor LIKE '%" + words[i] + "%'";
                if (i != (nWords - 1))
                    search += " OR ";
            }
        }

        search += ";";
        String selectQuery = "SELECT * FROM tblProduct WHERE " + search;

        Cursor recordSet = ProductDb.rawQuery(selectQuery, null);
        int recordCount = recordSet.getCount();
        recordSet.moveToFirst();

        for (int r = 0; r < recordCount; r++)
        {
            int productId = recordSet.getInt(EProductField.productId.ordinal());
            String productName = recordSet.getString(EProductField.productName.ordinal());
            String productVendor = recordSet.getString(EProductField.productVendor.ordinal());
            String productNotes = recordSet.getString(EProductField.productNotes.ordinal());
            Double productPrice = recordSet.getDouble(EProductField.productPrice.ordinal());
            String imagePath = recordSet.getString(EProductField.imagePath.ordinal());

            items.add(new ProductItem(productId, imagePath, productName, productVendor, productNotes, productPrice));

            recordSet.moveToNext();
        }

        recordSet.close();
        return items;
    }

    public ArrayList<String> RetrieveVendors()
    {
        ArrayList<String> vendors = new ArrayList<>();
        String selectQuery = "SELECT * FROM tblProduct";

        Cursor recordSet = ProductDb.rawQuery(selectQuery, null);
        int recordCount = recordSet.getCount();
        recordSet.moveToFirst();

        for (int r = 0; r < recordCount; r++)
        {
            String productVendor = recordSet.getString(EProductField.productVendor.ordinal());
            if(!vendors.contains(productVendor))
                vendors.add(productVendor);
            recordSet.moveToNext();
        }

        recordSet.close();
        return vendors;
    }
}
