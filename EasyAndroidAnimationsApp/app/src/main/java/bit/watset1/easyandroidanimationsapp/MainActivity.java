package bit.watset1.easyandroidanimationsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.DecelerateInterpolator;
import android.widget.ImageView;
import android.widget.Toast;

import com.easyandroidanimations.library.Animation;
import com.easyandroidanimations.library.AnimationListener;
import com.easyandroidanimations.library.ExplodeAnimation;

public class MainActivity extends AppCompatActivity {
    ImageView ivImageToExplode;
    ExplodeAnimation explodeAnimation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivImageToExplode = (ImageView)findViewById(R.id.ivAndroidMan);
        explodeAnimation = new ExplodeAnimation(ivImageToExplode);
        explodeAnimation.setDuration(500);
        explodeAnimation.setExplodeMatrix(ExplodeAnimation.MATRIX_3X3);
        explodeAnimation.setInterpolator(new DecelerateInterpolator());
        explodeAnimation.setListener(new ExplodeAnimationListener());

        ivImageToExplode.setOnClickListener(new ImageExploderHandler());
    }

    private class ImageExploderHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view) {
            explodeAnimation.animate();
        }
    }

    private class ExplodeAnimationListener implements AnimationListener
    {
        @Override
        public void onAnimationEnd(Animation animation) {
            Toast.makeText(getApplicationContext(), "The image has exploded....Booom", Toast.LENGTH_SHORT).show();
        }
    }
}
