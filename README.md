**这是一个使用Netty实现的一个简易的IM系统。实现了单聊和群聊的功能。**

##### 单聊流程：

有两个成员：A和B

1. A 要和 B 聊天，首先 A 和 B 需要与服务器建立连接，然后进行一次登录流程，服务端保存用户标识和 TCP 连接的映射关系
2. A 发消息给 B，首先需要将带有 B 标识的消息数据包发送到服务器，然后服务器从消息数据包中拿到 B 的标识，找到对应的 B 的连接，将消息发送给 B
3. 任意一方发消息给对方，如果对方不在线，需要将消息缓存，对方上线之后再发送

##### 群聊流程

有三个成员：A、B、C

1. A，B，C 依然会经历登录流程，服务端保存用户标识对应的 TCP 连接
2. A 发起群聊的时候，将 A，B，C 的标识发送至服务端，服务端拿到之后建立一个群聊 ID，然后把这个 ID 与 A，B，C 的标识绑定
3. 群聊里面任意一方在群里聊天的时候，将群聊 ID 发送至服务端，服务端拿到群聊 ID 之后，取出对应的用户标识，遍历用户标识对应的 TCP 连接，就可以将消息发送至每一个群聊成员

##### 自定义的通信协议

| 魔数  | 版本号 | 序列化算法 | 指令  | 数据长度 | 数据  |
| ----- | ------ | ---------- | ----- | -------- | ----- |
| 4字节 | 1字节  | 1字节      | 1字节 | 4字节    | N字节 |

1. 魔数借鉴了JVM虚拟机中Class文件的设计，作为一个校验标志
2. 版本号是扩展字段，预留字段，为了协议升级使用。类似于TCP协议中有一个字段标识是IPV4还是IPV6
3. 序列化算法标识如何把Java对象转换成二进制数据以及如何把二进制数据转换为Java对象，比如 Java 自带的序列化，json 等序列化方式。
4. 服务端或者客户端每收到一种指令都会有相应的处理逻辑，比如登录登出，创建群聊等等。这里，我们用一个字节来表示，最高支持256种指令，对于我们这个 IM 系统来说已经完全足够了。
5. 接下来的字段为数据部分的长度，占四个字节。
6. 最后一个部分为数据内容，每一种指令对应的数据是不一样的，比如登录的时候需要用户名密码，收消息的时候需要用户标识和具体消息内容等等。
