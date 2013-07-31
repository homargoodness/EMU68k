package controller;

import java.io.IOException;

public class IllegalInstructionException extends IOException {
	
	public IllegalInstructionException() {
		
	}
	
	public IllegalInstructionException(String message) {
		super(message);
	}

}

