package architecture.memory;

import java.io.IOException;

public class MemoryAccessException extends IOException {

	public MemoryAccessException() {
	}

	public MemoryAccessException(String message) {
		super(message);
	}

}
