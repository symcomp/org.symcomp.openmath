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
 * Representing the OpenMath object node <tt>&lt;OMOBJ&gt;</tt>
 */
public class OMObject extends OpenMathBase {

	//=== Attributes ===
	private OpenMathBase element;

    /**
     * construct <tt>&lt;OMOBJ&gt element /&lt;/OMOBJ&gt;</tt>
     */
	public OMObject(OpenMathBase element) {
		this.element = element;
	}

    public OpenMathBase getElement() {
        return element;
    }

    public void setElement(OpenMathBase element) {
        this.element = element;
    }

    //=== Methods ===

    public boolean equals(Object that) {
 		if(that.getClass() != OMObject.class) return false;
 	   	return (this.element.equals(((OMObject) that).getElement()));
 	}

    // traversing

    public void traverse(OpenMathVisitor visitor) {
        element = visitor.visit(element);
        element.traverse(visitor);
    }    

}
