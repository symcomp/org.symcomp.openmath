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
 * Representing the OpenMath symbol node <tt>&lt;OMS ... /&gt;</tt> 
 */
public class OMSymbol extends OpenMathBase {
	
	//=== Attributes ===
    private String name;
    private String cd;

    /**
     * construct <tt>&lt;OMS cd="cd" name="name" /&gt;</tt>
     */
    public OMSymbol(String cd, String name) {
    	this.cd = cd;
    	this.name = name;
	}
	
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCd() {
        return cd;
    }

    public void setCd(String cd) {
        this.cd = cd;
    }

    //=== Methods ===
 	public boolean equals(Object that) {
        if (that.getClass() == String.class) {
            String s = (String) that;
            if (!s.contains(".")) return false;
            String[] l = s.split("\\.");
            if (l.length != 2) return false;
            return (l[0].trim().equals(this.cd.trim()) && l[1].trim().equals(this.name.trim()));
        }
        if (that.getClass() != OMSymbol.class) return false;
        OMSymbol s = (OMSymbol) that;
        return this.sameAttributes(s) && this.name.equals(s.getName()) && this.cd.equals(s.getCd());
    }

	public String fullname() { return cd + "." + name; }
}
