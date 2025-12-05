//package com.icia.fproject.vrp.util;
//
//import lombok.Data;
//
//import java.util.HashMap;
//import java.util.Map;
//@Data
//public class JsonResult {
// public enum Code {
//   SUCC, FAIL
// }
// private Code code = Code.SUCC;
// private String msg;
// private Map<String, Object> data;
// public void addData(String key, Object value) {
//   if (data == null) {
//     data = new HashMap<>();
//   }
//   data.put(key, value);
// }
//
//    // 에러 상태를 설정하는 메서드 추가
//    public static JsonResult error(String msg) {
//        JsonResult result = new JsonResult();
//        result.setCode(Code.FAIL); // 실패 상태로 설정
//        result.setMsg(msg); // 에러 메시지 설정
//        return result;
//    }
//
//    // 성공 상태를 설정하는 메서드 (선택 사항)
//    public static JsonResult success(String msg) {
//        JsonResult result = new JsonResult();
//        result.setCode(Code.SUCC); // 성공 상태로 설정
//        result.setMsg(msg); // 성공 메시지 설정
//        return result;
//    }
//}


package com.kimleehan.bhproject.vrp.util;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

@Data
public class JsonResult {

    public enum Code {
        SUCC, FAIL
    }

    private Code code = Code.SUCC; // 기본값은 성공
    private String msg;           // 메시지
    private Map<String, Object> data; // 응답 데이터

    // 데이터 추가 메서드
    public void addData(String key, Object value) {
        if (data == null) {
            data = new HashMap<>();
        }
        data.put(key, value);
    }

    // 에러 상태를 반환하는 메서드
    public static JsonResult error(String msg) {
        JsonResult result = new JsonResult();
        result.setCode(Code.FAIL); // 실패 상태 설정
        result.setMsg(msg);        // 에러 메시지 설정
        return result;
    }

    // 성공 상태를 반환하는 메서드
    public static JsonResult success(String msg) {
        JsonResult result = new JsonResult();
        result.setCode(Code.SUCC); // 성공 상태 설정
        result.setMsg(msg);        // 성공 메시지 설정
        return result;
    }
}
