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

import org.symcomp.openmath.binary.BinaryRenderer;
import org.symcomp.openmath.binary.BinaryConstants;
import org.symcomp.openmath.binary.BinaryParser;

import java.math.BigInteger;
import java.util.Random;
import java.io.*;
import java.nio.charset.Charset;


public class BinaryEncodingTest implements BinaryConstants {

    //==============================================================================
    // 1 byte integers
    public void testSmallInt() throws Exception {
        OMInteger omi = new OMInteger(12);
        char[] s = BinaryRenderer.render(omi);
        assert s.length == 2;
        assert s[0] == TYPE_INT_SMALL;
        assert s[1] == 12;
        OMInteger omi2 = (OMInteger) BinaryParser.parse(s);
        assert omi.equals(omi2);
    }

    public void testSmallIntNeg() throws Exception {
        OMInteger omi = new OMInteger(-42);
        char[] s = BinaryRenderer.render(omi);
        assert s.length == 2;
        assert s[0] == TYPE_INT_SMALL;
        assert s[1] == 0xd6;
        OMInteger omi2 = (OMInteger) BinaryParser.parse(s);
        assert omi.equals(omi2);
    }

    public void testSmallIntId() throws Exception {
        OMInteger omi = new OMInteger(12);
        omi.setId("Ab");
        char[] s = BinaryRenderer.render(omi);
        assert s.length == 5;
        assert s[0] == (TYPE_INT_SMALL | FLAG_ID);
        assert s[1] == 2;
        assert s[2] == 12;
        assert s[3] == 'A';
        assert s[4] == 'b';
        OMInteger omi2 = (OMInteger) BinaryParser.parse(s);
        assert omi.equals(omi2);
    }


    public void testSmallIntIdNeg() throws Exception {
        OMInteger omi = new OMInteger(-77);
        omi.setId("Ab");
        char[] s = BinaryRenderer.render(omi);
        assert s.length == 5;
        assert s[0] == (TYPE_INT_SMALL | FLAG_ID);
        assert s[1] == 2;
        assert s[2] == 0xb3; // -77;
        assert s[3] == 'A';
        assert s[4] == 'b';
        OMInteger omi2 = (OMInteger) BinaryParser.parse(s);
        assert omi.equals(omi2);
    }

    //==============================================================================
    // 4 byte integers
    public void testMedInt() throws Exception {
        OMInteger omi = new OMInteger(0x3456789a); // 878082202
        char[] s = BinaryRenderer.render(omi);
        assert s.length == 5;
        assert s[0] == (TYPE_INT_SMALL | FLAG_LONG);
        assert s[1] == 0x34;
        assert s[2] == 0x56;
        assert s[3] == 0x78;
        assert s[4] == 0x9a;
        OMInteger omi2 = (OMInteger) BinaryParser.parse(s);
        assert omi.equals(omi2);
    }

    public void testMedIntNeg() throws Exception {
        OMInteger omi = new OMInteger(0xf8765432); // -126462926
        char[] s = BinaryRenderer.render(omi);
        assert s.length == 5;
        assert s[0] == (TYPE_INT_SMALL | FLAG_LONG);
        assert s[1] == 0xf8;
        assert s[2] == 0x76;
        assert s[3] == 0x54;
        assert s[4] == 0x32;
        OMInteger omi2 = (OMInteger) BinaryParser.parse(s);
        assert omi.equals(omi2);
    }

    public void testMedIntId() throws Exception {
        OMInteger omi = new OMInteger(0x3456789a); // 878082202
        omi.setId("Abc");
        char[] s = BinaryRenderer.render(omi);
        assert s.length == 12;
        assert s[0] == (TYPE_INT_SMALL | FLAG_ID | FLAG_LONG);
        assert s[1] == 0;
        assert s[2] == 0;
        assert s[3] == 0;
        assert s[4] == 3;
        assert s[5] == 0x34;
        assert s[6] == 0x56;
        assert s[7] == 0x78;
        assert s[8] == 0x9a;
        assert s[9] == 'A';
        assert s[10] == 'b';
        assert s[11] == 'c';
        OMInteger omi2 = (OMInteger) BinaryParser.parse(s);
        assert omi.equals(omi2);
    }

