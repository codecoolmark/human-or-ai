package at.codecool.humanoraiserver;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Result<T> {
    private final T value;
    private final String error;
    private final boolean isOk;

    public static <T> Result<T> of(T value) {
        return new Result<>(value, null, true);
    }

    public static <T> Result<T> error(String error) {
        return new Result<>(null, error, false);
    }

    @JsonProperty(value = "isOk")
    public boolean isOk() {
        return this.isOk;
    }

    public boolean isError() {
        return !this.isOk;
    }

    public T getValue() {
        return this.value;
    }

    public String getError() {
        return this.error;
    }

    private Result(T value, String error, boolean isOk) {
        this.value = value;
        this.error = error;
        this.isOk = isOk;
    }
}
