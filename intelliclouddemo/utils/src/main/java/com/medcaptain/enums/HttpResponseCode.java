package com.medcaptain.enums;

/**
 * @author : yangzhixiong
 * @description : 错误码的枚举类
 * @date : 2018/9/17
 */
public enum HttpResponseCode {
    /**
     * 错误码的枚举类
     */
    SUCCESS(200, "操作成功"),
    SUCCESS_INTERNAL_DEVICE(200,  "内部设备"),
    ERROR(409, "操作失败"),
    ERROR_PARAMETER(401, "参数校验失败"),
    RESOURCE_IN_USE(401, "资源已被使用,不能进行指定操作"),
    ERROR_VERIFY_CODE(401, "验证码校验失败"),
    ERROR_NO_POWER(403, "没有权限"),
    ERROR_URL(404, "请求地址不存在"),
    ERROR_IO(404, "IO错误"),
    RESOURCE_NOT_FOUND(404, "未找到指定资源"),
    ERROR_NO_BIND_PHONE(409, "未绑定手机号"),
    ERROR_IDENTIFYING_CODE(409, "验证码匹配失败"),
    ERROR_VALIDATION_BIND(409, "验证绑定错误"),
    ERROR_USER_INFO(409, "错误的用户信息"),
    ERROR_LOGIN_TYPE(409, "错误的登录类型"),
    ERROR_USER_PASSWORD(409, "账号或密码错误"),
    ERROR_DOMAIN_NOT_BINDING(409, "访问域名未绑定"),
    ERROR_TYPE(409, "错误的类型"),
    ERROR_GET_VERIFICATION_CODE(409, "获取验证码失败"),
    ERROR_PHONE_NUMBER(409, "错误的手机号"),
    ERROR_MESSAGE_NOT_READABLE(409, "信息不可读"),
    ERROR_PAGE_NOT_FOUND(409, "没有找到您需要的页数"),
    ERROR_METHOD_NOT_ALLOW(409, "非法的方法"),
    ERROR_ILLEGAL_ARGUMENT(409, "非法参数"),
    ERROR_PARAMETER_FORMAT(409,"参数格式异常"),
    ERROR_PARAMETER_EXTRA_LONG(409,"参数超长"),
    ERROR_PRODUCT_NO_POWER(403, "没有权限访问该产品或设备"),
    ERROR_TOKEN_EXPIRE(440, "token到期了"),
    EMPTY_TOKEN(440, "token为空"),
    ERROR_RESOURCE_EXPIRE(409, "请求资源不存在"),
    ERROR_FILE_NOT_FOUND(409, "文件上传失败"),
    ERROR_FILE_DOWNLOAD(409, "文件下载失败"),
    ERROR_NO_STORAGE(409, "没有可用的存储节点"),
    ERROR_RESOURCE_ALREADY_EXIST(409, "信息已存在"),
    ERROR_PRODUCT_NOT_EXIST(409, "产品不存在"),
    ERROR_DEVICE_NOT_EXIST(409, "设备不存在"),
    ERROR_DEVICE_NOT_ORGANIZATION_ID(409, "设备不属于当前机构"),
    ERROR_DEVICE_EXIST(409, "设备存在"),
    ERROR_PRODUCT_EXIST(409, "产品不存在"),
    ERROR_DEVICE_NO_RESPONSE(409, "设备无响应"),
    ERROR_DEVICE_IS_UPGRADING(409, "设备正在升级"),
    ERROR_DEVICE_IS_NOT_UPGRADING(409, "设备不在升级状态"),
    ERROR_DEVICE_ERROR_RETURN(409, "设备返回数据错误"),
    ERROR_MESSAGE_DUPLICATION(409, "消息重复"),
    ERROR_TRANSACTION_NAME_IS_NULL(409, "参数不能为空"),
    ERROR_OBJECT_IS_NULL(409, "对象为空"),
    ERROR_LOG_IS_OBTAINING(409, "日志文件正在获取，请勿重复点击"),
    ERROR_DEVICE_IS_ONLINE(409, "在线设备，禁止当前操作"),
    ERROR_FILE_IS_EMPTY(409, "文件为空"),
    ERROR_FILE_MD5_MISMATCH(409, "文件MD5校验失败"),
    ERROR_PRODUCTKEY_MISMATCH(409, "productKey不匹配"),
    ERROR_PHYSICAL_MODEL_ERROR(409, "物模型不符合规范"),

