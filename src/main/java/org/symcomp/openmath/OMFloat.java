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
 * Representing the OpenMath flot node <tt>&lt;OMF ... /&gt;</tt> 
 */
public class OMFloat extends OpenMathBase {
		
	//=== Attributes ===
    private String hex;
    private Double dec;
	protected String getXmlName() { return "OMF"; }

	
    /**
     * construct <tt>&lt;OMF dec="dec' /&gt;</tt>
     */
    public OMFloat(Double dec) {
    	this.dec = dec;
    	this.hex = null;
    }
    		
    /**
     * construct <tt>&lt;OMF hex="hex' /&gt;</tt>
     */
	public OMFloat(String hex) {
		this.dec = null;
	    this.hex = hex;
	}

    /**
     * construct <tt>&lt;OMF dec="dec' /&gt;</tt>
     */
    public OMFloat(char[] byt) {
    	this.dec = bytes2double(byt);
    	this.hex = null;
    }


    public String getHex() {
        return hex;
    }

    public void setHex(String hex) {
        this.hex = hex;
    }

    public Double getDec() {
        return dec;
    }

    public void setDec(Double dec) {
        this.dec = dec;
    }
 	
 	//=== Methods ===
 	public boolean equals(Object that) {
        if (that.getClass() != OMFloat.class) return false;
        OMFloat f = (OMFloat) that;
 		if(!this.sameAttributes(f)) return false;
		if (null != this.dec && !this.dec.equals(f.getDec())) return false;
		if (null != this.hex && !this.hex.equals(f.getHex())) return false;
		return true;
 	}

    /**
     * Converts an IEEE bytes-representation where the first byte
     * is most significant into a double. This is needed to deal with
     * the binary encoding.
     * We in fact use char instead of byte, since that's what you get out of readers.
     * @param b the bytes in question
     * @return the double
     */
    public static double bytes2double(char[] b) {
        long accum = 0;
        for (int i = 0; i < 8; i++) {
            accum |= ( (long)( b[7-i] & 0xff ) ) << 8*i;
        }
        return Double.longBitsToDouble(accum);
    }

    /**
     * Converts a double into the IEEE bytes-representation where the first byte
     * is most significant. This is needed to deal with the binary encoding.
     * We in fact use char instead of byte, since that's what you get out of readers.
     * @param d the double in question
     * @return the bytes-array
     */
    public static char[] double2bytes(double d) {
        long l = Double.doubleToRawLongBits(d);
        return new char[] {
                (char) ((l>>56) & 0xff),
                (char) ((l>>48) & 0xff)  ,
                (char) ((l>>40) & 0xff)  ,
                (char) ((l>>32) & 0xff)  ,
                (char) ((l>>24) & 0xff)  ,
                (char) ((l>>16) & 0xff)  ,
                (char) ((l>>8) & 0xff)  ,
                (char) (l & 0xff)
        };
    }

}