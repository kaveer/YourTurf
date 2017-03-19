package com.kavsoftware.kaveer.yourturf.ViewModel.Nomination;

import java.util.ArrayList;

/**
 * Created by kaveer on 3/6/2017.
 */

public class NominationViewModel extends ArrayList {
    public int raceCount;
    public ArrayList<Race> race = null;

    public Integer getRaceCount() {
        return raceCount;
    }

    public void setRaceCount(int raceCount) {
        this.raceCount = raceCount;
    }

    public ArrayList<Race> getRace() {
        return race;
    }

    public void setRace(ArrayList<Race> race) {
        this.race = race;
    }
}
