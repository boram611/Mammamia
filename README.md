# Mammamia UserGuide

## 1.Build gradle에 추가를 한다.

This is a normal paragraph:

    dependencies {........

     //리사이클러뷰
        implementation 'com.android.support:recyclerview-v7:30.0.0'


    //Google Play Services 라이브러리 추가
        implementation 'com.google.android.gms:play-services-maps:17.0.0'
        implementation 'com.google.android.gms:play-services-location:17.1.0'

    //Google places 라이브러리 추가
        implementation 'noman.placesapi:placesAPI:1.1.3'

    //Google 거리재기 라이브러리 추가
        implementation 'com.google.maps.android:android-maps-utils:0.5'



    //사진 업로드 관련 라이브러리 추가
        implementation 'com.github.bumptech.glide:glide:4.11.0'
        implementation 'com.squareup.picasso:picasso:2.71828'
        annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
    }

end code block.

---

## 2.Tomcat에 연동할 jsp 파일을 폴더에 넣는다.

방법은 tomcat 라이브러리 폴더의 /webapps/ROOT/안에 넣으면 된다.

Link: [JSP파일 Link][jsp link]

[jsp link]: https://github.com/AndroidMammamia/MammamiaDocument/tree/main/test



---

## 3.Tomcat에 사진을 넣기 위해 pictures 폴더를 만든다.
방법은 tomcat 라이브러리 폴더의 /webapps/ROOT/안에 pictures폴더를 생성하면 된다.



---

## 4.MYSQL 라이브러리를 넣는다

방법은 connector를 받아 tomcat 라이브러리 폴더의 /lib 에 넣어주면 된다.

Link: [MYSQL Connector][my sql connector]

[my sql connector]: https://github.com/AndroidMammamia/MammamiaDocument/tree/main/databaseConnector

---

## 5.test 폴더 안에 jsp MYSQL 데이터 베이스의 주소를 수정하도록한다.
이를테면

This is a normal paragraph:

    String stSearch =  request.getParameter("addrName");

    String url_mysql = "jdbc:mysql://이부분에 데이터베이스주소를 수정하세요/MYSQL스키마이름?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";

    String id_mysql = "아이디";

    String pw_mysql = "암호";


end code block.

한글로 적혀있는 부분은 각자 환경에 맞게 채우면 된다.

---

## 6.SQL Table 형식은 다음과 같다.
|addrNo|addrName|addrTel|addrAddr|addrDetail|addrLike|addrTag|addrImagePath
|------|---|---|---|---|---|---|---|
|int(11),AI,PK|varchar(45)|varchar(45)|varchar(150)|varchar(45)|varchar(45),Default'0'|varchar(45)|varchar(100)|

---



