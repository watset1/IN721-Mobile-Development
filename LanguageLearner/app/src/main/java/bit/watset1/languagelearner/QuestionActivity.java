package bit.watset1.languagelearner;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class QuestionActivity extends AppCompatActivity
{
    private final int DELAY = 1000;

    Resources resourceResolver;
    ScoreManager scoreManager;
    ImageView image;
    Button btnDer;
    Button btnDie;
    Button btnDas;
    TextView txtRound;
    TextView txtFeedback;

    Handler handler;
    FeedbackPause feedBackPause;

    ArrayList<GenderItem> genderItems;
    int roundCount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);
        resourceResolver = getResources();

        //Get shuffled arraylist from previous intent
        Intent menuIntent = getIntent();
        genderItems = menuIntent.getParcelableArrayListExtra("genderItems");
        scoreManager = new ScoreManager(genderItems.size());

        //Set the round index to 0
        roundCount = 0;

        //Display controls
        image = (ImageView) findViewById(R.id.ivGenderImage);
        txtRound = (TextView)findViewById(R.id.txtRound);
        txtFeedback = (TextView)findViewById(R.id.txtFeedback);

        //Button handling
        ButtonOnClickHandler OnClickHandler = new ButtonOnClickHandler();
        btnDer = (Button)findViewById(R.id.btnDer);
        btnDie = (Button)findViewById(R.id.btnDie);
        btnDas = (Button)findViewById(R.id.btnDas);
        btnDer.setOnClickListener(OnClickHandler);
        btnDie.setOnClickListener(OnClickHandler);
        btnDas.setOnClickListener(OnClickHandler);

        //Initialize runnable and handler for pause to allow feedback to display
        handler = new Handler();
        feedBackPause = new FeedbackPause(QuestionActivity.this);

        //Display the first question and instructions
        displayQuestion(roundCount);
        txtFeedback.setText(resourceResolver.getString(R.string.question_instructions));
    }

    //Sets the controls to reflect the current rounds question
    private void displayQuestion(int round)
    {
        txtRound.setText(String.valueOf((round + 1)));
        image.setImageResource(genderItems.get(round).ImageId);
    }

    //Toggles the answer buttons on or off
    private void toggleButtons()
    {
        btnDer.setEnabled(!btnDer.isEnabled());
        btnDie.setEnabled(!btnDie.isEnabled());
        btnDas.setEnabled(!btnDas.isEnabled());
    }

    public class ButtonOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            toggleButtons();
            //Get the text from the button clicked
            Button pressedButton = (Button)view;
            String userAnswer = (String)pressedButton.getText();

            //Check if the answer is correct
            Boolean correct = scoreManager.AnswerCheck(genderItems, userAnswer, roundCount);

            //Store results of question
            if(correct)
                scoreManager.TotalScore++;

            scoreManager.AddItem(correct);
            scoreManager.AddAnswerString(userAnswer);


            //Display whether the user was right or wrong
            txtFeedback.setText(displayTextFeedback(correct));
            pressedButton.setBackgroundColor(displayColorFeedback(correct));

            //Run delayed feedback pause, allowing feedback to appear for 2 seconds
            handler.postDelayed(feedBackPause, DELAY);
        }
    }

    //Runnable Wrapper for code running after feddback appears
    public class FeedbackPause implements Runnable
    {
        public FeedbackPause(Activity activity) {}

        @Override
        public void run()
        {
            //increment the round count
            roundCount++;
            //if all questions have been shown change to result screen
            if(roundCount == genderItems.size())
            {
                Intent resultIntent = new Intent(QuestionActivity.this, ResultActivity.class);
                //Add scoreManager and genderItems arraylist for reference
                resultIntent.putExtra("scoreManager", scoreManager);
                resultIntent.putExtra("genderItems", genderItems);
                //Start result activity
                startActivity(resultIntent);
                //End activity
                finish();
            }
            else
            {
                //Display next question
                displayQuestion(roundCount);
                //Re-enable buttons
                toggleButtons();
            }

            //Revert screen background to original
            revertDisplay();
        }
    }

    private int displayColorFeedback(Boolean correct)
    {
        int colorId = 0;
        if(correct) //set background green
            colorId = resourceResolver.getColor(R.color.correctColor);
        else    //set background red
            colorId = resourceResolver.getColor(R.color.incorrectColor);

        return colorId;
    }

    private String displayTextFeedback(Boolean correct)
    {
        String text = null;
        if(correct) //display "correct"
            text = resourceResolver.getString(R.string.question_correct);
        else    //display "incorrect"
            text = resourceResolver.getString(R.string.question_incorrect);

        return text;
    }

    //Revert the feedback displays to normal
    private void revertDisplay()
    {
        btnDer.setBackgroundColor(resourceResolver.getColor(R.color.questionButtonColor));
        btnDie.setBackgroundColor(resourceResolver.getColor(R.color.questionButtonColor));
        btnDas.setBackgroundColor(resourceResolver.getColor(R.color.questionButtonColor));
        txtFeedback.setText(resourceResolver.getString(R.string.question_instructions));
    }
}
