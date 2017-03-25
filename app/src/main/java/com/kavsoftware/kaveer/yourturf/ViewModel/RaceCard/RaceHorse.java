package com.kavsoftware.kaveer.yourturf.ViewModel.RaceCard;

import java.io.Serializable;

/**
 * Created by kaveer on 3/18/2017.
 */

public class RaceHorse implements Serializable {
    public String perf;
    public Integer age;
    public String gear;
    public String weight;
    public String jockey;
    public Integer draw;
    public String timeFactor;
    public String horseName;
    public Integer horseNumber;
    public String stable;
    public Integer raceNumber;

    public String getPerf() {
        return perf;
    }

    public void setPerf(String perf) {
        this.perf = perf;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGear() {
        return gear;
    }

    public void setGear(String gear) {
        this.gear = gear;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getJockey() {
        return jockey;
    }

    public void setJockey(String jockey) {
        this.jockey = jockey;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public String getTimeFactor() {
        return timeFactor;
    }

    public void setTimeFactor(String timeFactor) {
        this.timeFactor = timeFactor;
    }

    public String getHorseName() {
        return horseName;
    }

    public void setHorseName(String horseName) {
        this.horseName = horseName;
    }

    public Integer getHorseNumber() {
        return horseNumber;
    }

    public void setHorseNumber(Integer horseNumber) {
        this.horseNumber = horseNumber;
    }

    public String getStable() {
        return stable;
    }

    public void setStable(String stable) {
        this.stable = stable;
    }

    public Integer getRaceNumber() {
        return raceNumber;
    }

    public void setRaceNumber(Integer raceNumber) {
        this.raceNumber = raceNumber;
    }
}
