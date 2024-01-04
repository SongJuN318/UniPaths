package com.example.unipaths.Activities;

public class PastPersonality {
    private String personalityName;
    private String dateTaken;

    public PastPersonality(String personalityName, String dateTaken, String personalityType) {
        this.personalityName = personalityName;
        this.dateTaken = dateTaken;
        this.personalityType = personalityType;
    }

    private String personalityType;

    public PastPersonality() {
    }

    public PastPersonality(String personalityName, String dateTaken) {
        this.personalityName = personalityName;
        this.dateTaken = dateTaken;
    }

    public String getPersonalityName() {
        return personalityName;
    }

    public void setPersonalityName(String personalityName) {
        this.personalityName = personalityName;
    }

    public String getDateTaken() {
        return dateTaken;
    }

    public void setDateTaken(String dateTaken) {
        this.dateTaken = dateTaken;
    }

    public String getPersonalityType() {
        return personalityType;
    }

    public void setPersonalityType(String personalityType) {
        this.personalityType = personalityType;
    }
}
