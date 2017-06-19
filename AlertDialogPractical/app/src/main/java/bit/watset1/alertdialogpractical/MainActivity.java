package bit.watset1.alertdialogpractical;

import android.app.FragmentManager;
import android.content.res.Resources;
import android.media.Image;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import static android.content.DialogInterface.BUTTON_POSITIVE;

public class MainActivity extends AppCompatActivity {
    Resources resourceResolver;
    ConfirmAlert confirmAlert;
    ImageView ivImage;
    Button btnChooseButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        resourceResolver = getResources();
        btnChooseButton = (Button)findViewById(R.id.btnOrder);
        OrderPizzaButtonHandler pizzaHandler = new OrderPizzaButtonHandler();
        btnChooseButton.setOnClickListener(pizzaHandler);
        ivImage = (ImageView) findViewById(R.id.ivImage);
    }

    public class OrderPizzaButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            confirmAlert = new ConfirmAlert();
            FragmentManager fm = getFragmentManager();
            confirmAlert.show(fm, "order");
        }
    }

    public int getImage(int buttonChosen)
    {
        int imageId = 0;
        if(buttonChosen == BUTTON_POSITIVE)
            imageId = R.drawable.happypizza;
        else
            imageId = R.drawable.sadpizza;

        return imageId;
    }

    public void ShowImage(int buttonChosen)
    {
        ivImage.setImageResource(getImage(buttonChosen));
    }
}
