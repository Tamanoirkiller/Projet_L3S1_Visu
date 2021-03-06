

/**
 * Created by jb on 12/01/17.
 */
public class AlerteData {
    private String type;
    private String inferieurA;
    private String superieurA;

    public AlerteData(String type, String inferieurA, String superieurA)
    {
        this.type = type;
        this.inferieurA = inferieurA;
        this.superieurA = superieurA;
    }

    public String getType() {
        return type;
    }

    public String getInferieurA() {
        return inferieurA;
    }

    public String getSuperieurA() {
        return superieurA;
    }

    public void setType(String type) {
        this.type = type;
    }


    @Override
    public boolean equals(Object data)
    {
        if (!(data instanceof AlerteData))return false;
        AlerteData donnee = (AlerteData) data;
        return (type.equals(donnee.getType()) && inferieurA.equals(donnee.getInferieurA()) && superieurA.equals(donnee.getSuperieurA()));
    }
}
