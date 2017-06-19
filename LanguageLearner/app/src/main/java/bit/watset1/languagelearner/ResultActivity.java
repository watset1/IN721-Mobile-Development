package bit.watset1.languagelearner;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {
    ScoreManager scoreManager;
    ArrayList<GenderItem> genderItems;
    ArrayList<FeedbackItem> feedbackItems;
    TextView txtPercent;
    ListView lvFeedback;
    Button btnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        txtPercent = (TextView)findViewById(R.id.txtPercent);
        lvFeedback = (ListView)findViewById(R.id.lvFeedback);
        feedbackItems = new ArrayList<FeedbackItem>();

        //Get intent data
        Intent questionIntent = getIntent();
        scoreManager = questionIntent.getParcelableExtra("scoreManager");
        genderItems = questionIntent.getParcelableArrayListExtra("genderItems");

        //Button handling
        btnFinish = (Button)findViewById(R.id.btnFinish);
        ButtonOnClickHandler buttonOnClickHandler = new ButtonOnClickHandler();
        btnFinish.setOnClickListener(buttonOnClickHandler);

        //CreateFeedbackItems
        initialiseFeedbackList();

        //ListView adapter handling
        FeedbackItemArrayAdapter feedbackAdapter = new FeedbackItemArrayAdapter(this, R.layout.answer_list_item, feedbackItems);
        lvFeedback.setAdapter(feedbackAdapter);
        //Display results
        displayResult();
    }

    //Add public inner class extending from ArrayAdapter
    public class FeedbackItemArrayAdapter extends ArrayAdapter<FeedbackItem>
    {

        public FeedbackItemArrayAdapter(Context context, int resource, ArrayList<FeedbackItem> objects)
        {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            LayoutInflater inflater = LayoutInflater.from(ResultActivity.this);

            View customView = inflater.inflate(R.layout.answer_list_item, parent, false);

            ImageView itemImageView = (ImageView) customView.findViewById(R.id.ivAnswerImage);
            TextView itemTextView = (TextView) customView.findViewById(R.id.txtAnswerFeedback);
            ImageView itemCorrectView = (ImageView) customView.findViewById(R.id.ivCorrectFeedback);

            FeedbackItem currentItem = getItem(position);
            int questionImageId = currentItem.questionImageId;
            String answerString = currentItem.answer;
            int feedbackImageId = currentItem.correctImageId;
            itemImageView.setImageResource(questionImageId);
            itemTextView.setText("Your answer : " + answerString);
            itemCorrectView.setImageResource(feedbackImageId);

            return customView;
        }
    }

    private int getPercentage()
    {
        int score = (scoreManager.TotalScore   * 100) / genderItems.size();
        return score;
    }

    private void initialiseFeedbackList()
    {
        for (int i = 0; i < genderItems.size(); i++)
            feedbackItems.add(new FeedbackItem(genderItems.get(i).ImageId, scoreManager.Answers.get(i), scoreManager.CorrectQuestions.get(i), this));
    }


    private void displayResult()
    {
        txtPercent.setText(String.valueOf(getPercentage() + "%"));
    }

    public class ButtonOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent restartIntent = new Intent(ResultActivity.this, MenuScreen.class);
            startActivity(restartIntent);
            finish();
        }
    }


}
