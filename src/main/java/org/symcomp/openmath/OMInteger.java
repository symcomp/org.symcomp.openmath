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

import java.math.BigInteger;
import java.util.Map;
import java.io.PrintStream;

/**
 * Representing the OpenMath integer node <tt>&lt;OMI&gt;</tt> 
 */
public class OMInteger extends OpenMathBase {
	//=== Attributes ===
	private BigInteger bigIntValue = null;
	private String strValue = null;
	
    /**
     * construct <tt>&lt;OMI&gt value /&lt;/OMI&gt;</tt>
     */
	public OMInteger(BigInteger value) {
		this.bigIntValue = value;
	}
	
    /**
     * construct <tt>&lt;OMI&gt value /&lt;/OMI&gt;</tt>
     */
	public OMInteger(int value) {
		this.bigIntValue = new BigInteger(((Integer)value).toString());
	}

    /**
     * construct <tt>&lt;OMI&gt value /&lt;/OMI&gt;</tt>
     */
	public OMInteger(long value) {
		this.bigIntValue = new BigInteger(((Long)value).toString());
	}

    /**
     * construct <tt>&lt;OMI&gt value /&lt;/OMI&gt;</tt>
     */
	public OMInteger(String value) {
		this.strValue = value;
	}
	
	/**
	 * @deprecated  As of release 1.1, use {@link #getIntValue()} or {@link #getStrValue()} (hex support was broken before)
	 */
	@Deprecated public BigInteger getValue() {
		return getIntValue();
	}
	
	/**
	 * Get value of OMInteger as BigInt
	 */
    public BigInteger getIntValue() {
		if (bigIntValue == null) {
			assert strValue != null;
			if (strValue.startsWith("x") || strValue.startsWith("-x")) {
				//hex
				try {
					bigIntValue = fromBase16(strValue);
				} catch (Exception e) {
					//Decoding failed, apparently.
					throw new RuntimeException("Could not convert hexadecimal integer to dec: " + e.getMessage());
				}
			} else {
				//assume decimal
				bigIntValue = new BigInteger(strValue);
			}
		}
        return bigIntValue;
    }

	/**
	 * Get value of OMInteger as String. The return value depends on how the
	 *   OMInteger was constructed: If it was constructed by a String, that is
	 *   returned; if it was constructed by a BigInt, a decimal representation
	 *   of that integer is returned. 
	 */
    public String getStrValue() {
		if (strValue == null) {
			assert bigIntValue != null;
			strValue = bigIntValue.toString();
		}
        return strValue;
    }

	/**
	 * Get value of OMInteger as decimal String. 
	 */
    public String getStrValueDec() {
		return getIntValue().toString();
    }

	/**
	 * Get value of OMInteger as hex String (returned value always starts with "x")
	 */
    public String getStrValueHex() {
		return toBase16(getIntValue());
    }

    public void setValue(BigInteger value) {
        this.bigIntValue = value;
		this.strValue = null;
    }
    public void setValue(String value) {
        this.strValue = value;
		this.bigIntValue = null;
    }

    //=== Methods ===
 	public boolean equals(Object that) {
        if (that.getClass() != OMInteger.class) return false;
 		if(!this.sameAttributes((OMInteger)that)) return false;
 		return this.getStrValue().equals(((OMInteger) that).getStrValue());
 	}

	//=== Code for Hex conversions ===
	//Set b[j] to be the byte corresponding to base-16 chars at c[i] and c[i+1]
	private static void fromBase16(char[] c, int i, byte[] b, int j) {
		if (!( ( (c[i] >= '0' && c[i] <= '9') || (c[i] >= 'A' && c[i] <= 'F') )
		&& ( (c[i+1] >= '0' && c[i+1] <= '9') || (c[i+1] >= 'A' && c[i+1] <= 'F') ) )) {
			throw new RuntimeException("Invalid: '" + c[i] + c[i+1] + "' at position " + i);
		}
		
		b[j] = 0;
		b[j] += 16*( c[i] >= 'A' ? c[i]+10-'A' : c[i] - '0' );
		b[j] += ( c[i+1] >= 'A' ? c[i+1]+10-'A' : c[i+1] - '0' );
	}
	
	//Append base-16 repr. of b to r
	private static void toBase16(byte b, StringBuffer r) {
		r.append( ( b/16 ) < 10 ? (char) ('0' + (b/16)) : (char) ('A' + ((b/16) -10)) );
		r.append( ( b%16 ) < 10 ? (char) ('0' + (b%16)) : (char) ('A' + ((b%16) -10)) );		 
	}

	//Convert x (an integer in base 16) to a big integer
	public static BigInteger fromBase16(String x) {
		byte[] b; int sign;
		if (x.startsWith("x"))       { sign = 1; }
		else if (x.startsWith("-x")) { sign = -1; }
		else {
			throw new RuntimeException("Invalid hexadecimal integer: '" + x + "'");
		}

		char[] c = x.toCharArray();
		int startat = (sign == 1 ? 1 : 2);
		if ( ((c.length - startat) % 2) != 0 ) {
			throw new RuntimeException("Invalid hexadecimal integer: '" + x + "'");
		}
		
		b = new byte[(c.length-startat) / 2];
		int i = startat; int j = 0;
		while(i < c.length) {
			fromBase16(c, i, b, j);
			j +=1; i += 2;
		}

		BigInteger r = new BigInteger(b);
		if (sign == -1) r = r.multiply(BigInteger.valueOf(-1));
		return r;
	}
	
	//Convert x to a string containing a base-16 representation of x.
	public static String toBase16(BigInteger x) {
		byte[] b; StringBuffer r;
		if (x.signum() == -1) {
			b = x.multiply(BigInteger.valueOf(-1)).toByteArray();
			r = new StringBuffer("-x");
		} else {
			b = x.toByteArray();
			r = new StringBuffer("x");
		}

		for(int i = 0; i < b.length; ++i) {
			toBase16(b[i], r);
		}

		return r.toString();
	}
} 