package com.baustem.obrmanager.aop;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.baustem.obrmanager.entity.Log;
import com.baustem.obrmanager.entity.User;
import com.baustem.obrmanager.router.RouterToken;
import com.baustem.obrmanager.service.LogService;

@Component
public class LogAspect {

	@Autowired
	private LogService logService;
	@Autowired
	private HttpSession session;
	
	//环绕通知
	public Object recordLog(ProceedingJoinPoint jointPoint) throws Throwable{
		Object[] args = jointPoint.getArgs();  
		Object returnValueObj = null;
		
		String methodName = null;
		String className = null;
		String argsStr = null;
		String operateResult = null;
		String returnValue = null;
		String errorMessage = null;
		try {
			Signature signature = jointPoint.getSignature();
			methodName = signature.getName();
			className = signature.getDeclaringTypeName();
			argsStr = Arrays.asList(args).toString();
			//
			returnValueObj = jointPoint.proceed(args);
			operateResult = "Success";
			returnValue = returnValueObj==null?"this method has No Return Value":returnValueObj.toString();
			
		} catch (Throwable e) {
			operateResult = "Failed";
			returnValue = "Method throws exception,no return value";
			errorMessage = e.getMessage();
			Throwable cause = e.getCause();
			while(cause!=null){
				errorMessage = e.getMessage();
				cause = cause.getCause();
			}
			throw e;
		} finally {
			String operator = getOperator(returnValueObj);
			String operateTime = getOperateTime();
			Log log = new Log(operator, methodName, className, argsStr, operateResult, returnValue, operateTime, errorMessage);
			RouterToken token = new RouterToken();
			token.setTokenValue("log");
			RouterToken.bindToken(token);
			
			logService.saveLog(log);
			
		}
		return returnValueObj;
	}

	private String getOperateTime() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
	}

	private String getOperator(Object obj) {
		User user = (User) session.getAttribute("user");
		String operator = null;
		if(user==null && obj instanceof User){
			User u = (User)obj;
			operator = u.getName();
		}
		if(user !=null){
			operator = user.getName();
		}
		return operator;
	}
	
	public void setLogService(LogService logService) {
		this.logService = logService;
	}
	
	public LogService getLogService() {
		return logService;
	}
	
	
	
	
	
}
