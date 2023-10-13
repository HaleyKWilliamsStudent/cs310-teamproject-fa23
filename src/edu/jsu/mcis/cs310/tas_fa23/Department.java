
package edu.jsu.mcis.cs310.tas_fa23;

public class Department {
    private final int numericid;
    private final int terminalid;
    private final char description;
    
    public Department(int numericid, int terminalid, char description){
    this.numericid = numericid;
    this.terminalid = terminalid;
    this.description = description;
    }
    
    public int getNumericid(){
    return numericid;
    }

public int getTerminalid(){
    return terminalid;
    }
  

public char geDescription(){
    return description;
    }
@Override
public String toString(){
        String printOriginal = null;
   return printOriginal;
}
}