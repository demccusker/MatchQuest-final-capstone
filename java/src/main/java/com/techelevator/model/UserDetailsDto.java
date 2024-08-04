package com.techelevator.model;

public class UserDetailsDto {

    private String displayName;
    private int eloRating;
    private boolean isStaff;

    public UserDetailsDto(String displayName, int eloRating, boolean isStaff) {
        this.displayName = displayName;
        this.eloRating = eloRating;
        this.isStaff = isStaff;
    }
    public UserDetailsDto(){};

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public int getEloRating() {
        return eloRating;
    }

    public void setEloRating(int eloRating) {
        this.eloRating = eloRating;
    }

    public boolean isStaff() {
        return isStaff;
    }

    public void setStaff(boolean staff) {
        isStaff = staff;
    }
}
