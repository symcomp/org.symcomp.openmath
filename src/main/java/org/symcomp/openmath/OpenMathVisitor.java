//---------------------------------------------------------------------------
//  Copyright 2006-2009
//    Dan Roozemond, d.a.roozemond@tue.nl, (TU Eindhoven, Netherlands)
//    Peter Horn, horn@math.uni-kassel.de (University Kassel, Germany)
//
//  Licensed under the Apache License, Version 2.0 (the "License");
//  you may not use this file except in compliance with the License.
//  You may obtain a copy of the License at
//
//      http://www.apache.org/licenses/LICENSE-2.0
//
//  Unless required by applicable law or agreed to in writing, software
//  distributed under the License is distributed on an "AS IS" BASIS,
//  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//  See the License for the specific language governing permissions and
//  limitations under the License.
//---------------------------------------------------------------------------

package org.symcomp.openmath;

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
        } else if (OMContainer.class.isInstance(om)) {
                return visit(((OMContainer) om).toOpenMath());
        }
        throw new RuntimeException("This should never happen");
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
