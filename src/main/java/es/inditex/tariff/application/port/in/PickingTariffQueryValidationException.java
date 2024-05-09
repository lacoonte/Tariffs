package es.inditex.tariff.application.port.in;

public class PickingTariffQueryValidationException extends Exception {
    public PickingTariffQueryValidationException() {
        this(null, null);
    }

    public PickingTariffQueryValidationException(final String message, final Throwable cause) {
        super(message);
        if (cause != null) super.initCause(cause);
    }
}
