package com.assessment.pricecalculator.exceptions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import com.assessment.pricecalculator.dto.exception.ErrorDTO;
import com.assessment.pricecalculator.dto.exception.CommonErrorMessage;
import com.assessment.pricecalculator.dto.exception.RequestError;
import com.assessment.pricecalculator.dto.exception.ServiceException;
import com.assessment.pricecalculator.utils.ServiceErrorType;
import com.assessment.pricecalculator.utils.ValidationType;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;

@ControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		/* construct exception message object */
		CommonErrorMessage commonErrorMessage = new CommonErrorMessage();
		RequestError requestError = new RequestError();
		ServiceException serviceException = null;

		/* construct Multimap for store errors */
		Multimap<String, ErrorDTO> errorMap = ArrayListMultimap.create();

		List<FieldError> errorCodes = ex.getBindingResult().getFieldErrors();
		for (FieldError fieldError : errorCodes) {

			String error = fieldError.getCode();
			String errorField = fieldError.getDefaultMessage();
			String rejectedValue = String.valueOf(fieldError.getRejectedValue());

			ErrorDTO errorDTO = new ErrorDTO();
			errorDTO.setErrorField(errorField);
			errorDTO.setRejectedValue(rejectedValue);

			errorMap.put(error, errorDTO);
		}

		if (errorMap.containsKey(ValidationType.NOT_BLANK.getTObject())
				|| errorMap.containsKey(ValidationType.NOT_NULL.getTObject())
				|| errorMap.containsKey(ValidationType.NOT_EMPTY.getTObject())) {

			Collection<ErrorDTO> missingDTOCollection = new ArrayList<>();

			/* collect NotBlank violation errors */
			Collection<ErrorDTO> notBlankerrorDTOCollection = errorMap.get(ValidationType.NOT_BLANK.getTObject());
			/* collect NotNull violation errors */
			Collection<ErrorDTO> notNullErrorDTOCollection = errorMap.get(ValidationType.NOT_NULL.getTObject());
			/* collect NotEmpty violation errors */
			Collection<ErrorDTO> notEmptyErrorDTOCollection = errorMap.get(ValidationType.NOT_EMPTY.getTObject());

			missingDTOCollection.addAll(notBlankerrorDTOCollection);
			missingDTOCollection.addAll(notNullErrorDTOCollection);
			missingDTOCollection.addAll(notEmptyErrorDTOCollection);

			List<String> missingParameterList = new ArrayList<>();
			for (ErrorDTO errorDTO : missingDTOCollection) {

				missingParameterList.add(errorDTO.getErrorField());
			}

			String errorFieldCommaSeparated = String.join(",", missingParameterList);

			serviceException = new ServiceException();

			serviceException.setMessageId(ServiceErrorType.SVC_0002.getErrorCode());
			serviceException.setText(ServiceErrorType.SVC_0002.getErrorMessage());

			if (missingParameterList.size() > 1) {

				/*
				 * if missingParameterList contains more than one, then error message should be
				 * like following
				 */
				serviceException.setVariables("Missing mandatory parameters: " + errorFieldCommaSeparated);
			} else {

				/*
				 * if missingParameterList contains only one, then error message should be like
				 * following
				 */
				serviceException.setVariables("Missing mandatory parameter: " + missingParameterList.get(0));
			}
		} else if (errorMap.containsKey(ValidationType.DECIMAL_MIN.getTObject())) {

			Collection<ErrorDTO> decimalMinErrorDTOCollection = errorMap.get(ValidationType.DECIMAL_MIN.getTObject());
			List<String> decimalMinErrorFieldList = new ArrayList<>();
			for (ErrorDTO errorDTO : decimalMinErrorDTOCollection) {

				decimalMinErrorFieldList.add(errorDTO.getErrorField());
			}

			String errorFieldDecimalMinCommaSeparated = String.join(",", decimalMinErrorFieldList);

			serviceException = new ServiceException();

			serviceException.setMessageId(ServiceErrorType.SVC_0002.getErrorCode());
			serviceException.setText(ServiceErrorType.SVC_0002.getErrorMessage());
			serviceException
					.setVariables(errorFieldDecimalMinCommaSeparated + " should be equal or greater than 0.01 ");
		} else if (errorMap.containsKey(ValidationType.MIN.getTObject())) {

			Collection<ErrorDTO> minErrorDTOCollection = errorMap.get(ValidationType.MIN.getTObject());
			List<String> minErrorFieldList = new ArrayList<>();
			for (ErrorDTO errorDTO : minErrorDTOCollection) {

				minErrorFieldList.add(errorDTO.getErrorField());
			}

			String errorFieldMinCommaSeparated = String.join(",", minErrorFieldList);

			serviceException = new ServiceException();

			serviceException.setMessageId(ServiceErrorType.SVC_0002.getErrorCode());
			serviceException.setText(ServiceErrorType.SVC_0002.getErrorMessage());
			serviceException.setVariables(errorFieldMinCommaSeparated + " equal or greater than 1 ");
		} else if (errorMap.containsKey(ValidationType.ORDER_TYPE.getTObject())) {

			Collection<ErrorDTO> orderTypeErrorDTOCollection = errorMap.get(ValidationType.ORDER_TYPE.getTObject());
			List<String> orderTypeErrorFieldList = new ArrayList<>();
			List<String> orderTypeRejectedValueList = new ArrayList<>();
			for (ErrorDTO errorDTO : orderTypeErrorDTOCollection) {

				orderTypeErrorFieldList.add(errorDTO.getErrorField());
				orderTypeRejectedValueList.add(errorDTO.getRejectedValue());
			}

			String orderTypeCommaSeparated = String.join(",", orderTypeRejectedValueList);

			serviceException = new ServiceException();

			serviceException.setMessageId(ServiceErrorType.SVC_0002.getErrorCode());
			serviceException.setText(ServiceErrorType.SVC_0002.getErrorMessage());
			serviceException.setVariables("Invalid orderType : " + orderTypeCommaSeparated);
		}

		requestError.setServiceException(serviceException);
		commonErrorMessage.setRequestError(requestError);

		return new ResponseEntity<>(commonErrorMessage, headers, status);
	}

	@ExceptionHandler(ProductNotFoundException.class)
	public final ResponseEntity<Object> handleProductNotFoundException(ProductNotFoundException ex,
			WebRequest request) {

		CommonErrorMessage commonErrorMessage = new CommonErrorMessage();
		RequestError requestError = new RequestError();
		ServiceException serviceException = new ServiceException();

		serviceException.setMessageId(ServiceErrorType.SVC_0001.getErrorCode());
		serviceException.setText(ServiceErrorType.SVC_0001.getErrorMessage());
		serviceException.setVariables(ex.getLocalizedMessage());

		requestError.setServiceException(serviceException);
		commonErrorMessage.setRequestError(requestError);

		return new ResponseEntity<>(commonErrorMessage, HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(PriceCalculatorException.class)
	public final ResponseEntity<Object> handlePriceCalculatorException(PriceCalculatorException ex,
			WebRequest request) {

		CommonErrorMessage commonErrorMessage = new CommonErrorMessage();
		RequestError requestError = new RequestError();
		ServiceException serviceException = new ServiceException();

		serviceException.setMessageId(ServiceErrorType.SVC_0001.getErrorCode());
		serviceException.setText(ServiceErrorType.SVC_0001.getErrorMessage());
		serviceException.setVariables(ex.getLocalizedMessage());

		requestError.setServiceException(serviceException);
		commonErrorMessage.setRequestError(requestError);

		return new ResponseEntity<>(commonErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllException(Exception ex, WebRequest request) {

		CommonErrorMessage commonErrorMessage = new CommonErrorMessage();
		RequestError requestError = new RequestError();
		ServiceException serviceException = new ServiceException();

		serviceException.setMessageId(ServiceErrorType.SVC_0001.getErrorCode());
		serviceException.setText(ServiceErrorType.SVC_0001.getErrorMessage());
		serviceException.setVariables(ex.getLocalizedMessage());

		requestError.setServiceException(serviceException);
		commonErrorMessage.setRequestError(requestError);

		return new ResponseEntity<>(commonErrorMessage, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
