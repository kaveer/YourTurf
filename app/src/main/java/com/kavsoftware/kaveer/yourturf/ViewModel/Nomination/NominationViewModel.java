package com.kavsoftware.kaveer.yourturf.ViewModel.Nomination;

import java.util.List;

/**
 * Created by kaveer on 3/6/2017.
 */

public class NominationViewModel {
    public int raceCount;
    public List<Race> race = null;

    public Integer getRaceCount() {
        return raceCount;
    }

    public void setRaceCount(int raceCount) {
        this.raceCount = raceCount;
    }

    public List<Race> getRace() {
        return race;
    }

    public void setRace(List<Race> race) {
        this.race = race;
    }
}
