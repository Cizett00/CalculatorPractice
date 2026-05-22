package org.example;

public class CalResult<T> { //결과를 제네릭으로 저장
    private T result;

    public T getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
    }
}
