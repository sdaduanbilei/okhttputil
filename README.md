### OkhttpUtils kotlin 版本


### 初始化 Application
```kotlin
val cookieJar = CookieJarImpl(PersistentCookieStore(applicationContext))
        val client = OkHttpClient.Builder()
                .cookieJar(cookieJar)
                .addInterceptor(ReceivedCookiesInterceptor(applicationContext))
                .addInterceptor(AddCookiesInterceptor(applicationContext))
                .addInterceptor(HeaderInterceptor())
                .build()
        OkHttpUtils().initClient(client,applicationContext)
```
```ReceivedCookiesInterceptor``` 接收服务器端Set-Cookie 并设置

```AddCookiesInterceptor``` 全局添加Cookie

```HeaderInterceptor``` 全局添加Header


```kotlin
class HeaderInterceptor :Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("City", "530102")
        builder.addHeader("Platform","Android/91")
        builder.addHeader("Authorization","ASDFADAFASDFE8802302")
        return chain.proceed(builder.build())

    }
}
```

### GET 请求
```kotlin
OkHttpUtils().get()
                .url("https://m.9ji.com/web/api/products/productCityDetail/v1")
                .param("ppid","58206")
                .tag(this)
                .build()
                .execute(object: JsonCallback<String>(){
                    override fun onError(call: Call, e: Exception, id: Int) {
                        Log.d(TAG,"onError" + e.localizedMessage)
                    }

                    override fun onSucc(response: Any, id: Int) {
                        Log.d(TAG,response.toString())
                        mText.text = response.toString()
                    }

                })
```

POST请求
```kotlin
val map = HashMap<String, String>()
        map["label"] = ""
        map["cityId"] = "530102"
        val file = File("/storage/emulated/0/9ji/1519456887211.jpg")
        OkHttpUtils().post()
                .url("https://m.baidu.com/app/3_0/UserHandler.ashx")
                .param("act", "UploadUserFace")
                .param("userid","54119")
                .params(map)
                .addFile("file","usericon,",file)
                .tag(this)
                .build()
                .execute(object : JsonCallback<String>() {
                    override fun onError(call: Call, e: Exception, id: Int) {
                        Log.d(TAG,e.toString())
                    }

                    override fun onSucc(response: Any, id: Int) {
                        Log.d(TAG,response.toString())
                    }
                })
```

### POST JSON
```kotlin
val json = JSONObject()
        json["status"] = "3"
        json["token"] = "7C08EA9A3203480C8D7523FAABD6ECC6"
        OkHttpUtils().postString()
                .url("https://moa.ch999.com/wapi/V2/User/ChangeRecipientStatus")
                .content(json.toString())
                .addHeader("Authorization","Basic 3389 7C08EA9A3203480C8D7523FAABD6ECC6")
                .mediaType(MediaType.parse("application/json; charset=utf-8")!!)
                .build()
                .execute(object: JsonCallback<String>(){
                    override fun onError(call: Call, e: Exception, id: Int) {
                        Log.d(TAG,e.toString())
                    }

                    override fun onSucc(response: Any, id: Int) {
                        Log.d(TAG,response.toString())
                    }

                })
```

### 自定义CallBack
```kotlin
abstract class JsonCallback<T> : GenericsCallback<T>(JsonGenericsSerializator()) {

    @Throws(Exception::class)
    override fun validateReponse(response: Response, id: Int): String {
        val json = JSON.parseObject(response.body()!!.string())
        if (json == null) {
            return "Response is not json data!!!"
        } else {
            val data = json.getString("data")
            if (json.containsKey("stats")) {
                val status = json.getIntValue("stats")
                val msg = json.getString("msg")
                if (status == 1) {
                    validateData = data
                } else {
                    return msg
                }
            } else {
                val code = json.getIntValue("code")
                val msg = json.getString("userMsg")
                if (code == 0) {
                    if (data.isNullOrEmpty()) {
                        validateData = json.toString()
                    } else {
                        validateData = data
                    }
                } else {
                    return msg
                }
            }
        }
        return ""
    }
}
```

### 文件下载
```kotlin
OkHttpUtils().get()
                .url("https://www.9ji.com/topic/Android/9ji.apk")
                .build()
                .execute(object : FileCallBack(Environment.getExternalStorageDirectory().absolutePath, Date().time.toString() + "_9ji.apk"){
                    override fun onError(call: Call, e: Exception, id: Int) {
                        Log.d("onError",e.toString())
                    }

                    override fun inProgress(progress: Float, total: Long, id: Int) {
                        super.inProgress(progress, total, id)
                        Log.d("inProgress", progress.toString() + "==" + total)
                    }

                    override fun onSucc(response: Any, id: Int) {
                        val file = response as File
                        Log.d("file",file.absolutePath)
                    }
                })
```

###同步请求
```kotlin
val response = OkHttpUtils()
                .get()
                .url("")
                .tag(this)
                .build()
                .execute()
```

###取消单个请求
```kotlin
val call = OkHttpUtils().get()
                .url("")
                .build()
        call.cancel()
```

###更加tag 取消请求

目前对于支持的方法都添加了最后一个参数Object tag，取消则通过OkHttpUtils.cancelTag(tag)执行。
例如：在Activity中，当Activity销毁取消请求：
```kotlin
OkHttpUtils()
                .get()
                .url("")
                .tag(this)
                .build()

        OkHttpUtils().cancelTag(this)
```


