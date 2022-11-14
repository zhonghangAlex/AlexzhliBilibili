package com.alexzhli.bilibili.domain;

public class JsonResponse<T> {
    private String code;

    private String msg;

    private T data;

    public JsonResponse(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public JsonResponse(T data) {
        this.data = data;
        msg = "成功";
        code = "0";
    }

    // 成功了，但是不需要返回前端具体的信息
    public static JsonResponse<String> success() {
        return new JsonResponse<>(null);
    }

    // 成功了，需要给前端返回具体的信息
    public static JsonResponse<String> success(String data) {
        return new JsonResponse<>(data);
    }

    // 失败了，返回通用的失败信息
    public static JsonResponse<String> fail() {
        return new JsonResponse<>("1", "失败");
    }

    // 失败了，需要给前端返回具体的失败信息
    public static JsonResponse<String> fail(String code, String msg) {
        return new JsonResponse<>(code, msg);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
