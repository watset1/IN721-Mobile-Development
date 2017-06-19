package bit.watset1.languagelearner;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Random;

public class QuestionManager extends AppCompatActivity
{
    Random random;
    ArrayList<GenderItem> genderItems;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        random = new Random();
        //shuffle genderItems arraylist
        //pass this into questionActivity intent
        Intent returnIntent = getIntent();
        genderItems = returnIntent.getParcelableArrayListExtra("genderItems");
        ShuffleQuestions();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private void ShuffleQuestions()
    {
        int n = genderItems.size();
        int iterations = 7 * n;

        for (int i = 0; i < iterations; i++)
        {
            int a = random.nextInt(n);
            int b = random.nextInt(n);

            while (b == a)
            {
                b = random.nextInt(n);
            }

            swap(a, b);
        }
    }

    private void swap(int a, int b)
    {
        GenderItem holder = genderItems.get(a);
        genderItems.set(a, genderItems.get(b));
        genderItems.set(b, holder);
    }

}
