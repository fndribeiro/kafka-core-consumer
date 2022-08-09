package br.com.ribeiro.fernando.kafkacoreconsumer.domain.entities;

public class EmployeeReplyMessage {
	
	private boolean success;
	private String message;
	
	public EmployeeReplyMessage(boolean success, String message) {
		this.success = success;
		this.message = message;
	}

	public boolean isSuccess() {
		return success;
	}

	public String getMessage() {
		return message;
	}
	
}
