package cn.luckybin.Controller;

import lombok.Data;

/**
 * @ClassName ResponseData
 * @Author liuyunbin
 * @Date 2019/7/15 15:50
 * @Description TODO
 */
@Data
public class ResponseData {
    private int code;
    private String msg;
    private Object data;

    public ResponseData(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public ResponseData(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
