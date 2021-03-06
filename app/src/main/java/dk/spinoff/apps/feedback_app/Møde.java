package dk.spinoff.apps.feedback_app;

public class Møde {

    private String navn;
    private String formål;
    private String dato;
    private String startTid;
    private String slutTid;
    private String sted;
    private boolean afholdt;
    private String mødeholderID;
    private String mødeID;
    private String mødeIDtildeltager;
    private boolean igang;
    private String dagsorden;
    private String FaktiskStartTid;
    private String FaktiskSlutTid;

    public Møde(){
    }

    public Møde(String navn, String formål, String dato, String startTid, String slutTid, String sted, String mødeholderID, boolean afholdt) {
        this.navn = navn;
        this.formål = formål;
        this.dato = dato;
        this.startTid = startTid;
        this.slutTid = slutTid;
        this.sted = sted;
        this.mødeholderID = mødeholderID;
        this.afholdt = afholdt;
    }



    public void setFaktiskStartTid(String faktiskStartTid) {
        FaktiskStartTid = faktiskStartTid;
    }

    public String getFaktiskStartTid() {
        return FaktiskStartTid;
    }

    public void setFaktiskSlutTid(String faktiskSlutTid) {FaktiskSlutTid = faktiskSlutTid;}

    public String getFaktiskSlutTid() {return FaktiskSlutTid;}


    public void setNavn(String navn) {
        this.navn = navn;
    }

    public String getNavn() {
        return navn;
    }


    public void setFormål(String formål) {
        this.formål = formål;
    }

    public String getFormål() {
        return formål;
    }


    public void setDato(String dato) {
        this.dato = dato;
    }

    public String getDato() {
        return dato;
    }


    public int getDatoDag(){
        return Integer.parseInt(dato.split("-")[0]);
    }

    public int getDatoMåned() {
        return Integer.parseInt(dato.split("-")[1]);
    }

    public int getDatoÅr(){
        return Integer.parseInt(dato.split("-")[2]);
    }


    public void setSted(String sted) {
        this.sted = sted;
    }

    public String getSted() {
        return sted;
    }


    public void setStartTid(String startTid) {
        this.startTid = startTid;
    }

    public String getStartTid() {
        return startTid;
    }


    public void setSlutTid(String slutTid) {
        this.slutTid = slutTid;
    }

    public String getSlutTid() {
        return slutTid;
    }


    public void setAfholdt(boolean afholdt) {
        this.afholdt = afholdt;
    }

    public boolean isAfholdt() {
        return afholdt;
    }


    public String getMødeholderID() {
        return mødeholderID;
    }


    public void setIgang(boolean igang) {
        this.igang = igang;
    }
    public boolean getIgang(){
        return igang;
    }


    public void setMødeholderID(String mødeholderID) {
        this.mødeholderID = mødeholderID;
    }


    public void setMødeIDtildeltager(String mødeIDtildeltager) {
        this.mødeIDtildeltager = mødeIDtildeltager;
    }

    public String getMødeIDtildeltager() {
        return mødeIDtildeltager;
    }


    public void setMødeID(String mødeID) {
        this.mødeID = mødeID;
    }

    public String getMødeID() {
        return mødeID;
    }



}