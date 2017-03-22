package com.kavsoftware.kaveer.yourturf.ViewModel.RaceCard;

import java.util.ArrayList;

/**
 * Created by kaveer on 3/18/2017.
 */

public class RaceCardViewModel {
    public Integer raceCount;
    public ArrayList<Race> race = null;

    public Integer getRaceCount() {
        return raceCount;
    }

    public void setRaceCount(Integer raceCount) {
        this.raceCount = raceCount;
    }

    public ArrayList<Race> getRace() {
        return race;
    }

    public void setRace(ArrayList<Race> race) {
        this.race = race;
    }
}