    public void testMedIntIdNeg() throws Exception {
        OMInteger omi = new OMInteger(0xf456789a); // 878082202
        omi.setId("Abc");
        char[] s = BinaryRenderer.render(omi);
        assert s.length == 12;
        assert s[0] == (TYPE_INT_SMALL | FLAG_ID | FLAG_LONG);
        assert s[1] == 0;
        assert s[2] == 0;
        assert s[3] == 0;
        assert s[4] == 3;
        assert s[5] == 0xf4;
        assert s[6] == 0x56;
        assert s[7] == 0x78;
        assert s[8] == 0x9a;
        assert s[9] == 'A';
        assert s[10] == 'b';
        assert s[11] == 'c';
        OMInteger omi2 = (OMInteger) BinaryParser.parse(s);
        assert omi.equals(omi2);
    }

    //==============================================================================
    // really big integers
    public void testBigInt() throws Exception {
        String ss = "1234567890123456789012345678901212112121";
        OMInteger omi = new OMInteger(ss);
        char[] s = BinaryRenderer.render(omi);
        OMInteger omi2 = (OMInteger) BinaryParser.parse(s);
        assert omi.equals(omi2);
        assert omi2.toPopcorn().equals(ss);
    }

    public void testBigIntNeg() throws Exception {
        String ss = "-1234567890123456789012345678901212112121";
        OMInteger omi = new OMInteger(ss);
        char[] s = BinaryRenderer.render(omi);
        OMInteger omi2 = (OMInteger) BinaryParser.parse(s);
        assert omi.equals(omi2);
        assert omi2.toPopcorn().equals("("+ss+")");
    }

    public void testBigIntId() throws Exception {
        String ss = "1234567890123456789012345678901212112121";
		String id = "Abc";
        OMInteger omi = new OMInteger(ss);
        omi.setId(id);
        char[] s = BinaryRenderer.render(omi);
        OMInteger omi2 = (OMInteger) BinaryParser.parse(s);
        assert omi.equals(omi2);
        assert omi2.toPopcorn().equals(ss + ":" + id);
    }

    public void testBigIntIdNeg() throws Exception {
        String ss = "-1234567890123456789012345678901212112121";
		String id = "Abc";
        OMInteger omi = new OMInteger(ss);
        omi.setId(id);
        char[] s = BinaryRenderer.render(omi);
        OMInteger omi2 = (OMInteger) BinaryParser.parse(s);
        assert omi.equals(omi2);
        assert omi2.toPopcorn().equals("("+ss+"):"+id);
    }

    public void testDefectiveBigInt() throws Exception {
        String ss = "2147483648";
        OMInteger omi = new OMInteger(ss);
        char[] s = BinaryRenderer.render(omi);
        OMInteger omi2 = (OMInteger) BinaryParser.parse(s);
        assert equal(omi, omi2);
    }

    public void testIntLooped() throws Exception {
        BigInteger i, ub = new BigInteger("50000");
        OMInteger omi = null;
        for (i = BigInteger.ZERO; i.compareTo(ub) < 0; i = i.add(BigInteger.ONE)) {
            // first loop: small encodings
            omi = new OMInteger(i);
            assert equal(omi, BinaryParser.parse(BinaryRenderer.render(omi)));
            omi = new OMInteger(i.negate());
            assert equal(omi, BinaryParser.parse(BinaryRenderer.render(omi)));
        }
        ub = new BigInteger("2147493648"); //0x80000000+10000
        for (i = new BigInteger("2147473648"); i.compareTo(ub) < 0; i = i.add(BigInteger.ONE)) {
            // first loop: small encodings
            omi = new OMInteger(i);
            assert equal(omi, BinaryParser.parse(BinaryRenderer.render(omi)));
            omi = new OMInteger(i.negate());
            assert equal(omi, BinaryParser.parse(BinaryRenderer.render(omi)));
        }
    }

    public void testIntRandom() throws Exception {
        BigInteger i;
        OMInteger omi;
        Random rand = new Random();
        for (int j = 1; j < 10000; j++) {
            i = new BigInteger(Math.abs(1+rand.nextInt() % 2345), rand);
            omi = new OMInteger(i);
            assert equal(omi, BinaryParser.parse(BinaryRenderer.render(omi)));
            omi = new OMInteger(i.negate());
            assert equal(omi, BinaryParser.parse(BinaryRenderer.render(omi)));
        }
    }

    //==============================================================================
    // Symbols
    public void testSymbol() throws Exception {
        OMSymbol oms = new OMSymbol("hello", "friend");
        char[] s = BinaryRenderer.render(oms);
        assert s[0] == TYPE_SYMBOL;
        assert s[1] == 5;
        assert s[2] == 6;
        assert s.length == 14;
        assert BinaryParser.parse(s).equals(oms);
    }

