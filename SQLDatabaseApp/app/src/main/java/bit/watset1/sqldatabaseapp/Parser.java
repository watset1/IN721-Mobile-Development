package bit.watset1.sqldatabaseapp;

import java.util.ArrayList;

/**
 * Created by Emerson on 7/04/2017.
 */

public final class Parser
{
    public static Player parsePlayerRecords(ArrayList<String> record)
    {
        String firstName = record.get(0);
        String lastName = record.get(1);
        String position = record.get(2);
        Player player = new Player(firstName, lastName, position);

        return player;
    }

}
