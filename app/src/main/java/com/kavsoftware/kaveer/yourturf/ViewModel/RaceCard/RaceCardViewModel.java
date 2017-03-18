package com.kavsoftware.kaveer.yourturf.ViewModel.RaceCard;

import java.util.List;

/**
 * Created by kaveer on 3/18/2017.
 */

public class RaceCardViewModel {
    public Integer raceCount;
    public List<Race> race = null;

    public Integer getRaceCount() {
        return raceCount;
    }

    public void setRaceCount(Integer raceCount) {
        this.raceCount = raceCount;
    }

    public List<Race> getRace() {
        return race;
    }

    public void setRace(List<Race> race) {
        this.race = race;
    }
}
