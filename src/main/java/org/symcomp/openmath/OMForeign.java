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

import org.symcomp.openmath.*;

import java.io.PrintStream;

/**
 * Representing the OpenMath foreign node <tt>&lt;OMFOREIGN&gt;</tt><br/>
 */
public class OMForeign extends OpenMathBase {

	//=== Attributes ===
    String content;
    String encoding;

    /**
     * doesn't construct anything so far ;)
     */
    public OMForeign(String content) {
        super();
        this.content = content;
        this.encoding = null;
    }

    public OMForeign(String content, String encoding) {
        super();
        this.content = content;
        this.encoding = encoding;
    }

    //=== Methods ===

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean equals(Object that) {
        if (that.getClass() != OMForeign.class) return false;
        OMForeign s = (OMForeign) that;
		if (!(this.sameAttributes(s))) return false;
		if (this.encoding == null && s.encoding != null) return false;
		if (this.encoding != null && !(this.encoding.equals(s.encoding))) return false;
		return this.content.equals(s.content);
 	}
} 
	
