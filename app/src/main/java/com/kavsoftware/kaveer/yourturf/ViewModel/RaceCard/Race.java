package com.kavsoftware.kaveer.yourturf.ViewModel.RaceCard;

import java.util.List;

/**
 * Created by kaveer on 3/18/2017.
 */

public class Race {
    public Integer raceNumber;
    public String distance;
    public String valueBenchmark;
    public String time;
    public String raceName;
    public Integer horseCount;
    public List<RaceHorse> raceHorses = null;

    public Integer getRaceNumber() {
        return raceNumber;
    }

    public void setRaceNumber(Integer raceNumber) {
        this.raceNumber = raceNumber;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getValueBenchmark() {
        return valueBenchmark;
    }

    public void setValueBenchmark(String valueBenchmark) {
        this.valueBenchmark = valueBenchmark;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRaceName() {
        return raceName;
    }

    public void setRaceName(String raceName) {
        this.raceName = raceName;
    }

    public Integer getHorseCount() {
        return horseCount;
    }

    public void setHorseCount(Integer horseCount) {
        this.horseCount = horseCount;
    }

    public List<RaceHorse> getRaceHorses() {
        return raceHorses;
    }

    public void setRaceHorses(List<RaceHorse> raceHorses) {
        this.raceHorses = raceHorses;
    }
}
