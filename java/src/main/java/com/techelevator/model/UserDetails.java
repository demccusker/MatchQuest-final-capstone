package com.techelevator.model;

public class UserDetails {

    private int detailId;

    private int userId;

    private String displayName;

    private int eloRating;

    private boolean isStaff;

    public UserDetails() {
    }
    public UserDetails(int detailId, int userId, String displayName, int eloRating, boolean isStaff) {
        this.detailId = detailId;
        this.userId = userId;
        this.displayName = displayName;
        this.eloRating = eloRating;
        this.isStaff = isStaff;
    }

    public int getDetailId() {
        return detailId;
    }

    public int getUserId() {
        return userId;
    }

    public String getDisplayName() {
        return displayName;
    }

    public int getEloRating() {
        return eloRating;
    }

    public boolean getIsStaff() {
        return isStaff;
    }

    public void setDetailId(int detailId) {
        this.detailId = detailId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setEloRating(int eloRating) {
        this.eloRating = eloRating;
    }

    public void setIsStaff(boolean isStaff) {
       this.isStaff = isStaff;
    }
}
