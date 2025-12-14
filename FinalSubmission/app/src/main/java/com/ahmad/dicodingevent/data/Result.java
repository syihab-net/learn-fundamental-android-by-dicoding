package com.ahmad.dicodingevent.data;

public class Result {
    private Result() {
    }

    public final static class Success<T> extends Result {
        private final T data;

        public Success(T data) {
            this.data = data;
        }

        public T getData() {
            return this.data;
        }
    }

    public final static class Error extends Result {
        private final String error;

        public Error(String error) {
            this.error = error;
        }

        public String getError() {
            return this.error;
        }
    }

    public static class Loading extends Result {
    }
}
