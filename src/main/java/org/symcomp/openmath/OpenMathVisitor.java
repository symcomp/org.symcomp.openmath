package org.symcomp.openmath;

import java.io.Writer;
import java.io.IOException;

public abstract class OpenMathVisitor {

    public OpenMathBase visit(OpenMathBase om) {
        Class c = om.getClass();
        if (c.equals(OMObject.class)) {
                return visit((OMObject) om);
        } else if (c.equals(OMInteger.class)) {
                return visit((OMInteger) om);
        } else if (c.equals(OMVariable.class)) {
                return visit((OMVariable) om);
        } else if (c.equals(OMFloat.class)) {
                return visit((OMFloat) om);
        } else if (c.equals(OMSymbol.class)) {
                return visit((OMSymbol) om);
        } else if (c.equals(OMReference.class)) {
                return visit((OMReference) om);
        } else if (c.equals(OMString.class)) {
                return visit((OMString) om);
        } else if (c.equals(OMBind.class)) {
                return visit((OMBind) om);
        } else if (c.equals(OMApply.class)) {
                return visit((OMApply) om);
        } else if (c.equals(OMError.class)) {
                return visit((OMError) om);
        }
        return null;
    }

    public OpenMathBase visit(OMApply om) { return om; }
    public OpenMathBase visit(OMBinary om) { return om; }
    public OpenMathBase visit(OMBind om) { return om; }
    public OpenMathBase visit(OMError om) { return om; }
    public OpenMathBase visit(OMFloat om) { return om; }
    public OpenMathBase visit(OMForeign om) { return om; }
    public OpenMathBase visit(OMInteger om) { return om; }
    public OpenMathBase visit(OMObject om) { return om; }
    public OpenMathBase visit(OMReference om) { return om; }
    public OpenMathBase visit(OMString om) { return om; }
    public OpenMathBase visit(OMSymbol om) { return om; }
    public OpenMathBase visit(OMVariable om) { return om; }
    
}
