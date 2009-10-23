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

/**
 * Representing the OpenMath flot node <tt>&lt;OMF ... /&gt;</tt>
 */
case class OMFloat(dec:Double) extends OpenMathBase {

    def this(bts:Array[Char]) = this(OMFloat.bytes2double(bts))
    def this(hex:String) = this(hex.toArray[Char])

    def getHex():String = new String(OMFloat.double2bytes(dec))
    def getDec():java.lang.Double = new java.lang.Double(dec)
    def getBytes():Array[Char] = OMFloat.double2bytes(dec)
    
 	//=== Methods ===
 	override def equals(that:Any):Boolean = {
        if (!that.isInstanceOf[OMFloat]) return false;
        val f = that.asInstanceOf[OMFloat];
 		if(!this.sameAttributes(f)) return false;
        this.dec == f.dec
 	}

}


object OMFloat {
    /**
     * Converts an IEEE bytes-representation where the first byte
     * is most significant into a double. This is needed to deal with
     * the binary encoding.
     * We in fact use char instead of byte, since that's what you get out of readers.
     * @param b the bytes in question
     * @return the double
     */
    def bytes2double(b:Array[Char]):Double = {
        var accum:Long = 0;
        for (i <- 0 to 7) {
            accum |= (( b(7-i) & 0xff ).asInstanceOf[Long] << 8*i);
        }
        java.lang.Double.longBitsToDouble(accum);
    }

    /**
     * Converts a double into the IEEE bytes-representation where the first byte
     * is most significant. This is needed to deal with the binary encoding.
     * We in fact use char instead of byte, since that's what you get out of readers.
     * @param d the double in question
     * @return the bytes-array
     */
    def double2bytes(d:Double):Array[Char] = {
        val l:Long = java.lang.Double.doubleToRawLongBits(d);
        Array(
            ((l>>56) & 0xff).asInstanceOf[Char],
            ((l>>48) & 0xff).asInstanceOf[Char],
            ((l>>40) & 0xff).asInstanceOf[Char],
            ((l>>32) & 0xff).asInstanceOf[Char],
            ((l>>24) & 0xff).asInstanceOf[Char],
            ((l>>16) & 0xff).asInstanceOf[Char],
            ((l>>8) & 0xff).asInstanceOf[Char],
            (l & 0xff).asInstanceOf[Char]
        )
    }
}