    ERROR_SIGN_400(400, "客户端请求有语法错误,不能被服务器所理解"),
    /**
     * 这个状态代码必须和WWW-Authenticate报头域一起使用
     */
    ERROR_UNAUTHRORIZED_ACCESS(401, "未授权访问"),
    ERROR_SIGN_401(401, "没有找到访问资源 或 请求未经授权"),
    ERROR_SIGN_401_2(401, "未授权：服务器配置问题导致登录失败"),
    ERROR_SIGN_401_3(401, "ACL 禁止访问资源"),
    ERROR_SIGN_401_4(401, "未授权：授权被筛选器拒绝"),
    ERROR_SIGN_401_5(401, "未授权：ISAPI 或 CGI 授权失败"),
    ERROR_SIGN_402(402, "保留有效ChargeTo头响应"),
    ERROR_SIGN_403(403, "禁止访问，服务器收到请求，但是拒绝提供服务"),
    ERROR_SIGN_403_1(403, "禁止访问：禁止可执行访问"),
    ERROR_SIGN_403_2(403, "禁止访问：禁止读访问"),
    ERROR_SIGN_403_3(403, "禁止访问：禁止写访问"),
    ERROR_SIGN_403_4(403, "禁止访问：要求 SSL"),
    ERROR_SIGN_403_5(403, "禁止访问：要求 SSL 128"),
    ERROR_SIGN_403_6(403, "禁止访问：IP 地址被拒绝"),
    ERROR_SIGN_403_7(403, "禁止访问：要求客户证书"),
    ERROR_SIGN_403_8(403, "禁止访问：禁止站点访问"),
    ERROR_SIGN_403_9(403, "禁止访问：连接的用户过多"),
    ERROR_SIGN_403_10(403, "禁止访问：配置无效"),
    ERROR_SIGN_403_11(403, "禁止访问：密码更改"),
    ERROR_SIGN_403_12(403, "禁止访问：映射器拒绝访问"),
    ERROR_SIGN_403_13(403, "禁止访问：客户证书已被吊销"),
    ERROR_SIGN_403_15(403, "禁止访问：客户访问许可过多"),
    ERROR_SIGN_403_16(403, "禁止访问：客户证书不可信或者无效"),
    ERROR_SIGN_403_17(403, "禁止访问：客户证书已经到期或者尚未生效"),
    ERROR_SIGN_404(404, "无法取得所请求的网页，请求资源不存在"),

    ERROR_PARAMETER_NOT_FOUND(404, "在产品模板中未找到传入的参数"),
    ERROR_PROPERTY_NOT_FOUND(404, "在产品模板中未找到传入的属性定义"),
    CURRENT_USER_NOT_FOUND(440, "登录信息已过期，请重新登录。"),
    ERROR_PROPERTY_ACESS_DENIED(401, "产品属性的读写类型不匹配"),
    ERROR_ILLEGAL_PARAMETER_FORMAT(400, "传入参数格式不正确"),
    ERROR_SIGN_405(405, "用户在Request-Line字段定义的方法不允许"),
    ERROR_SIGN_406(406, "根据用户发送的Accept拖，请求资源不可访问"),
    ERROR_SIGN_407(407, "必须首先在代理服务器上得到授权"),
    ERROR_SIGN_408(408, "客户端没有在用户指定的时间内完成请求"),
    ERROR_SIGN_409(409, "对当前资源状态，请求不能完成"),
    ERROR_SIGN_410(410, "服务器上不再有此资源且无进一步的参考地址"),
    ERROR_SIGN_411(411, "服务器拒绝用户定义的Content-Length属性请求"),
    ERROR_SIGN_412(412, "对一个或多个请求头字段在当前请求中错误"),
    ERROR_SIGN_413(413, "请求的资源大于服务器允许的大小"),
    ERROR_SIGN_414(414, "请求的资源URL长于服务器允许的长度"),
    ERROR_SIGN_415(415, "请求资源不支持请求项目格式"),
    ERROR_SIGN_416(416, "请求中包含Range请求头字段，在当前请求资源范围内没有range指示值，请求也不包含If-Range请求头字段"),
    ERROR_SIGN_417(417, "服务器不满足请求Expect头字段指定的期望值，如果是代理服务器，可能是下一级服务器不能满足请求长。"),

    SERVER_INTERNAL_ERROR(500, "服务器遇到错误，无法完成请求"),
    ERROR_SIGN_500_100(500, "内部服务器错误"),
    ERROR_SIGN_500_11(500, "服务器关闭"),
    ERROR_SIGN_500_12(500, "应用程序重新启动"),
    ERROR_SIGN_500_13(500, "服务器太忙"),
    ERROR_SIGN_500_14(500, "应用程序无效"),
    ERROR_SIGN_500_15(500, "不允许请求 global.asa"),
    ERROR_SIGN_501(501, "未实现"),
    ERROR_SIGN_502(502, "网关错误"),
    ERROR_SIGN_503(503, "由于超载或停机维护，服务器目前无法使用，一段时间后可能恢复正常");


