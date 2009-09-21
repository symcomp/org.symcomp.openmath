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
 * Representing the OpenMath apply node <tt>&lt;OMA&gt;</tt>
 */
public class OMApply extends OpenMathBase {

	//=== Attributes ===
    OpenMathBase head;
    OpenMathBase[] params;

    public OMApply() { }

	/**
     * construct <tt>&lt;OMA&gt; head params[0] ... params[<i>n</i>] &lt;/OMA&gt;</tt>
     */
    public OMApply(OpenMathBase head, OpenMathBase[] params) {
		this.head = head;
		this.params = params;
	}

    public OMApply(String cd, String name, OpenMathBase ... params) {
        this.head = new OMSymbol(cd, name);
        this.params = params;
    }

    public OpenMathBase getHead() { return head; }

    public void setHead(OpenMathBase head) { this.head = head; }

    public OpenMathBase[] getParams() {
        return params;
    }

    public int getParamsLength() {
        return params.length;
    }

    public OpenMathBase getParam(int n) {
        return params[n];
    }

    public void setParams(OpenMathBase[] params) {
        this.params = params;
    }

    public boolean equals(Object that) {
        if (that.getClass() != OMApply.class) return false;
 		if(!this.sameAttributes((OMApply) that)) return false;
        if (!this.head.equals(((OMApply)that).getHead())) return false;
 		Integer ps = this.params.length;
        OpenMathBase[] pp = ((OMApply) that).getParams();
        if (pp.length != ps) return false;
        for (int i=0; i<ps; i++) {
            if (!this.params[i].equals(pp[i]))
                return false;
        }
 		return true;
 	}

    // traversing

    public void traverse(OpenMathVisitor visitor) {
        head = visitor.visit(head);
        head.traverse(visitor);
        for (int i = 0; i<params.length; i++) {
            params[i] = visitor.visit(params[i]);
            params[i].traverse(visitor);
        }
    }
    
}