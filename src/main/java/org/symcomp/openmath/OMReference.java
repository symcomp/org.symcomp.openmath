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
 * Representing the OpenMath reference node <tt>&lt;OMR ... /&gt;</tt> 
 */
public class OMReference extends OpenMathBase {
	
	//=== Attributes ===
    private String href;

    /**
     * construct <tt>&lt;OMR href="href" /&gt;</tt>
     */
	public OMReference(String href) {
		this.href = href;
	}

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    //=== Methods ===
	
 	public boolean equals(Object that) {
        if(that.getClass() != OMReference.class) return false;
        OMReference r = (OMReference) that;
 		if(!this.sameAttributes(r)) return false;
		return (this.href.equals(r.getHref()));
 	}
} 
