package com.kavsoftware.kaveer.yourturf.ViewModel;

/**
 * Created by kaveer on 2/24/2017.
 */

public class HorseDetails {
    private String horseName;
    private Integer horseNumber;
    private String stable;
    private Double handicap;
    private Integer value;

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

    public Double getHandicap() {
        return handicap;
    }
    public void setHandicap(Double handicap) {
        this.handicap = handicap;
    }

    public Integer getValue() {
        return value;
    }
    public void setValue(Integer value) {
        this.value = value;
    }
}
