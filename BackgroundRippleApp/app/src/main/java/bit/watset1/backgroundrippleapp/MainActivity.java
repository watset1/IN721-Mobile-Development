package bit.watset1.backgroundrippleapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.skyfishjy.library.RippleBackground;

public class MainActivity extends AppCompatActivity
{
    RippleBackground rippleBackground;
    ImageView ivAnimToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rippleBackground = (RippleBackground)findViewById(R.id.content);
        ivAnimToggle = (ImageView)findViewById(R.id.ivAnimToggle);
        ivAnimToggle.setOnClickListener(new ToggleAnimationHandler());
    }

    private class ToggleAnimationHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            if(!rippleBackground.isRippleAnimationRunning())
                rippleBackground.startRippleAnimation();
            else
                rippleBackground.stopRippleAnimation();
        }
    }
}
