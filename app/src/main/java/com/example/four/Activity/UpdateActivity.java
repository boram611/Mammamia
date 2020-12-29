package com.example.four.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.four.NetworkTask.NetworkTask;
import com.example.four.R;

import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class UpdateActivity extends Activity {

    String imagePath  = null;
    String imageName  = null;
    private String img_path = new String();
    private Bitmap image_bitmap_copy = null;
    private Bitmap image_bitmap = null;
    private final int REQ_CODE_SELECT_IMAGE = 100;

    String tagName, addrName, addrTel, addrDetail;
    final static String TAG = "업데이트액티비티";
    //addr1 추가
    String tag1, name1, tel1, detail1, addr1;
    int num;
    EditText tag, name, tel, detail, addr;

    ImageView profileImage;

    String urlAddr = null;
    String urlIp = null;

    Button okbtn;
    Button backbtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);


        Intent intent = getIntent();

        urlIp = intent.getStringExtra("urlIp");

        tag1 = intent.getStringExtra("addrTag");
        name1 = intent.getStringExtra("addrName");
        tel1 = intent.getStringExtra("addrTel");
        addr1 = intent.getStringExtra("addrAddr");
        detail1 = intent.getStringExtra("addrDetail");

        imagePath = intent.getStringExtra("addrImagePath");

        num = intent.getIntExtra("addrNo", 0);


        urlAddr = "http://" + urlIp + ":8080/test/mammamiaUpdate.jsp?";


        profileImage = findViewById(R.id.iv_profile_update);
        tag = findViewById(R.id.et_tagname_update);
        name = findViewById(R.id.et_name_update);
        tel = findViewById(R.id.et_tel_update);
        addr = findViewById(R.id.et_addr_update);
        detail = findViewById(R.id.et_detail_update);
        okbtn = findViewById(R.id.btn_ok_update);
        backbtn = findViewById(R.id.btn_back_update);


        tag.setText(tag1);
        name.setText(name1);
        tel.setText(tel1);
        addr.setText(addr1);
        detail.setText(detail1);

            Glide.with(UpdateActivity.this).load(urlAddr + imagePath).override(300, 300).placeholder(R.drawable.shape_circle).apply(new RequestOptions().circleCrop()).into(profileImage);


//        profileImage.setImageBitmap(BitmapFactory.decodeFile(imagePath));//가져온 경로를 imageView에 올리기

        //--------------------이미지 올리기---------------------------
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);

            }
        });


        okbtn.setOnClickListener(onClickListener);
        backbtn.setOnClickListener(onClickListener1);


    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_ok_update:
                    String st_tag = tag.getText().toString();
                    String st_name = name.getText().toString();
                    String st_tel = tel.getText().toString();
                    String st_addr = addr.getText().toString();
                    String st_detail = detail.getText().toString();

                    if (imagePath != null) {

                        doMultiPartRequest();//사진 넣는 okHttp3 메소드}}}
                    }else{
                        imagePath = null;
                    }


                    urlAddr = urlAddr + "addrNo=" + num + "&addrName=" + st_name + "&addrTel=" + st_tel + "&addrAddr=" + st_addr + "&addrDetail=" + st_detail + "&addrTag=" + st_tag + "&addrImagePath=" + imageName;
                    connectUpdateData();
                    Log.d(TAG, urlAddr);

                    Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                    Toast.makeText(UpdateActivity.this, "수정이완료돼싸", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
            }

        }
    };


    View.OnClickListener onClickListener1 = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            onBackPressed();
        }
    };

    private void connectUpdateData() {
        try {
            NetworkTask updateworkTask = new NetworkTask(UpdateActivity.this, urlAddr, "update");
            updateworkTask.execute().get();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    //----------------------이미지 관련 메소드----------------------------------------------
    //
    //고종찬 = 바지사장
    //
    //---------------------------------------------------------------------------------
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        Toast.makeText(getBaseContext(), "resultCode : " + data, Toast.LENGTH_SHORT).show();

        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                try {
                    img_path = getImagePathToUri(data.getData()); //이미지의 URI를 얻어 경로값으로 반환.
                    Toast.makeText(getBaseContext(), "img_path : " + img_path, Toast.LENGTH_SHORT).show();
                    //이미지를 비트맵형식으로 반환
                    image_bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());

                    //사용자 단말기의 width , height 값 반환
                    int reWidth = (int) (getWindowManager().getDefaultDisplay().getWidth());
                    int reHeight = (int) (getWindowManager().getDefaultDisplay().getHeight());

                    //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
                    image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);
                    ImageView image = (ImageView) findViewById(R.id.iv_image_insert);  //이미지를 띄울 위젯 ID값
                    image.setImageBitmap(image_bitmap_copy);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }//end of onActivityResult()

    public String getImagePathToUri(Uri data) {
        //사용자가 선택한 이미지의 정보를 받아옴
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);
        Log.d("test", imgPath);//이미지 경로 확인해서 데이터 값 넘기기

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);
        Toast.makeText(UpdateActivity.this, "이미지 이름 : " + imgName, Toast.LENGTH_SHORT).show();
        this.imageName = imgName;
//        this.imagePath = imgPath;

        return imgPath;
    }//end of getImagePathToUri()

    //파일 변환
    private void doMultiPartRequest() {

        File f = new File(img_path);

        DoActualRequest(f);
    }

    //서버 보내기
    private void DoActualRequest(File file) {
        OkHttpClient client = new OkHttpClient();
        String url = "http://" + urlIp + ":8080/test/multipartRequest.jsp";

        RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("image", file.getName(),
                        RequestBody.create(MediaType.parse("image/jpeg"), file))

                .build();

        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();

        try {
            Response response = client.newCall(request).execute();

        } catch (IOException e) {

            e.printStackTrace();
        }
    }


    //----------------------이미지 관련 메소드----------------------------------------------
    //
    //고종찬 = 바지사장
    //
    //---------------------------------------------------------------------------------





    //배경 터치 시 키보드 사라지게
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View view = getCurrentFocus();
        InputMethodManager imm;
        if (view != null && (ev.getAction() == MotionEvent.ACTION_UP || ev.getAction() == MotionEvent.ACTION_MOVE) && view instanceof EditText && !view.getClass().getName().startsWith("android.webkit.")) {
            int scrcoords[] = new int[2];
            view.getLocationOnScreen(scrcoords);
            float x = ev.getRawX() + view.getLeft() - scrcoords[0];
            float y = ev.getRawY() + view.getTop() - scrcoords[1];
            if (x < view.getLeft() || x > view.getRight() || y < view.getTop() || y > view.getBottom())
                ((InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow((this.getWindow().getDecorView().getApplicationWindowToken()), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}//-----------------