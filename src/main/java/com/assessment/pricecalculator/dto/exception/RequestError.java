package com.assessment.pricecalculator.dto.exception;

public class RequestError {

	private ServiceException serviceException;

	public ServiceException getServiceException() {
		return serviceException;
	}

	public void setServiceException(ServiceException serviceException) {
		this.serviceException = serviceException;
	}
}
