# Spring Simple Lab
***

## 使用技術


1. **[Spring Core](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html)**
2. **[Spring Web MVC](https://docs.spring.io/spring/docs/current/spring-framework-reference/web.html)**
3. **[Spring Data JPA](https://docs.spring.io/spring-data/jpa/docs/2.1.3.RELEASE/reference/html/)**
4. **[Spring AOP](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/core.html#aop)**
5. **[Spring Cache Caffeine](https://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-caching.html#boot-features-caching-provider-caffeine)**
6. Java 8
	+ **[Lambda](https://magiclen.org/java-8-lambda/)**
	+ **[Optional API](http://blog.tonycube.com/2015/10/java-java8-4-optional.html)**
	+ **[Stream API](https://www.ibm.com/developerworks/cn/java/j-lo-java8streamapi/index.html)**
	+ **[CompletableFuture](https://popcornylu.gitbooks.io/java_multithread/content/async/cfuture.html)**
7. **[Spring Boot 2](https://docs.spring.io/spring-boot/docs/2.1.1.RELEASE/reference/htmlsingle/)**
8. **[Spring Transaction](https://docs.spring.io/spring-framework/docs/current/spring-framework-reference/data-access.html)**
9. **[BCrypt](https://ithelp.ithome.com.tw/articles/10196477)**
10. **[Spring Security](https://docs.spring.io/spring-security/site/docs/5.1.2.RELEASE/reference/htmlsingle/)**
11. **[JWT(JSON Web Token)](https://yami.io/jwt/)**
12. **[ECDSA](https://hk.saowen.com/a/34562609077f8b590a14b571c1cfa9412bf4277eda6f027d979a1e21bafd8e36)**
13. **[Spring Schedule](https://www.baeldung.com/spring-scheduled-tasks)**
14. **[Cron Expressions](https://www.baeldung.com/cron-expressions)**
***
## RESTful API 架構


![](https://i.imgur.com/fw4MI8D.png)
***
## 專案結構
***

## 快速啟動
***

## 開始前動作


### Step 1：從 master 分支切出自己的開發分支

```shell
$ git checkout -b <your name> master
```

### Step 2：開始開發
***
## LAB 目標
***

### 1. 按照以下 API 規格進行開發

>**NOTE：** 不需要快取

#### 1-1. API：查詢所有書籍

##### 基本資訊

|                   | Content         |
| ----------------- | --------------- |
| **URL**           | /book           |
| **HTTP Method**   | GET             | 
| **Desc**          | 查詢所有的 book   |

##### 回覆（Response）訊息格式

| Column              | Type      | Remarks             |
| ------------------- | -------   | ------------------- |
| Repeating Structure |         |                     |
| name                | String    | 書名                 |
| author              | String    | 作者                 |
| publicationDate     | String    | 出版日期 yyyy-mm-dd   |

#### 1-2. API：以書名查詢書籍

##### 基本資訊

|                   | Content           |
| ----------------- | ----------------- |
| **URL**           | /book/{name}      |
| **HTTP Method**   | GET               | 
| **Desc**          | 以 name 撈取 book  |

##### 回覆（Response）訊息格式

| Column            | Type      | Remarks             |
| ----------------- | -------   | ------------------- |
| name              | String    | 書名                 |
| author            | String    | 作者                 |
| publicationDate   | String    | 出版日期 yyyy-mm-dd   |

#### 1-3. API：新增書籍

##### 基本資訊

|                   | Content                                                       |
| ----------------- | ------------------------------------------------------------- |
| **URL**           | /book                                                         |
| **HTTP Method**   | POST                                                          | 
| **Desc**          | 1. 將請求（Request）的書籍資料寫入 Table「BOOK」2. 回傳已新增的書籍資料 |

##### 請求（Request）訊息格式

| Column            | Type      | Remarks            |
| ----------------- | -------   | -------------------|
| name              | String    | 書名                |
| author            | String    | 作者                |
| publicationDate   | String    | 出版日期 yyyymmdd   |

##### 回覆（Response）訊息格式

| Column            | Type      | Remarks             |
| ----------------- | -------   | ------------------- |
| name              | String    | 書名                 |
| author            | String    | 作者                 |
| publicationDate   | String    | 出版日期 yyyy-mm-dd   |

#### 1-4. API：刪除書籍

##### 基本資訊

|                   | Content           |
| ----------------- | ----------------- |
| **URL**           | /book/{name}      |
| **HTTP Method**   | DELETE            | 
| **Desc**          | 以 name 來刪除書籍  |

#### 1-5. API：更新書籍

##### 基本資訊

|                   | Content                                                                                           |
| ----------------- | -----------------------------------------------------------------------------------------------   |
| **URL**           | /book                                                                                             |
| **HTTP Method**   | PUT                                                                                               | 
| **Desc**          | 1. 將請求（Request）的書籍資料更新對應書名的書籍資料 2. 若請求欄位為 null，則不更新此欄位 3. 回傳已更新的書籍資料   |

##### 請求（Request）訊息格式

| Column            | Type      | Remarks            |
| ----------------- | -------   | -------------------|
| name              | String    | 書名                |
| author            | String    | 作者                |
| publicationDate   | String    | 出版日期 yyyymmdd   |

##### 回覆（Response）訊息格式

| Column            | Type      | Remarks             |
| ----------------- | -------   | ------------------- |
| name              | String    | 書名                 |
| author            | String    | 作者                 |
| publicationDate   | String    | 出版日期 yyyy-mm-dd   |

***
### 2. 針對資源 Book 新增 Cache

#### 2-1. Cache 時間為 1 分鐘
#### 2-2. 查詢時若有Cache則回傳Cache，若沒Cache則重新查詢後更新Cache並回傳
#### 2-3. 變更時更新Cache
#### 2-4. 刪除時清除Cache
***

### 3. Transaction 控制

#### 3-1. 設計一API：同時新增使用者、書籍，並以 Transaction 控制若有任何例外發生，則 rollback
#### 3-2. 設計一API：同時更新使用者、書籍，並以 Transaction 控制若有任何例外發生也不影響已經執行的 DB 操作
***

### 4. AOP (Aspect-Oriented Programming)

>**NOTE：** 使用 AOP 的方式實作

#### 4-1. 在進到 Controller method 前印出「{method 名稱} 開始執行」、輸入的參數
#### 4-2. 在 Controller method 之後印出「{method 名稱} 執行結束」
#### 4-3. 印出執行 Controller method 所花費的時間
***

### 5. Security

>**NOTE：** 5-1 使用 Spring Security 實作

#### 5-1. 將 Book API 限制為角色「USER」才能存取
#### 5-2. 實作一個方法將一個字串做Base64編碼
#### 5-3. 實作一個方法將byte[]做Base64編碼
#### 5-4. 實作一個方法將Base64字串做Base64解碼，並印出byte[]的長度
#### 5-5. 調查並分享目前企業應用上常用的加解密有哪些種類、哪些演算法？
***

### 6. Scheduler

>**NOTE：** 使用 Spring Schedule 的方式實作

#### 6-1. 新增一排程每10秒鐘統計一次當前DB裡有多少本書，並在console log印出
***