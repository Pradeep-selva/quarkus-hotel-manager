package com.pradeepselva.hotelmanagement.joins;


import java.util.Date;

public class ReservationJoin {
    Long reservationId;
    Long guestId;
    Long roomId;

    Date resDate;
    String bedInfo;
    String roomNumber;
    String fullName;
    String emailId;
    String phoneNumber;

    public ReservationJoin(
            Long reservationId, Long guestId, Long roomId, Date resDate, String bedInfo, String roomNumber, String fullName, String emailId, String phoneNumber
    ) {
        this.reservationId = reservationId;
        this.guestId = guestId;
        this.roomId = roomId;
        this.resDate = resDate;
        this.bedInfo = bedInfo;
        this.roomNumber = roomNumber;
        this.fullName = fullName;
        this.emailId = emailId;
        this.phoneNumber = phoneNumber;
    }

    public Long getReservationId() {
        return reservationId;
    }

    public void setReservationId(Long reservationId) {
        this.reservationId = reservationId;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Date getResDate() {
        return resDate;
    }

    public void setResDate(Date resDate) {
        this.resDate = resDate;
    }

    public String getBedInfo() {
        return bedInfo;
    }

    public void setBedInfo(String bedInfo) {
        this.bedInfo = bedInfo;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(String roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
