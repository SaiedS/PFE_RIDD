package com.example.demo_drive_4;

public class TrajetData {


    String ConsoMoyenne100km,DistanceTot,Prix1km,PrixTrajet,dureeTrajet,fuelDebut, fuelFin;
    String EmailUser,dateTrajet,TelId, trajetId,score;


    public TrajetData() {
    }
    public TrajetData( String ConsoMoyenne100km, String DistanceTot, String  Prix1km, String PrixTrajet, String dureeTrajet, String fuelDebut, String fuelFin, String EmailUser, String dateTrajet, String TelId,String trajetId, String score ){

        ConsoMoyenne100km=this.ConsoMoyenne100km;
        DistanceTot=this.DistanceTot;
        Prix1km=this.Prix1km;
        PrixTrajet=this.PrixTrajet;
        dureeTrajet=this.dureeTrajet;
        fuelDebut=this.fuelDebut;
        fuelFin=this.fuelFin;
        EmailUser=this.EmailUser;
        dateTrajet=this.dateTrajet;
        TelId=this.TelId;
        trajetId=this.trajetId;
        score=this.score;
    }

    public String getConsoMoyenne100km() {
        return ConsoMoyenne100km;
    }

    public void setConsoMoyenne100km(String consoMoyenne100km) {
        ConsoMoyenne100km = consoMoyenne100km;
    }

    public String getDistanceTot() {
        return DistanceTot;
    }

    public void setDistanceTot(String distanceTot) {
        DistanceTot = distanceTot;
    }

    public String getPrix1km() {
        return Prix1km;
    }

    public void setPrix1km(String prix1km) {
        Prix1km = prix1km;
    }

    public String getPrixTrajet() {
        return PrixTrajet;
    }

    public void setPrixTrajet(String prixTrajet) {
        PrixTrajet = prixTrajet;
    }

    public String getDureeTrajet() {
        return dureeTrajet;
    }

    public void setDureeTrajet(String dureeTrajet) {
        this.dureeTrajet = dureeTrajet;
    }

    public String getFuelDebut() {
        return fuelDebut;
    }

    public void setFuelDebut(String fuelDebut) {
        this.fuelDebut = fuelDebut;
    }

    public String getFuelFin() {
        return fuelFin;
    }

    public void setFuelFin(String fuelFin) {
        this.fuelFin = fuelFin;
    }

    public String getEmailUser() {
        return EmailUser;
    }

    public void setEmailUser(String emailUser) {
        EmailUser = emailUser;
    }

    public String getDateTrajet() {
        return dateTrajet;
    }

    public void setDateTrajet(String dateTrajet) {
        this.dateTrajet = dateTrajet;
    }

    public String getTelId() {
        return TelId;
    }

    public void setTelId(String telId) {
        TelId = telId;
    }

    public String getTrajetId() {
        return trajetId;
    }

    public void setTrajetId(String trajetId) {
        this.trajetId = trajetId;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }
}
