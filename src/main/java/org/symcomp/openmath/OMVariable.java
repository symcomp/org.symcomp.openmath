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
 * Representing the OpenMath variable node <tt>&lt;OMV ... /&gt;</tt> 
 */
public class OMVariable extends OpenMathBase {
	
	//=== Attributes ===
	private String name;

    /**
     * construct <tt>&lt;OMV name="name" /&gt;</tt>
     */
	public OMVariable(String name) {
		this.name = name;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
	
	//=== Methods ===
 	public boolean equals(Object that) {
        if (that.getClass() != OMVariable.class) return false;
        OMVariable v = (OMVariable) that;
        return this.sameAttributes(v) && (this.name.equals(v.getName()));
    }
} 
