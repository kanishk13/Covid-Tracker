package com.example.CoronaVirusTracker.models;

public class LocationStats
{
    private String State,Country;
    private String LatestTotal;
    private int diffFromPrevDay;
    private String status;

    public String getLatestTotal() {
        return LatestTotal;
    }

    public int getDiffFromPrevDay() {
        return diffFromPrevDay;
    }

    public void setDiffFromPrevDay(int diffFromPrevDay) {
        this.diffFromPrevDay = diffFromPrevDay;
    }

    public void setLatestTotal(String latestTotal) {
        LatestTotal = latestTotal;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getStatus() {
        if(diffFromPrevDay>=500)
            return "High_Alert";

        else if(diffFromPrevDay>=50)
        return "Alert";

        else if (diffFromPrevDay>=1)
            return "Normal";

        else if(diffFromPrevDay<=0)
            return "Improvement";
    return "Improvement";
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "LocationStats{" +
                "State='" + State + '\'' +
                ", Country='" + Country + '\'' +
                ", LatestTotal='" + LatestTotal + '\'' +
                '}';
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }


}

