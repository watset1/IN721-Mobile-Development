package bit.watset1.complexdata;

import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Emerson on 31/03/2017.
 */

public class Team implements Parcelable
{
    private int logoId;
    private String name;

    public Team(int logoId, String name)
    {
        this.logoId = logoId;
        this.name = name;
    }

    public int getLogo() {
        return logoId;
    }

    public String getName() {
        return name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(logoId);
        parcel.writeString(name);
    }

    public static final Parcelable.Creator<Team> CREATOR
            = new Parcelable.Creator<Team>() {
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    private Team(Parcel in)
    {
        logoId = in.readInt();
        name = in.readString();
    }
}
