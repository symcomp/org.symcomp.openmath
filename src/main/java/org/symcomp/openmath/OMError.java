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
public class OMError extends OpenMathBase {

	//=== Attributes ===
    OpenMathBase head;
    OpenMathBase[] params;

	/**
     * construct <tt>&lt;OMA&gt; head params[0] ... params[<i>n</i>] &lt;/OMA&gt;</tt>
     */
    public OMError(OpenMathBase head, OpenMathBase[] params) {
		this.head = head;
		this.params = params;
	}

    public OpenMathBase getHead() { return head; }

    public void setHead(OpenMathBase head) { this.head = head; }

    public OpenMathBase[] getParams() {
        return params;
    }

    public void setParams(OpenMathBase[] params) {
        this.params = params;
    }


 	//=== Methods ===

    public boolean equals(Object that) {
        if (that.getClass() != OMError.class) return false;
        OMError e = (OMError) that;
 		if(!this.sameAttributes(e)) return false;
        if (!this.head.equals(e.getHead())) return false;
 		Integer ps = this.params.length;
        OpenMathBase[] pp = e.getParams();
        if (pp.length != ps) return false;
        if (ps == 0) return true;
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