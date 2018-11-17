package com.example.tingc6190.tutorfinder.DataObject;

public class Reviewable {

    private String tutorUID;
    private boolean canReview;

    public Reviewable(String tutorUID, boolean canReview) {
        this.tutorUID = tutorUID;
        this.canReview = canReview;
    }

    public String getTutorUID() {
        return tutorUID;
    }

    public void setTutorUID(String tutorUID) {
        this.tutorUID = tutorUID;
    }

    public boolean isCanReview() {
        return canReview;
    }

    public void setCanReview(boolean canReview) {
        this.canReview = canReview;
    }
}
