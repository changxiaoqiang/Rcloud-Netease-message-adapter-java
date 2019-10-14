# 融云、网易云信 IM 适配
此项目为示例代码，通过融云的服务端实时消息路由和网易云信的消息抄送服务简单的实现双方消息互通。

能够平滑的从网易迁移到融云，不用强制客户端升级，在迁移过程中，使新老客户端版本能够同时正常的互发消息。

## 测试方法

测试从网易云信消息抄送服务发送的的消息发送到融云
```
curl -X POST \
 'http://localhost:8081/index/' \
 -H "Content-Type: application/json" \
 -H "CurTime: 1440570500855" \
 -H "MD5: feec2cda404d7417b8b427e95748bb56" \
 -H "CheckSum: 001511b8435e0b28044ca50a78e8f983026c5e01" \
 -d '{"attach":"thisisattach","body":"hello","convType":"PERSON","eventType":"1","fromAccount":"111","fromClientType":"IOS","fromDeviceId":"thisisfromdeviceid","fromNick":"mike","msgTimestamp":"1441977355557","msgType":"TEXT","msgidClient":"1234567","msgidServer":"3456789","resendFlag":"0","to":"222"}' 

```

测试从融云消息路由服务发送的消息发送到网易云信

```
curl -X POST \
  'http://localhost:8081/index/?appKey=appKey&signTimestamp=1558583658&nonce=819344537&signature=74f479512b2dc0b299812cb86c293211793f5f27' \
  -H 'Accept: */*' \
  -H 'Cache-Control: no-cache' \
  -H 'Connection: keep-alive' \
  -H 'Content-Type: application/x-www-form-urlencoded' \
  -H 'Host: localhost:8081' \
  -H 'Postman-Token: 6ada2186-6f0e-4cae-9db0-ef0b62c16bda,ad1e34e0-c687-4c60-b99d-ecb104774767' \
  -H 'User-Agent: PostmanRuntime/7.13.0' \
  -H 'accept-encoding: gzip, deflate' \
  -H 'cache-control: no-cache' \
  -H 'content-length: 216' \
  -d 'fromUserId=2&toUserId=1&objectName=RC%3ATxtMsg&content=%7B%0A%20%20%22content%22%3A%22Hello%20world!%22%2C%0A%20%20%22extra%22%3A%22%22%0A%7D&channelType=PERSON&msgTimestamp=1408710653491&msgUID=596E-P5PG-4FS2-7OJK'
```

### 后续工作

实现了网易云信的部分的消息发送接口，暂时支持的消息类型：文本消息、图片消息、视频消息、位置消息、文件消息。

目前融云和网易的消息互通实现的会话有单聊和群聊，消息类型包括：文本消息、图片消息和位置消息。可以自己按照示例中的代码实现消息的适配。

PS：1、云信中的图片、音频和视频消息里面有文件 MD5 值的字段，验证了下，在服务端没有做验证，应该是在客户端做双端的验证，在消息适配中，可以酌情删除了，提高效率，当然需要确认客户端是否正常，包括文件大小等内容，不影响消息的发送。

2、图片消息，因为融云为了弱网环境下的体验，在消息类型中包含了缩略图，这部分暂时没有实现，从云信的图片链接中获取缩略图，暂时没支持缩略图。

3、融云的语音消息是在消息内容中以 base64 的方式包含语音信息；网易云信是以链接的方式附带在消息中，牵涉到将云信的语音文件转为 base64字符串和融云的消息转为文件并上传到文件服务器，这部分需要注意。

