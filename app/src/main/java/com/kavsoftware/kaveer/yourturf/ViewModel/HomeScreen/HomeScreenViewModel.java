package com.kavsoftware.kaveer.yourturf.ViewModel.HomeScreen;

import java.io.Serializable;

/**
 * Created by kaveer on 3/4/2017.
 */
@SuppressWarnings("serial")
public class HomeScreenViewModel implements Serializable {
    private Boolean isRaceCardAvailable;
    public Integer meetingNumber;
    public Object notificationMessage;
    public Boolean notification;

    public Integer getMeetingNumber() {
        return meetingNumber;
    }

    public void setMeetingNumber(Integer meetingNumber) {
        this.meetingNumber = meetingNumber;
    }

    public Object getNotificationMessage() {
        return notificationMessage;
    }

    public void setNotificationMessage(Object notificationMessage) {
        this.notificationMessage = notificationMessage;
    }

    public Boolean getNotification() {
        return notification;
    }

    public void setNotification(Boolean notification) {
        this.notification = notification;
    }

    public Boolean getIsRaceCardAvailable() {
        return isRaceCardAvailable;
    }

    public void setIsRaceCardAvailable(Boolean isRaceCardAvailable) {
        this.isRaceCardAvailable = isRaceCardAvailable;
    }
}
