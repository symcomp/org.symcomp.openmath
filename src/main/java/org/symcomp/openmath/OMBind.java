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

import java.util.Map;
import java.util.List;
import java.io.PrintStream;

/**
 * Representing the OpenMath bind node <tt>&lt;OMBIND&gt;</tt>, note that there is no
 * class to represent the <tt>&lt;OMBVAR&gt;</tt> node, the semantics for this is
 * integrated into OMBind
 */
public class OMBind extends OpenMathBase {

	//=== Attributes ===
	OMVariable[] bvars;
	OMSymbol symbol;
	OpenMathBase param;

    /**
     * construct
     * <pre>&lt;OMBIND&gt;
     *   symbol
     *   &lt;OMBVAR&gt; bvars[0] ... bvars[<i>n</i>] &lt;/OMBVAR&gt;
     *   param
     * &lt;/OMBIND&gt;
     */
    public OMBind(OMSymbol symbol, OMVariable[] bvars, OpenMathBase param) {
		this.bvars = bvars;
		this.symbol = symbol;
		this.param = param;
	}

    // Getters and Setters
    public OMVariable[] getBvars() {
        return bvars;
    }

    public void setBvars(OMVariable[] bvars) {
        this.bvars = bvars;
    }

    public OMSymbol getSymbol() {
        return symbol;
    }

    public void setSymbol(OMSymbol symbol) {
        this.symbol = symbol;
    }

    public OpenMathBase getParam() {
        return param;
    }

    public void setParam(OpenMathBase param) {
        this.param = param;
    }

    //=== Methods ===

 	public boolean equals(Object that) {
        if (that.getClass() != OMBind.class) return false;
         OMBind t = (OMBind) that;
 		if(!this.sameAttributes(t)) return false;
 		if (!this.symbol.equals(t.getSymbol()) || !this.param.equals(t.getParam())) return false;
        OMVariable[] omv = t.getBvars();
 		if (this.bvars.length != omv.length) return false;
 		for(int i=0; i<this.bvars.length; i++) {
 			if (!this.bvars[i].equals(omv[i])) return false;
 		}
 		return true;
 	}

    // traversing

    public void traverse(OpenMathVisitor visitor) {
        symbol = (OMSymbol) visitor.visit(symbol);
        symbol.traverse(visitor);
        for (int i = 0; i<bvars.length; i++) {
            bvars[i] = (OMVariable) visitor.visit(bvars[i]);
            bvars[i].traverse(visitor);
        }
        param = visitor.visit(param);
        param.traverse(visitor);
    }


}