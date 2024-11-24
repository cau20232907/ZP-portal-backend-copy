package org.zeropage.portal.exception;

import java.io.IOException;

public class FileNotSavableException extends IOException {
    public FileNotSavableException() {
    }

    public FileNotSavableException(String message) {
        super(message);
    }

    public FileNotSavableException(String message, Throwable cause) {
        super(message, cause);
    }

    public FileNotSavableException(Throwable cause) {
        super(cause);
    }
}
