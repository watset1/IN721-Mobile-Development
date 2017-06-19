package bit.watset1.iwant;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;

import java.io.Serializable;

/**
 * Created by Emerson on 5/06/2017.
 */

public class ProductItem implements Serializable
{
    int ProductId;
    String ImagePath;
    String ProductName;
    String ProductVendor;
    String Notes;
    double ProductPrice;

    public ProductItem(int ProductId, String ImagePath, String ProductName, String ProductVendor, String Notes, double ProductPrice)
    {
        this.ProductId = ProductId;
        this.ImagePath = ImagePath;
        this.ProductName = ProductName;
        this.ProductVendor = ProductVendor;
        this.Notes = Notes;
        this.ProductPrice = ProductPrice;
    }
}
