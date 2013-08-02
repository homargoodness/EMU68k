package architecture.memory;

import java.io.IOException;

public class StorageException extends IOException {

	public StorageException() {
	}

	public StorageException(String message) {
		super(message);
	}

}
