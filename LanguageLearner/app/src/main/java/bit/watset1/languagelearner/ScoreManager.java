package bit.watset1.languagelearner;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Emerson on 15/03/2017.
 */

public class ScoreManager implements Parcelable
{
    public int TotalScore;
    public ArrayList<Boolean> CorrectQuestions;
    public ArrayList<String> Answers;

    public ScoreManager(int nQuestions)
    {
        TotalScore = 0;
        CorrectQuestions = new ArrayList<Boolean>();
        Answers = new ArrayList<String>();
    }

    public boolean AnswerCheck(ArrayList<GenderItem> genderItems, String userAnswer, int roundCount)
    {
        String correctAnswer = genderItems.get(roundCount).Article;
        if(userAnswer.equalsIgnoreCase(correctAnswer))
            return true;
        else
            return false;
    }

    public void AddItem(Boolean answer)
    {
        CorrectQuestions.add(answer);
    }

    public void AddAnswerString(String answer)
    {
        Answers.add(answer);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(TotalScore);
        parcel.writeList(CorrectQuestions);
        parcel.writeList(Answers);
    }

    public static final Parcelable.Creator<ScoreManager> CREATOR
            = new Parcelable.Creator<ScoreManager>() {
        public ScoreManager createFromParcel(Parcel in) {
            return new ScoreManager(in);
        }

        public ScoreManager[] newArray(int size) {
            return new ScoreManager[size];
        }
    };

    private ScoreManager(Parcel in)
    {
        TotalScore = in.readInt();
        CorrectQuestions = in.readArrayList(Boolean.class.getClassLoader());
        Answers = in.readArrayList(String.class.getClassLoader());
    }
}
