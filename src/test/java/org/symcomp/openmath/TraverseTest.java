package org.symcomp.openmath;

import java.math.BigInteger;


public class TraverseTest {


    public void test1() throws OpenMathException {
        OMObject o = OpenMathBase.parsePopcorn("1+2").toOMObject();
        o.traverse(new OpenMathVisitor() {
            public OpenMathBase visit(OMInteger om) { return new OMInteger(om.getIntValue().add(new BigInteger("1"))); }
        });
        assert o.toPopcorn().equals("2 + 3");
    }

    public void test2() throws OpenMathException {
        OMObject o = OpenMathBase.parsePopcorn("1+$a").toOMObject();
        o.traverse(new OpenMathVisitor() {
            public OpenMathBase visit(OMInteger om) { return new OMInteger(om.getIntValue().add(new BigInteger("1"))); }
            public OpenMathBase visit(OMVariable om) { return new OMVariable(om.getName()+"b"); }
        });
        assert o.toPopcorn().equals("2 + $ab");
    }


    public void test3() throws OpenMathException {
        OMObject o = OpenMathBase.parsePopcorn("sin(1+$a)").toOMObject();
        o.traverse(new OpenMathVisitor() {
            public OpenMathBase visit(OMInteger om) { return new OMInteger(om.getIntValue().add(new BigInteger("1"))); }
            public OpenMathBase visit(OMVariable om) { return new OMVariable(om.getName()+"b"); }
            public OpenMathBase visit(OMSymbol om) { return new OMSymbol(om.getName(), om.getCd()); }
        });
        assert o.isObject();
        assert o.toPopcorn().equals("sin.transc1(plus.arith1(2, $ab))");

    }

}
