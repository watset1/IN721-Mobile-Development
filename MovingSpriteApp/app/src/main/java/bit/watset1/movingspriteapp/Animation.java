package bit.watset1.movingspriteapp;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.AnimationDrawable;

/**
 * Created by Emerson on 11/05/2017.
 */

public class Animation
{
    final String IDLE_SHEET = "idle_sheet";
    final String WALK_SIDE_SHEET = "walk_sheet";
    final String WALK_DOWN_SHEET = "walk_down_sheet";
    final String WALK_UP_SHEET = "walk_up_sheet";
    final String DRAWABLE = "drawable";
    final int IDLE_FRAMES = 1;
    final int WALK_FRAMES = 6;

    Resources resources;
    AnimationDrawable idle;
    AnimationDrawable walk_side;
    AnimationDrawable walk_down;
    AnimationDrawable walk_up;

    int spriteWidth;
    int spriteHeight;
    int speed;

    int idle_sheet;
    int walk_side_sheet;
    int walk_down_sheet;
    int walk_up_sheet;

    public Animation(Context context, Resources resources, int spriteWidth, int spriteHeight, int speed)
    {
        this.resources = resources;
        this.spriteWidth = spriteWidth;
        this.spriteHeight = spriteHeight;
        this.speed = speed;
        idle_sheet = resources.getIdentifier(IDLE_SHEET, DRAWABLE, context.getPackageName());
        walk_side_sheet = resources.getIdentifier(WALK_SIDE_SHEET, DRAWABLE, context.getPackageName());
        walk_down_sheet = resources.getIdentifier(WALK_DOWN_SHEET, DRAWABLE, context.getPackageName());
        walk_up_sheet = resources.getIdentifier(WALK_UP_SHEET, DRAWABLE, context.getPackageName());
        setAnimations();
    }

    private void setAnimations()
    {
        idle = AnimationCreator.CreateAnimation(resources, idle_sheet, IDLE_FRAMES, spriteWidth, spriteHeight, speed);
        walk_side = AnimationCreator.CreateAnimation(resources, walk_side_sheet, WALK_FRAMES, spriteWidth, spriteHeight, speed);
        walk_down = AnimationCreator.CreateAnimation(resources, walk_down_sheet, WALK_FRAMES, spriteWidth, spriteHeight, speed);
        walk_up = AnimationCreator.CreateAnimation(resources, walk_up_sheet, WALK_FRAMES, spriteWidth, spriteHeight, speed);
    }
}
