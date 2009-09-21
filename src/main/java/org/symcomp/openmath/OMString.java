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
import java.io.PrintStream;

/**
 * Representing the OpenMath string node <tt>&lt;OMSTR&gt;</tt> 
 */
public class OMString extends OpenMathBase {
	
	//=== Attributes ===
	private String value;

    /**
     * construct <tt>&lt;OMSTR&gt value /&lt;/OMSTR&gt;</tt>
     */
	public OMString(String value) {
		this.value = value;
	}
	
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    //=== Methods ===
 	
 	public boolean equals(Object that) {
        if (that.getClass() != OMString.class) return false;
        OMString s = (OMString) that;
        return this.sameAttributes(s) && this.value.equals(s.getValue());
     }

}
	
