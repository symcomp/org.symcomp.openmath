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
import java.util.Arrays;
import java.io.PrintStream;

/**
 * Representing the OpenMath binary node <tt>&lt;OMB&gt;</tt><br/>
 */
public class OMBinary extends OpenMathBase {

	//=== Attributes ===
	byte[] byteValue = null;
	String base64Value = null;
	

	//=== Constructors ===
    /**
     * construct <tt>&lt;OMB&gt; value &lt;/OMB&gt;</tt>
	 *@param val The value of the OMBinary as byte array
     */
	public OMBinary(byte[] value) {
		this.byteValue = value;
	}

    /**
     * construct <tt>&lt;OMB&gt; value &lt;/OMB&gt;</tt>
	 *@param val The value of the OMBinary as Base64 encoded string
     */
	public OMBinary(String val) {
		this.base64Value = val;
	}

	/**
	 * @deprecated  As of release 1.2, use {@link #getBase64Value()} or {@link #getByteValue()} 
	 */
    public byte[] getValue() {
        return byteValue;
    }

	/**
	 * Get the value of this OMBinary as byte array
	 */
    public byte[] getByteValue() {
		if (byteValue == null) {
			try {
				byteValue = Base64.decode( base64Value );
			} catch (java.io.IOException e) {
				throw new RuntimeException("Could not decode base64 encoding of OMBinary: " + e.getMessage());
			}
		}
        return byteValue;
    }

	/**
	 * Get the value of this OMBinary as Base64 encoded string
	 */
    public String getBase64Value() {
		if (base64Value == null) {
			base64Value = Base64.encodeBytes( byteValue );
		}
        return base64Value;
    }

	

    public void setValue(byte[] value) {
        this.byteValue = value;
		this.base64Value = null;
    }

    public void setValue(String value) {
        this.byteValue = null;
		this.base64Value = value;
    }


 	public boolean equals(Object that) {
        if (that.getClass() != OMBinary.class) return false;
 		if(!this.sameAttributes((OMBinary) that)) return false;

		if (this.base64Value != null && ((OMBinary) that).base64Value != null) 
			return this.base64Value.equals(((OMBinary) that).base64Value);
		if (this.byteValue != null && ((OMBinary) that).byteValue != null)
 			return(Arrays.equals(this.byteValue, ((OMBinary) that).getByteValue()));
		return this.getBase64Value().equals(((OMBinary) that).getBase64Value());
 	}

}

