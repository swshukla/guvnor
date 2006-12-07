package org.drools.brms.client.modeldriven.model;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.user.client.rpc.IsSerializable;

public class RuleModel implements IsSerializable {

    public IPattern[] lhs;
    public IAction[] rhs;
    
    /**
     * This will return the fact pattern that a variable is bound to. 
     * 
     * @param var The bound fact variable (NOT bound field).
     * @return null or the FactPattern found. 
     */
    public FactPattern getBoundFact(String var) {
        if (lhs == null ) return null;
        for ( int i = 0; i < lhs.length; i++ ) {
            
            if (lhs[i] instanceof FactPattern) {
                FactPattern p = (FactPattern) lhs[i];
                if (p.boundName != null && var.equals( p.boundName)) {
                    return p;
                }
            }
        }
        return null;
    }
    
    /**
     * @return A list of bound facts (String). Or empty list if none are found.
     */
    public List getBoundFacts() {
        if (lhs == null) return null;
        List list = new ArrayList();
        for ( int i = 0; i < lhs.length; i++ ) {
            if (lhs[i] instanceof FactPattern) {
                FactPattern p = (FactPattern) lhs[i];
                if (p.boundName != null)  list.add( p.boundName );
            }
        }
        return list;
        
    }

    /**
     * 
     * @param idx Remove this index from the LHS.
     * @param Returns false if it was NOT allowed to remove this item (ie 
     * it is used on the RHS).
     */
    public boolean removeLhsItem(int idx) {
        
        IPattern[] newList = new IPattern[lhs.length - 1];
        int newIdx = 0;
        for ( int i = 0; i < lhs.length; i++ ) {
            
            if (i != idx) {
                newList[newIdx] = lhs[i];
                newIdx++;
            } else {
                if (lhs[i] instanceof FactPattern) {
                    FactPattern p = (FactPattern) lhs[i];
                    if (p.boundName != null && isBoundFactUsed( p.boundName )) {
                        return false;
                    }
                }
                
            }
            
        }
        this.lhs = newList;
        return true;
    }

    /**
     * @param binding The name of the LHS fact binding.
     * @return Returns true if the specified binding is used on the RHS.
     */
    public boolean isBoundFactUsed(String binding) {
        if (rhs == null) return false;
        for ( int i = 0; i < rhs.length; i++ ) {
            if (rhs[i] instanceof ActionSetField) {
                ActionSetField set = (ActionSetField) rhs[i];
                if (set.variable.equals( binding )) {
                    return true;
                }
            } else if (rhs[i] instanceof ActionRetractFact) {
                ActionRetractFact ret = (ActionRetractFact) rhs[i];
                if (ret.variableName.equals( binding )) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public void removeRhsItem(int idx) {
        IAction[] newList = new IAction[rhs.length - 1];
        int newIdx = 0;
        for ( int i = 0; i < rhs.length; i++ ) {
            
            if (i != idx) {
                newList[newIdx] = rhs[i];
                newIdx++;
            }
            
        }
        this.rhs = newList;
    }
    
}
