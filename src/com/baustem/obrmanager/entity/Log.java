package com.baustem.obrmanager.entity;

public class Log extends IdEntity {

	private String operator;
	private String methodName;
	private String className;
	private String args;
	private String operateResult;
	private String returnValue;
	private String operateTime;
	private String errorMessage;

	public Log() {
		super();
	}

	public Log(String operator, String methodName, String className, String args, String operateResult,
			String returnValue, String operateTime, String errorMessage) {
		super();
		this.operator = operator;
		this.methodName = methodName;
		this.className = className;
		this.args = args;
		this.operateResult = operateResult;
		this.returnValue = returnValue;
		this.operateTime = operateTime;
		this.errorMessage = errorMessage;
	}

	public String getOperator() {
		return operator;
	}
	
	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getMethodName() {
		return methodName;
	}

	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getArgs() {
		return args;
	}

	public void setArgs(String args) {
		this.args = args;
	}

	public String getOperateResult() {
		return operateResult;
	}

	public void setOperateResult(String operateResult) {
		this.operateResult = operateResult;
	}

	public String getReturnValue() {
		return returnValue;
	}

	public void setReturnValue(String returnValue) {
		this.returnValue = returnValue;
	}

	public String getOperateTime() {
		return operateTime;
	}

	public void setOperateTime(String operateTime) {
		this.operateTime = operateTime;
	}

	public String getErrorMessage() {
		return errorMessage;
	}

	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}

	//use for testing
	@Override
	public String toString() {
		return "Log [operator=" + operator + ", methodName=" + methodName
				+ ", className=" + className + ", args=" + args
				+ ", operateResult=" + operateResult + ", returnValue="
				+ returnValue + ", operateTime=" + operateTime
				+ ", errorMessage=" + errorMessage + "]";
	}
	
	

}
