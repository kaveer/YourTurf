package com.kavsoftware.kaveer.yourturf.ViewModel;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by kaveer on 3/4/2017.
 */

public class HomeScreenViewModel {
    private Boolean isRaceCardAvailable;

    @JsonProperty("isRaceCardAvailable")
    public Boolean getIsRaceCardAvailable() {
        return isRaceCardAvailable;
    }

    public void setIsRaceCardAvailable(Boolean isRaceCardAvailable) {
        this.isRaceCardAvailable = isRaceCardAvailable;
    }
}