    public void testSymbolId() throws Exception {
        OMSymbol oms = new OMSymbol("hello", "friend");
        oms.setId("abc");
        char[] s = BinaryRenderer.render(oms);
        assert s[0] == (TYPE_SYMBOL | FLAG_ID);
        assert s[1] == 5;
        assert s[2] == 6;
        assert s[3] == 3;
        assert s.length == 18;
        assert BinaryParser.parse(s).equals(oms);
    }

    public void testSymbolLong() throws Exception {
        OMSymbol oms = new OMSymbol("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789", "friend");
        char[] s = BinaryRenderer.render(oms);
        assert s[0] == (TYPE_SYMBOL | FLAG_LONG);
        assert s[1] == 0 && s[2] == 0 && s[3] == 0 && s[4] == 130;
        assert s[5] == 0 && s[6] == 0 && s[7] == 0 && s[8] == 6;
        assert s.length == 130 + 6 + 8 + 1;
        assert BinaryParser.parse(s).equals(oms);
    }

    public void testSymbolIdLong() throws Exception {
        OMSymbol oms = new OMSymbol("hello", "friend");
        oms.setId("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        char[] s = BinaryRenderer.render(oms);
        assert s[0] == (TYPE_SYMBOL | FLAG_LONG | FLAG_ID);
        assert s[1] == 0 && s[2] == 0 && s[3] == 0 && s[4] == 5;
        assert s[5] == 0 && s[6] == 0 && s[7] == 0 && s[8] == 6;
        assert s[9] == 0 && s[10] == 0 && s[11] == 0 && s[12] == 130;
        assert s.length == 130 + 5 + 6 + 12 + 1;
        assert BinaryParser.parse(s).equals(oms);
    }


    //==============================================================================
    // Variables
    public void testVar() throws Exception {
        OMVariable omv = new OMVariable("hello");
        char[] s = BinaryRenderer.render(omv);
        assert s[0] == TYPE_VARIABLE;
        assert s[1] == 5;
        assert s.length == 7;
        assert BinaryParser.parse(s).equals(omv);
    }

    public void testVarId() throws Exception {
        OMVariable omv = new OMVariable("hello");
        omv.setId("abc");
        char[] s = BinaryRenderer.render(omv);
        assert s[0] == (TYPE_VARIABLE | FLAG_ID);
        assert s[1] == 5;
        assert s[2] == 3;
        assert s.length == 11;
        assert BinaryParser.parse(s).equals(omv);
    }

    public void testVarLong() throws Exception {
        OMVariable omv = new OMVariable("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        char[] s = BinaryRenderer.render(omv);
        assert s[0] == (TYPE_VARIABLE | FLAG_LONG);
        assert s[1] == 0 && s[2] == 0 && s[3] == 0 && s[4] == 130;
        assert s.length == 130 + 4 + 1;
        assert BinaryParser.parse(s).equals(omv);
    }

    public void testVarIdLong() throws Exception {
        OMVariable omv = new OMVariable("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        omv.setId("hello");
        char[] s = BinaryRenderer.render(omv);
        assert s[0] == (TYPE_VARIABLE | FLAG_ID | FLAG_LONG);
        assert s[1] == 0 && s[2] == 0 && s[3] == 0 && s[4] == 130;
        assert s[5] == 0 && s[6] == 0 && s[7] == 0 && s[8] == 5;
        assert s.length == 130 + 8 + 5 + 1;
        assert BinaryParser.parse(s).equals(omv);
    }

    //==============================================================================
    // Floats
    public void testFloats() throws Exception {
        OMFloat omf = new OMFloat(1.234);
        char[] s = BinaryRenderer.render(omf);
        assert s[0] == (TYPE_FLOAT);
        assert s.length == 9;
        assert BinaryParser.parse(s).equals(omf);
    }

    public void testFloatsId() throws Exception {
        OMFloat omf = new OMFloat(1.234);
        omf.setId("abc");
        char[] s = BinaryRenderer.render(omf);
        assert s[0] == (TYPE_FLOAT | FLAG_ID);
        assert s[1] == 3;
        assert s.length == 9 + 4;
        assert BinaryParser.parse(s).equals(omf);
    }

    public void testFloatsIdLong() throws Exception {
        OMFloat omf = new OMFloat(1.234);
        omf.setId("0123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789012345678901234567890123456789");
        char[] s = BinaryRenderer.render(omf);
        assert s[0] == (TYPE_FLOAT | FLAG_ID | FLAG_LONG);
        assert s[1] == 0 && s[2] == 0 && s[3] == 0 && s[4] == 130;
        assert s.length == 9 + 4 + 130;
        assert BinaryParser.parse(s).equals(omf);
    }

    //==============================================================================
    // Strings
    public void testStrings() throws Exception {
        OMString omstr = new OMString("That's a DEAD parrot!");
        char[] s = BinaryRenderer.render(omstr);
        assert s[0] == (TYPE_STRING_ISO);
        assert s[1] == 21;
        assert s.length == 21 + 2;
        assert BinaryParser.parse(s).equals(omstr);
    }

    public void testStringsId() throws Exception {
        OMString omstr = new OMString("That's a DEAD parrot!");
        omstr.setId("JustSleeping");
        char[] s = BinaryRenderer.render(omstr);
        assert s[0] == (TYPE_STRING_ISO | FLAG_ID);
        assert s[1] == 21;
        assert s[2] == 12;
        assert s.length == 21 + 12 + 3;
        assert BinaryParser.parse(s).equals(omstr);
    }

    public void testStringsIdLong() throws Exception {
        OMString omstr = new OMString("Um...now look...now look, mate, I've definitely 'ad enough of this. That parrot is definitely deceased, and when I purchased it not 'alf an hour ago, you assured me that its total lack of movement was due to it bein' tired and shagged out following a prolonged squawk.");
        omstr.setId("JustSleeping");
        char[] s = BinaryRenderer.render(omstr);
        assert s[0] == (TYPE_STRING_ISO | FLAG_ID | FLAG_LONG );
        assert s[1] == 0 && s[2] == 0 && s[3] == 1 && s[4] == 12;
        assert s[5] == 0 && s[6] == 0 && s[7] == 0 && s[8] == 12;
        assert s.length == 268 + 12 + 9;
        assert BinaryParser.parse(s).equals(omstr);
    }

    //==============================================================================
    // Application
    public void testApplication() throws Exception {
        OMSymbol oms = new OMSymbol("whats", "that");
        OMString omstr = new OMString("That's a DEAD parrot!");
        OMApply oma = oms.apply(new OMString[]{omstr});
        char[] s = BinaryRenderer.render(oma);
        assert s[0] == (TYPE_APPLICATION);
        assert s[1] == TYPE_SYMBOL;
        assert s[13] == TYPE_STRING_ISO;
        assert s[36] == TYPE_APPLICATION_END;
        assert s.length == 37;
        assert BinaryParser.parse(s).equals(oma);
    }

    public void testApplication2() throws Exception {
        OMSymbol oms = new OMSymbol("whats", "that");
        OMString omstr = new OMString("That's a DEAD parrot!");
        OMInteger omi = new OMInteger(412);
        OMApply oma = oms.apply(new OpenMathBase[]{omstr, omi});
        char[] s = BinaryRenderer.render(oma);
        assert s[0] == (TYPE_APPLICATION);
        assert s[1] == TYPE_SYMBOL;
        assert s[13] == TYPE_STRING_ISO;
        assert s[36] == (TYPE_INT_SMALL | FLAG_LONG);
        assert s[41] == TYPE_APPLICATION_END;
        assert s.length == 42;
        assert BinaryParser.parse(s).equals(oma);
    }

    public void testApplicationId() throws Exception {
        OMSymbol oms = new OMSymbol("whats", "that");
        OMString omstr = new OMString("That's a DEAD parrot!");
        OMApply oma = oms.apply(new OMString[]{omstr});
        oma.setId("abc");
        char[] s = BinaryRenderer.render(oma);
        assert s[0] == (TYPE_APPLICATION | FLAG_ID);
        assert s[1] == 3;
        assert s[5] == TYPE_SYMBOL;
        assert s[17] == TYPE_STRING_ISO;
        assert s[40] == TYPE_APPLICATION_END;
        assert s.length == 41;
        assert BinaryParser.parse(s).equals(oma);
    }

    //==============================================================================
    // Error
    public void testError() throws Exception {
        OMSymbol oms = new OMSymbol("whats", "that");
        OMString omstr = new OMString("That's a DEAD parrot!");
        OMError ome = new OMError(oms, new OMString[]{omstr});
        char[] s = BinaryRenderer.render(ome);
        assert s[0] == (TYPE_ERROR);
        assert s[1] == TYPE_SYMBOL;
        assert s[13] == TYPE_STRING_ISO;
        assert s[36] == TYPE_ERROR_END;
        assert s.length == 37;
        assert BinaryParser.parse(s).equals(ome);
    }

    public void testError2() throws Exception {
        OMSymbol oms = new OMSymbol("whats", "that");
        OMString omstr = new OMString("That's a DEAD parrot!");
        OMInteger omi = new OMInteger(412);
        OMError ome = new OMError(oms, new OpenMathBase[]{omstr, omi});
        char[] s = BinaryRenderer.render(ome);
        assert s[0] == (TYPE_ERROR);
        assert s[1] == TYPE_SYMBOL;
        assert s[13] == TYPE_STRING_ISO;
        assert s[36] == (TYPE_INT_SMALL | FLAG_LONG);
        assert s[41] == TYPE_ERROR_END;
        assert s.length == 42;
        assert BinaryParser.parse(s).equals(ome);
    }

    public void testErrorId() throws Exception {
        OMSymbol oms = new OMSymbol("whats", "that");
        OMString omstr = new OMString("That's a DEAD parrot!");
        OMError ome = new OMError(oms, new OMString[]{omstr});
        ome.setId("abc");
        char[] s = BinaryRenderer.render(ome);
        assert s[0] == (TYPE_ERROR | FLAG_ID);
        assert s[1] == 3;
        assert s[5] == TYPE_SYMBOL;
        assert s[17] == TYPE_STRING_ISO;
        assert s[40] == TYPE_ERROR_END;
        assert s.length == 41;
        assert BinaryParser.parse(s).equals(ome);
    }

    //==============================================================================
    // Binding
    public void testBinding() throws Exception {
        OMSymbol oms = new OMSymbol("whats", "that");
        OMString param = new OMString("That's a DEAD parrot!");
        OMVariable[] vars = { new OMVariable("just"), new OMVariable("sleeping") };
        OMBind ombind = oms.bind(vars, param);
        char[] s = BinaryRenderer.render(ombind);
        assert s[0] == TYPE_BINDING;
        assert s[1] == TYPE_SYMBOL;
        assert s[13] == TYPE_BVARS;
        assert s[30] == TYPE_BVARS_END;
        assert s[31] == TYPE_STRING_ISO;
        assert s.length == 55;
        assert s[54] == TYPE_BINDING_END;
        assert BinaryParser.parse(s).equals(ombind);
    }

    public void testBindingId() throws Exception {
        OMSymbol oms = new OMSymbol("whats", "that");
        OMString param = new OMString("That's a DEAD parrot!");
        OMVariable[] vars = { new OMVariable("just"), new OMVariable("sleeping") };
        OMBind ombind = oms.bind(vars, param);
        ombind.setId("ei_die");
        char[] s = BinaryRenderer.render(ombind);
        assert s[0] == (TYPE_BINDING | FLAG_ID);
        assert s[1] == 6;
        assert s[8] == TYPE_SYMBOL;
        assert s[20] == TYPE_BVARS;
        assert s[37] == TYPE_BVARS_END;
        assert s[38] == TYPE_STRING_ISO;
        assert s.length == 62;
        assert s[61] == TYPE_BINDING_END;
        OMBind bb = (OMBind) BinaryParser.parse(s);
        assert bb.equals(ombind);
        assert bb.getId().equals("ei_die");
    }

    //==============================================================================
    // Attribution
    public void testAttributed() throws Exception {
        OMInteger omi = new OMInteger(327);
        OMSymbol oms = new OMSymbol("this", "that");
        OMString omstr = new OMString("Well, o'course it was nailed there! If I hadn't nailed that bird down, it would have nuzzled up to those bars, bent 'em apart with its beak, and VOOM! Feeweeweewee!");
        omi.putAt(oms, omstr);
        char[] s = BinaryRenderer.render(omi);
        assert s[0] == TYPE_ATTRIBUTION;
        assert s[1] == TYPE_ATTRPAIRS;
        assert s[2] == TYPE_SYMBOL;
        assert s[188] == TYPE_ATTRIBUTION_END;
        assert s.length == 189;
        OMInteger omi2 = (OMInteger) BinaryParser.parse(s);
        assert omi.equals(omi2);
        assert omi.getAt(oms).equals(omstr);
    }

    //==============================================================================
    // OMOBJ
    public void testobject() throws Exception {
        OMObject omo = new OMObject(new OMInteger("8888888888888888888888888888888888888888888"));
        char[] s = BinaryRenderer.render(omo);
        assert s[0] == TYPE_OBJECT;
        int len = s.length;
        assert s[1] == TYPE_INT_BIG;
        assert s[len-1] == TYPE_OBJECT_END;
        assert omo.equals(BinaryParser.parse(s));
    }

    //==============================================================================
    // Compression Tests
    public void testCompression() throws Exception {
        OMSymbol oms = new OMSymbol("abcd", "efgh");
        OMSymbol oms2 = new OMSymbol("muff", "puff");
        OMInteger omi = new OMInteger(12);
        OMApply oma = oms.apply(new OpenMathBase[]{oms, omi, oms2, oms, oms2, omi, oms2, oms, oms2, omi, oms2, oms, oms2});
        char[] s = BinaryRenderer.render(oma, true);
        char[] s2 = BinaryRenderer.render(oma, false);
        assert s.length < s2.length;
        //hexdump(s);
        OMApply oma2 = (OMApply) BinaryParser.parse(s);
        assert oma.equals(oma2);
        OMApply oma3 = (OMApply) BinaryParser.parse(s2);
        assert oma.equals(oma3);
    }

    public void testCompression2() throws Exception {
        OpenMathBase om = OpenMathBase.parse("  <OMOBJ xmlns='http://www.openmath.org/OpenMath'><OMA><OMS cd='polyd1' name='DMP'/> <OMA><OMS cd='polyd1' name='poly_ring_d_named'/> <OMS cd='fieldname1' name='Q'/> <OMV\n" +
                "  name='x'/> </OMA> <OMA><OMS cd='polyd1' name='SDMP'/> <OMA><OMS cd='polyd1' name='term'/> <OMI>-636</OMI> <OMI>1000</OMI> </OMA> <OMA><OMS cd='polyd1' name='term'/>\n" +
                "  <OMI>397</OMI> <OMI>999</OMI> </OMA> <OMA><OMS cd='polyd1' name='term'/> <OMI>-293</OMI> <OMI>998</OMI> </OMA>\n" +
                "  </OMA> </OMA> </OMOBJ>");
        char[] s = BinaryRenderer.render(om, true);
        char[] s2 = BinaryRenderer.render(om, false);
//        hexdump (s);
//        hexdump (s2);
        assert s.length < s2.length;
        OpenMathBase om2 = BinaryParser.parse(s);
//        System.out.println(om.toXml());
//        System.out.println(om2.toXml());
        assert om.equals(om2);
        OpenMathBase om3 = BinaryParser.parse(s2);
        assert om.equals(om3);
    }

    public void testCompression3() throws Exception {
        OpenMathBase om = OpenMathBase.parse("<?xml version=\"1.0\"?>\n" +
                "<OMOBJ xmlns=\"http://www.openmath.org/OpenMath\">\n" +
                "\t<OMA>\n" +
                "\t\t<OMS cd=\"linalg2\" name=\"matrix\"/>\n" +
                "\t\t<OMA>\n" +
                "\t\t\t<OMS cd=\"linalg2\" name=\"matrixrow\"/>\n" +
                "\t\t\t<OMA>\n" +
                "\t\t\t\t<OMS cd=\"polyd1\" name=\"DMP\"/>\n" +
                "\t\t\t\t<OMA>\n" +
                "\t\t\t\t\t<OMS cd=\"polyd1\" name=\"poly_ring_d_named\"/>\n" +
                "\t\t\t\t\t<OMS cd=\"fieldname1\" name=\"Q\"/>\n" +
                "\t\t\t\t\t<OMV name=\"x\"/>\n" +
                "\t\t\t\t</OMA>\n" +
                "\t\t\t\t<OMA>\n" +
                "\t\t\t\t\t<OMS cd=\"polyd1\" name=\"SDMP\"/>\n" +
                "\t\t\t\t\t<OMA>\n" +
                "\t\t\t\t\t\t<OMS cd=\"polyd1\" name=\"term\"/>\n" +
                "\t\t\t\t\t\t<OMI>3</OMI>\n" +
                "\t\t\t\t\t\t<OMI>10</OMI>\n" +
                "\t\t\t\t\t</OMA>\n" +
                "\t\t\t\t\t<OMA>\n" +
                "\t\t\t\t\t\t<OMS cd=\"polyd1\" name=\"term\"/>\n" +
                "\t\t\t\t\t\t<OMI>784</OMI>\n" +
                "\t\t\t\t\t\t<OMI>8</OMI>\n" +
                "\t\t\t\t\t</OMA>\n" +
                "\t\t\t\t\t<OMA>\n" +
                "\t\t\t\t\t\t<OMS cd=\"polyd1\" name=\"term\"/>\n" +
                "\t\t\t\t\t\t<OMI>581</OMI>\n" +
                "\t\t\t\t\t\t<OMI>7</OMI>\n" +
                "\t\t\t\t\t</OMA>\n" +
                "\t\t\t\t\t<OMA>\n" +
                "\t\t\t\t\t\t<OMS cd=\"polyd1\" name=\"term\"/>\n" +
                "\t\t\t\t\t\t<OMI>716</OMI>\n" +
                "\t\t\t\t\t\t<OMI>6</OMI>\n" +
                "\t\t\t\t\t</OMA>\n" +
                "\t\t\t\t\t<OMA>\n" +
                "\t\t\t\t\t\t<OMS cd=\"polyd1\" name=\"term\"/>\n" +
                "\t\t\t\t\t\t<OMI>-63</OMI>\n" +
                "\t\t\t\t\t\t<OMI>5</OMI>\n" +
                "\t\t\t\t\t</OMA>\n" +
                "\t\t\t\t</OMA>\n" +
                "\t\t\t</OMA>\n" +
                "\t\t</OMA>\n" +
                "\t</OMA>\n" +
                "</OMOBJ>");

        File tfile = File.createTempFile("test", "om_binenc");
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(tfile));
        Writer out = new OutputStreamWriter(dos, Charset.forName("ISO-8859-1"));
        om.toBinary(out);
        out.close();

        DataInputStream dis = new DataInputStream(new FileInputStream(tfile));
        Reader in = new InputStreamReader(dis,Charset.forName("ISO-8859-1"));
        OpenMathBase om2 = OpenMathBase.parse(in);
        assert om.equals(om2);

		tfile.delete();
    }

    public void testFromMickael() throws Exception {
        String s = "<OMOBJ><OMATTR><OMATP><OMS cd=\"scscp1\" name=\"call_id\"/><OMSTR>libSCSCP:0x7fff5fbfd800:1250775649:0</OMSTR><OMS cd=\"scscp1\" name=\"option_return_object\"/><OMSTR> </OMSTR></OMATP><OMA><OMS cd=\"scscp1\" name=\"procedure_call\"/><OMA><OMS cd=\"scscp2\" name=\"get_allowed_heads\"/></OMA></OMA></OMATTR></OMOBJ>";
        OpenMathBase om = OpenMathBase.parse(s);
        char[] omb = om.toBinary();
        //hexdump(omb);
        String ss = new String(omb);
        OpenMathBase om2 = OpenMathBase.parse(ss);
        assert equal(om, om2);

        // now, do it w/ files
        File tfile = File.createTempFile("test", "om_binenc");
        DataOutputStream dos = new DataOutputStream(new FileOutputStream(tfile));
        Writer out = new OutputStreamWriter(dos, Charset.forName("UTF-8"));
        om.toBinary(out);
        out.close();

        DataInputStream dis = new DataInputStream(new FileInputStream(tfile));
        Reader in = new InputStreamReader(dis,Charset.forName("UTF-8"));
        om2 = OpenMathBase.parse(in);
        assert om.equals(om2);

        tfile.delete();
    }


    //==============================================================================
    // Helpers
    private void hexdump(char[] s) {
        System.out.printf ("---- Total Length: %d ----", s.length);
        for (int i = 0; i<s.length; i++) {
            if (i%20 == 0)
                System.out.println("");
            else if (i%10 == 0)
                System.out.print("  ");
            else if (i%5 == 0)
                System.out.print(" ");

            System.out.printf("%02x ", (int) s[i]);
        }
        System.out.println("");
    }

    private boolean equal(OpenMathBase om1, OpenMathBase om2) {
        if(om1.equals(om2))
            return true;
        System.out.printf("NOT EQUAL: '%s' and '%s'\n", om1.toPopcorn(), om2.toPopcorn());
        return false;
    }

}
