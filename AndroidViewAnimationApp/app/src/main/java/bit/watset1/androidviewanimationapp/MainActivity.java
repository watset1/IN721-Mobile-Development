package bit.watset1.androidviewanimationapp;

import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

public class MainActivity extends AppCompatActivity
{
    ImageView ivStandImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ivStandImage = (ImageView)findViewById(R.id.ivStandImage);
        ivStandImage.setImageResource(R.drawable.aidan);
        ivStandImage.setOnClickListener(new StandUpButtonHandler());
    }

    private class StandUpButtonHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            YoYo.with(Techniques.StandUp)
                    .duration(1000)
                    .repeat(0)
                    .playOn(ivStandImage);
        }
    }
}
