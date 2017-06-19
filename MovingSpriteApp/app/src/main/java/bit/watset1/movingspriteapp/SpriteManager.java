package bit.watset1.movingspriteapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by Emerson on 11/05/2017.
 */

public class SpriteManager
{
    final int GUYBRUSH_SPEED = 10;

    ArrayList<Animation> sprites;
    ArrayList<ImageView> spriteHolders;
    ArrayList<Integer> spriteSpeeds;

    public SpriteManager(View ivSprite, Context context, Resources resources, ArrayList<ImageView> spriteHolders)
    {
        sprites = new ArrayList<Animation>();
        spriteSpeeds = new ArrayList<Integer>();
        this.spriteHolders = spriteHolders;
        setSprites(context, resources);
    }

    private void setSprites(Context context, Resources resources)
    {
        //Add Guybrush
        sprites.add(new Animation(context, resources,  AnimationDetails.GUYBRUSH_WIDTH, AnimationDetails.GUBRUSH_HEIGHT, AnimationDetails.GUYBRUSH_ITERATION_SPEED));
        spriteSpeeds.add(GUYBRUSH_SPEED);
    }

    public void MoveSprite(float x, float y, Sprites spriteIndex)
    {
        ImageView ivSprite = spriteHolders.get(spriteIndex.ordinal());
        Animation currentSprite = sprites.get(spriteIndex.ordinal());
        int speed = spriteSpeeds.get(spriteIndex.ordinal());
        AnimationDrawable currentAnimation = null;

        float xSpeed = speed;
        float ySpeed = xSpeed;
        float xScale = 1;
        if(x <= -1 || x >= 1)
        {
            if(x >= 1)
            {
                xSpeed *= -1;
                xScale *= -1;
            }

            ivSprite.setScaleX(xScale);
            currentAnimation = currentSprite.walk_side;
            ivSprite.setX(ivSprite.getX() + xSpeed);
        }
        if(y <= -1)
        {
            if(y <= -1)
                ySpeed *= -1;

            currentAnimation = currentSprite.walk_up;
            ivSprite.setY(ivSprite.getY() + ySpeed);
        }
        if(y >= 1)
        {
            currentAnimation = currentSprite.walk_down;
            ivSprite.setY(ivSprite.getY() + ySpeed);
        }

        if(y < 1 && y > -1 && x < 1 && x > -1)
            currentAnimation = currentSprite.idle;

        ivSprite.setImageDrawable(currentAnimation);
        currentAnimation.start();
    }
}
