package org.hisp.dhis.pbf.math;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import de.congrace.exp4j.Calculable;
import de.congrace.exp4j.CustomFunction;
import de.congrace.exp4j.ExpressionBuilder;
import de.congrace.exp4j.InvalidCustomFunctionException;
import de.congrace.exp4j.UnknownFunctionException;
import de.congrace.exp4j.UnparsableExpressionException;

public class ExpLogic {

	public static void main(String args[]) throws InvalidCustomFunctionException {

		BigDecimal result = null;

		 Expression expression = new Expression("1+1/3");
		 result = expression.eval();
		 System.out.println("result 1:" + result);
		 
		 expression.setPrecision(2);
		 result = expression.eval();
		 System.out.println("result 2:" + result);
		 
		 result = new Expression("(3.4 + -4.1)/2").eval();
		 System.out.println("result 3:" + result);
		 
		 result = new Expression("SQRT(a^2 + b^2)").with("a","2.4").and("b","9.253").eval();
		 System.out.println("result 4:" + result);
		 
		 BigDecimal a = new BigDecimal("2.4");
		 BigDecimal b = new BigDecimal("9.235");
		 result = new Expression("SQRT(a^2 + b^2)").with("a",a).and("b",b).eval();
		 System.out.println("result 5:" + result);
		 
		 result = new Expression("2.4/PI").setPrecision(128).setRoundingMode(RoundingMode.UP).eval();
		 System.out.println("result 6:" + result);
		 
		 result = new Expression("random() > 0.5").eval();
		 System.out.println("result 7:" + result);
		 
		 result = new Expression("not(x<7 || sqrt(max(x,9)) <= 3)").with("x","22.9").eval();
		 System.out.println("result 8:" + result);
		 
		 // not(a=>80 b*m, b*0)
		 
		 result = new Expression("not(x<7 || sqrt(max(x,9)) <= 3)").with("x","22.9").eval();
		 System.out.println("result nuh:" + result);
		 
		 Expression e = new Expression("2 * average(12,4,8) + if(50,80,2,14)");

		 e.addFunction(e.new Function("average", 3) {
		     @Override
		     public BigDecimal eval(List<BigDecimal> parameters) {
		         BigDecimal sum = parameters.get(0).add(parameters.get(1)).add(parameters.get(2));
		         return sum.divide(new BigDecimal(3));
		     }
		 });

		 e.addFunction(e.new Function("if", 4) {
		     @Override
		     public BigDecimal eval(List<BigDecimal> parameters) {
		    	 BigDecimal value = parameters.get(0);
		    	 BigDecimal evaluator = parameters.get(1);
		    	 if(value.compareTo(evaluator)>=0){
		    		 BigDecimal sum = parameters.get(2).multiply(parameters.get(3));
			         return sum;
		    	 }
		         return new BigDecimal(0);
		     }
		 });
		 
		 System.out.println("result nuh:" + e.eval());
		 
		 Expression ee = new Expression("if(2,4,80,79)");


		 ee.addFunction(ee.new Function("if", 4) {
		     @Override
		     public BigDecimal eval(List<BigDecimal> parameters) {
		    	 BigDecimal value = parameters.get(0);
		    	 BigDecimal evaluator = parameters.get(1);
		    	 if(value.compareTo(evaluator)>=0){
		    		 BigDecimal sum = parameters.get(2).multiply(parameters.get(3));
			         return sum;
		    	 }else
		         return new BigDecimal(0);
		     }
		 });
		 
		 System.out.println("result calcs:" + ee.eval());
	        
		 try {
			Calculable calc = new ExpressionBuilder("x * y - 2").withVariable("x",1).withVariable("y",2).build();
			System.out.println("result variable calcs: " + calc.calculate());
			
			
			Calculable calc2 = new ExpressionBuilder("x * y - 2").withVariableNames("x","y").build();
			
	calc.setVariable("x",1);
	calc.setVariable("y",2);
	
	System.out.println("result variable calcs2: " + calc.calculate());
			
		} catch (UnknownFunctionException e1) {
			e1.printStackTrace();
		} catch (UnparsableExpressionException x1){
			x1.printStackTrace();
		}
	
		 
		 CustomFunction ifFunc;
		try {
			ifFunc = new CustomFunction("if", 4) {
				    public double applyFunction(double[] values) {
				        
				    	double value = values[0];
				    	double evaluator = values[1];
				    	 if(value>=evaluator){
				    		 double sum = values[2]*values[3];
					         return sum;
				    	 }else
				         return 0;
				    	
				        }
				};
			
			double varX=12d;
			double varY=1d;
			Calculable calc;
				calc = new ExpressionBuilder("if(x,y,70,80)")
				        .withCustomFunction(ifFunc)
				        .withVariable("x",varX)
				        .withVariable("y",varY)
				        .build();
				calc.setVariable("x",1);
				calc.setVariable("y",2);

				System.out.println("result variable calcs3: " + calc.calculate());	
			} catch (UnknownFunctionException e1) {
				e1.printStackTrace();
			}catch (UnparsableExpressionException x1){
				x1.printStackTrace();
			}
			
	}
}
