package org.drools.brms.client.modeldriven.model;

public class FactPattern implements IPattern {

    public FactPattern() {
        this.constraints = new Constraint[0];
    }
    
    public FactPattern(String factType) {
        this.factType = factType;
        this.constraints = new Constraint[0];
    }
    
    public Constraint[] constraints;
    public String factType;
    public String boundName;
    
    
    public void addConstraint(Constraint constraint) {
        if (constraints == null) {
            constraints = new Constraint[1];            
            constraints[0] = constraint;            
        } else {
            Constraint[] newList = new Constraint[constraints.length + 1];
            for ( int i = 0; i < constraints.length; i++ ) {            
                newList[i] = constraints[i];
            }
            newList[constraints.length] = constraint;
            constraints = newList;
        }
    }

    public void removeConstraint(int idx) {
        //Unfortunately, this is kinda duplicate code with other methods, 
        //but with typed arrays, and GWT, its not really possible to do anything "better" 
        //at this point in time. 
        Constraint[] newList = new Constraint[constraints.length - 1];
        int newIdx = 0;
        for ( int i = 0; i < constraints.length; i++ ) {
            
            if (i != idx) {
                newList[newIdx] = constraints[i];
                newIdx++;
            }
            
        }
        this.constraints = newList;        
        
    }
    
    
    
    
}
