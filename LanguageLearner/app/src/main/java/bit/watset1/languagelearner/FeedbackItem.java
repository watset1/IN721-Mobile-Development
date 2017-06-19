package bit.watset1.languagelearner;

import android.app.Activity;
import android.content.res.Resources;

/**
 * Created by Emerson on 31/03/2017.
 */

public class FeedbackItem
{
    Resources resources;
    public int questionImageId;
    public String answer;
    public int correctImageId;

    public FeedbackItem(int questionImageId, String answer, Boolean correct, Activity activity)
    {
        resources = activity.getResources();
        this.questionImageId = questionImageId;
        this.answer = answer;
        setCorrectImage(correct, activity);
    }

    private void setCorrectImage(Boolean correct, Activity activity)
    {
        if(correct)
            correctImageId = resources.getIdentifier("tick", "drawable", activity.getPackageName());
        else
            correctImageId = resources.getIdentifier("cross", "drawable", activity.getPackageName());
    }
}
