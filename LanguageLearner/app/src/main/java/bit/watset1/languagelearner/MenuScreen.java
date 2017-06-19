package bit.watset1.languagelearner;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

public class MenuScreen extends AppCompatActivity {
    Resources resourceResolver;
    ArrayList<GenderItem> genderItems;
    Button btnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_screen);
        resourceResolver = getResources();

        //Button handling
        btnStart = (Button) findViewById(R.id.btnStart);
        ButtonOnClickHandler OnClickHandler = new ButtonOnClickHandler();
        btnStart.setOnClickListener(OnClickHandler);

        //Initialize list of gender items
        genderItems = new ArrayList<GenderItem>();

        //Fill the arrayList
        populateGenderItems();
    }

    private void populateGenderItems()
    {
        //Hard coded items
        genderItems.add(new GenderItem("Apfel", "Apple", "Masculine", "Der", resourceResolver.getIdentifier("apple", "drawable", this.getPackageName())));
        genderItems.add(new GenderItem("Auto", "Car", "Neutral", "Das", resourceResolver.getIdentifier("car", "drawable", this.getPackageName())));
        genderItems.add(new GenderItem("Baum", "Tree", "Masculine", "Der", resourceResolver.getIdentifier("tree", "drawable", this.getPackageName())));
        genderItems.add(new GenderItem("Ente", "Duck", "Feminine", "Die", resourceResolver.getIdentifier("duck", "drawable", this.getPackageName())));
        genderItems.add(new GenderItem("Haus", "House", "Neutral", "Das", resourceResolver.getIdentifier("house", "drawable", this.getPackageName())));
        genderItems.add(new GenderItem("Hexe", "Witch", "Feminine", "Die", resourceResolver.getIdentifier("witch", "drawable", this.getPackageName())));
        genderItems.add(new GenderItem("Kuh", "Cow", "Feminine", "Die", resourceResolver.getIdentifier("cow", "drawable", this.getPackageName())));
        genderItems.add(new GenderItem("Milch", "Milk", "Feminine", "Die", resourceResolver.getIdentifier("milk", "drawable", this.getPackageName())));
        genderItems.add(new GenderItem("Schaf", "Sheep", "Neutral", "Das", resourceResolver.getIdentifier("sheep", "drawable", this.getPackageName())));
        genderItems.add(new GenderItem("Strasse", "Street", "Feminine", "Die", resourceResolver.getIdentifier("street", "drawable", this.getPackageName())));
        genderItems.add(new GenderItem("Stuhl", "Chair", "Masculine", "Der", resourceResolver.getIdentifier("chair", "drawable", this.getPackageName())));
    }

    public class ButtonOnClickHandler implements View.OnClickListener
    {
        @Override
        public void onClick(View view)
        {
            Intent shuffleIntent = new Intent(MenuScreen.this, QuestionManager.class);
            //pass genderItems into QuestionManager intent
            shuffleIntent.putExtra("genderItems", genderItems);
            //Start question manager expecting a returned inte4nt holding a shuffled arraylist
            startActivityForResult(shuffleIntent, 0);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(requestCode == 0)
        {
            genderItems = data.getParcelableArrayListExtra("genderItems");
            Intent questionIntent = new Intent(MenuScreen.this, QuestionActivity.class);
            questionIntent.putExtra("genderItems", genderItems);
            startActivity(questionIntent);
        }
    }
}
