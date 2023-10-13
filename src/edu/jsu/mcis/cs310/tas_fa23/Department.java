package edu.jsu.mcis.cs310.tas_fa23;

public class Department {

    private final int numericid;
    private final int terminalid;
    private final String description;

    public Department(int numericid, int terminalid, String description) {
        this.numericid = numericid;
        this.terminalid = terminalid;
        this.description = description;
    }

    public int getNumericid() {
        return numericid;
    }

    public int getTerminalid() {
        return terminalid;
    }

    public String geDescription() {
        return description;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        
        sb.append("#").append(numericid).append(" (").append(description).append("), ");
        sb.append("Terminal ID: ").append(terminalid);
        
        return sb.toString();
    }
}
