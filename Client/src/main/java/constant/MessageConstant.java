package constant;

public interface MessageConstant {
    //    消息类型
    int GET = 0;
    int INFORM = 1;
    int FORWARD = 2;

    //    消息状态
    int NO = -1;
    int OK = 0;
    int CLIENT_ERROR = 1;
    int SEVER_ERROR = 2;

    //    发送接受者
    int NOT_LOGGED_IN_CLIENT = -1;
    int SERVER = -2;
    int ALL_USER = -3;

    //    消息大小
    int UNKNOWN = -1;
}
