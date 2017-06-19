package bit.watset1.languagelearner;

import android.media.Image;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Emerson on 15/03/2017.
 */

public class GenderItem implements Parcelable
{
    public String German;
    public String English;
    public String Gender;
    public String Article;
    public int ImageId;

    public GenderItem(String german, String english, String gender, String article, int imageId)
    {
        German = german;
        English = english;
        Gender = gender;
        Article = article;
        ImageId = imageId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(German);
        parcel.writeString(English);
        parcel.writeString(Gender);
        parcel.writeString(Article);
        parcel.writeInt(ImageId);
    }

    public static final Parcelable.Creator<GenderItem> CREATOR
            = new Parcelable.Creator<GenderItem>() {
        public GenderItem createFromParcel(Parcel in) {
            return new GenderItem(in);
        }

        public GenderItem[] newArray(int size) {
            return new GenderItem[size];
        }
    };

    private GenderItem(Parcel in)
    {
        German = in.readString();
        English = in.readString();
        Gender = in.readString();
        Article = in.readString();
        ImageId = in.readInt();
    }
}