    private int code;
    private String msg;

    HttpResponseCode(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

//    public String getMsg(HttpResponseCode errorCode) {
//        String returnMsg = null;
//        switch (errorCode) {
//            case SUCCESS:
//                returnMsg = SUCCESS.getMsg();
//                break;
//            default:
//                break;
//        }
//        return returnMsg == null ? null : errorCode.getMsg();
//    }

    /**

     1**(信息类)：表示接收到请求并且继续处理
     100——客户必须继续发出请求
     101——客户要求服务器根据请求转换HTTP协议版本

     2**(响应成功)：表示动作被成功接收、理解和接受
     200——表明该请求被成功地完成，所请求的资源发送回客户端
     201——提示知道新文件的URL
     202——接受和处理、但处理未完成
     203——返回信息不确定或不完整
     204——请求收到，但返回信息为空
     205——服务器完成了请求，用户代理必须复位当前已经浏览过的文件
     206——服务器已经完成了部分用户的GET请求

     3**(重定向类)：为了完成指定的动作，必须接受进一步处理
     300——请求的资源可在多处得到
     301——本网页被永久性转移到另一个URL
     302——请求的网页被转移到一个新的地址，但客户访问仍继续通过原始URL地址，重定向，新的URL会在response中的Location中返回，浏览器将会使用新的URL发出新的Request。
     303——建议客户访问其他URL或访问方式
     304——自从上次请求后，请求的网页未修改过，服务器返回此响应时，不会返回网页内容，代表上次的文档已经被缓存了，还可以继续使用
     305——请求的资源必须从服务器指定的地址得到
     306——前一版本HTTP中使用的代码，现行版本中不再使用
     307——申明请求的资源临时性删除

     4**(客户端错误类)：请求包含错误语法或不能正确执行
     400——客户端请求有语法错误，不能被服务器所理解
     401——请求未经授权，这个状态代码必须和WWW-Authenticate报头域一起使用
     401.2 - 未授权：服务器配置问题导致登录失败
     401.3 - ACL 禁止访问资源
     401.4 - 未授权：授权被筛选器拒绝
     401.5 - 未授权：ISAPI 或 CGI 授权失败
     402——保留有效ChargeTo头响应
     403——禁止访问，服务器收到请求，但是拒绝提供服务
     403.1 禁止访问：禁止可执行访问
     　　 403.2 - 禁止访问：禁止读访问
     　　 403.3 - 禁止访问：禁止写访问
     　　 403.4 - 禁止访问：要求 SSL
     　　 403.5 - 禁止访问：要求 SSL 128
     403.6 - 禁止访问：IP 地址被拒绝
     　　 403.7 - 禁止访问：要求客户证书
     　　 403.8 - 禁止访问：禁止站点访问
     　　 403.9 - 禁止访问：连接的用户过多
     　　 403.10 - 禁止访问：配置无效
     　　 403.11 - 禁止访问：密码更改
     　　 403.12 - 禁止访问：映射器拒绝访问
     　   403.13 - 禁止访问：客户证书已被吊销
     　　 403.15 - 禁止访问：客户访问许可过多
     　　 403.16 - 禁止访问：客户证书不可信或者无效
     403.17 - 禁止访问：客户证书已经到期或者尚未生效
     404——一个404错误表明可连接服务器，但服务器无法取得所请求的网页，请求资源不存在。eg：输入了错误的URL
     405——用户在Request-Line字段定义的方法不允许
     406——根据用户发送的Accept拖，请求资源不可访问
     407——类似401，用户必须首先在代理服务器上得到授权
     408——客户端没有在用户指定的饿时间内完成请求
     409——对当前资源状态，请求不能完成
     410——服务器上不再有此资源且无进一步的参考地址
     411——服务器拒绝用户定义的Content-Length属性请求
     412——一个或多个请求头字段在当前请求中错误
     413——请求的资源大于服务器允许的大小
     414——请求的资源URL长于服务器允许的长度
     415——请求资源不支持请求项目格式
     416——请求中包含Range请求头字段，在当前请求资源范围内没有range指示值，请求也不包含If-Range请求头字段
     417——服务器不满足请求Expect头字段指定的期望值，如果是代理服务器，可能是下一级服务器不能满足请求长。

     5**(服务端错误类)：服务器不能正确执行一个正确的请求
     500 - 服务器遇到错误，无法完成请求
     500.100 - 内部服务器错误 - ASP 错误
     500-11 服务器关闭
     500-12 应用程序重新启动
     500-13 - 服务器太忙
     500-14 - 应用程序无效
     500-15 - 不允许请求 global.asa
     501 - 未实现
     502 - 网关错误
     503：由于超载或停机维护，服务器目前无法使用，一段时间后可能恢复正常
     ***/

}