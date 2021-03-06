package com.example.four.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.res.ResourcesCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.four.Activity.ListviewActivity;
import com.example.four.Activity.UpdateActivity;
import com.example.four.Bean.AddressDto;

import com.example.four.ItemHelper.CustomDialogLeft;
import com.example.four.ItemHelper.CustomDialogRight;
import com.example.four.ItemHelper.ItemTouchHelperListener;
import com.example.four.ItemHelper.OnDialogListener;
import com.example.four.ItemHelper.Variable;
import com.example.four.R;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder>
        //하진추가
        implements ItemTouchHelperListener, OnDialogListener {
    final static String TAG = "어드레스어뎁터";


    Context mContext = null;
    int layout = 0;
    LayoutInflater inflater = null;
    private ArrayList<AddressDto> mDataset;
    ///////////////////////////////////////-자기 아이피 챙기기-//////////////////////////////////////////////
    String urlAddr = "http://192.168.35.147:8080/pictures/";//자기 ip로 바꾸기 종찬                  //
//    String urlAddr = "http://172.30.1.27:8080/pictures/";//자기 ip로 바꾸기 애정                     //
//   String urlAddr = "http://222.106.89.206:8080/pictures/";//자기 ip로 바꾸기 이누                     //
//   String urlAddr = "http://192.168.0.105:8080/pictures/";//자기 ip로 바꾸기 보람                    //
//    String urlAddr = "http://192.168.1.5:8080/pictures/";//자기 ip로 바꾸기 하진                  //
///////////////////////////////////////-자기 아이피 챙기기-//////////////////////////////////////////////

    int pos = 0;

    String cal = null;

    public AddressAdapter(Context mContext, int layout, ArrayList<AddressDto> data) {
        this.mContext = mContext;
        this.layout = layout;
        this.mDataset = data;
        this.inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.listlayout, parent, false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Log.d(TAG, mDataset.get(position).getAddrImagePath());

        holder.addrTag.setText(mDataset.get(position).getAddrTag());
        holder.addrName.setText(mDataset.get(position).getAddrName());
        holder.addrAddr.setText(mDataset.get(position).getAddrAddr());
        holder.addrTel.setText(mDataset.get(position).getAddrTel());


        Glide.with(holder.addrProfile)
                .load(urlAddr + mDataset
                        .get(position).getAddrImagePath())
                .placeholder(R.drawable.noimg)
                .override(120, 120)
                .apply(new RequestOptions().circleCrop()).into(holder.addrProfile);//사진

//        Log.d(TAG, urlAddr + mDataset.get(position).getAddrImagePath());


        if (mDataset.get(position).getAddrTag().equals("병원") && mDataset.get(position).getAddrLike().equals("0")) {
            holder.addrTagImg.setImageResource(R.drawable.tag_hospital);
            holder.addrLike.setImageResource(R.drawable.unlike);
        } else if (mDataset.get(position).getAddrTag().equals("유치원") && mDataset.get(position).getAddrLike().equals("0")) {
            holder.addrTagImg.setImageResource(R.drawable.tag_kindergaden);
            holder.addrLike.setImageResource(R.drawable.unlike);
        } else if (mDataset.get(position).getAddrTag().equals("키즈카페") && mDataset.get(position).getAddrLike().equals("0")) {
            holder.addrTagImg.setImageResource(R.drawable.tag_cafe);
            holder.addrLike.setImageResource(R.drawable.unlike);
        } else if (mDataset.get(position).getAddrTag().equals("기타") && mDataset.get(position).getAddrLike().equals("0")) {
            holder.addrTagImg.setImageResource(R.drawable.tag_user);
            holder.addrLike.setImageResource(R.drawable.unlike);
        }else if(mDataset.get(position).getAddrTag().equals("병원") && mDataset.get(position).getAddrLike().equals("1")) {
            holder.addrTagImg.setImageResource(R.drawable.tag_hospital);
            holder.addrLike.setImageResource(R.drawable.like);
        } else if (mDataset.get(position).getAddrTag().equals("유치원") && mDataset.get(position).getAddrLike().equals("1")) {
            holder.addrTagImg.setImageResource(R.drawable.tag_kindergaden);
            holder.addrLike.setImageResource(R.drawable.like);
        } else if (mDataset.get(position).getAddrTag().equals("키즈카페") && mDataset.get(position).getAddrLike().equals("1")) {
            holder.addrTagImg.setImageResource(R.drawable.tag_cafe);
            holder.addrLike.setImageResource(R.drawable.like);
        } else if (mDataset.get(position).getAddrTag().equals("기타") && mDataset.get(position).getAddrLike().equals("1")) {
            holder.addrTagImg.setImageResource(R.drawable.tag_user);
            holder.addrLike.setImageResource(R.drawable.like);
        }

        holder.onBind(mDataset.get(position));
    }


    //인터페이스 선언
    public interface OnItemClickListener {
        void onItemClick(View v, int position);

    }

    private OnItemClickListener mListener = null;

    //메인에서 사용할 클릭메서드 선언
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mListener = listener;
    }

    public interface OnItemLongClickListener {
        void onItemLongClick(View v, int position);
    }

    private OnItemLongClickListener mLongListener = null;

    public void setOnItemLongClickListener(OnItemLongClickListener longListener) {
        this.mLongListener = longListener;
    }


    @Override
    public int getItemCount() {
        return mDataset.size();
    }


    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean onItemMove(int from_position, int to_position) {
        //이동할 객체 저장
        AddressDto addressDto = mDataset.get(from_position);
        //이동할 객체 삭제
        mDataset.remove(from_position);

        //이동하고 싶은 position에 추가
        mDataset.add(to_position, addressDto);
        //Adapter에 데이터 이동알림
        notifyItemMoved(from_position, to_position);

        return true;
    }

    @Override
    public void onItemSwipe(int position) {
        Log.v(TAG, "onItemSwipe");


    }

    //왼쪽 버튼 누르면 수정할 다이얼로그 띄우기
    @Override
    public void onLeftClick(int position, RecyclerView.ViewHolder viewHolder) {
        CustomDialogLeft dialog = new CustomDialogLeft(mContext, position, mDataset.get(position));//수정 버튼 클릭시 다이얼로그 생성
        DisplayMetrics dm = mContext.getApplicationContext().getResources().getDisplayMetrics();// 화면 사이즈 구하기
        int width = dm.widthPixels;
        int height = dm.heightPixels;
        WindowManager.LayoutParams wm = dialog.getWindow().getAttributes();//다이얼로그 사이즈 세팅
        wm.copyFrom(dialog.getWindow().getAttributes());
        wm.width = (int) (width * 0.8);
        wm.height = height / 5;
        Variable.publicaddrno = mDataset.get(position).getAddrNo();
        Variable.whereaddrlike = mDataset.get(position).getAddrLike();
        dialog.setDialogListener(this);//다이얼로그 Listener 세팅
        dialog.show();//다이얼로그 띄우기

    }


    //   }
    //오른쪽 버튼 누르면 아이템 삭제
    @Override
    public void onRightClick(int position, RecyclerView.ViewHolder viewHolder) {
        new AlertDialog.Builder(mContext) // 저장 후 입력 완료 되었다는 Alert 창, 확인 클릭 시 리스트 창으로 이동

                .setTitle("")
                .setMessage("" + mDataset.get(position).getAddrTel())
                .setCancelable(false)//아무데나 눌렀을때 안꺼지게 하는거 (버튼을 통해서만 닫게)
                .setPositiveButton("취소", null)
                .setNegativeButton("전화", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mDataset.get(position).getAddrTel()));
                        mContext.startActivity(intent);

                    }
                })
                .show();

    }

    @Override
    public void onFinish(int position, AddressDto addressDto) {
        mDataset.set(position, addressDto);
        notifyItemChanged(position);

    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        final static String TAG1 = "MyViewHolder";
        // each data item is just a string in this case
        public TextView addrTag;
        public TextView addrName;
        public TextView addrTel;
        public TextView addrAddr;
        public ImageView addrProfile;
        public ImageView addrTagImg;
        //추가
        public ImageView addrLike;

        MyViewHolder(View v) {

            super(v);
            addrAddr = v.findViewById(R.id.tv_address_listlayout);
            addrTag = v.findViewById(R.id.tv_tag_listlayout);
            addrName = v.findViewById(R.id.tv_name_listlayout);
            addrTel = v.findViewById(R.id.tv_tel_listlayout);
            addrProfile = v.findViewById(R.id.iv_profile_listlayout);
            addrTagImg = v.findViewById(R.id.iv_tag_listlayout);
            addrLike = v.findViewById(R.id.iv_like_listlayout);
            Log.v(TAG1, "MyViewHolder");
            // 뷰홀더에서만 리스트 포지션값을 불러올 수 있음.

            //-----------------Click Event---------------------
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();//어뎁터 포지션값
                    // 뷰홀더에서 사라지면 NO_POSITION 을 리턴
                    if (position != RecyclerView.NO_POSITION) {
                        if (mListener != null) {
                            mListener.onItemClick(view, position);
                        }
                    }
                }
            });
            //-----------------Click Event---------------------
            pos = getAdapterPosition();

        }

        public void onBind(AddressDto addressDto) {
            addrTag.setText(addressDto.getAddrTag());
            addrName.setText(addressDto.getAddrName());
            addrTel.setText(addressDto.getAddrTel());
            addrAddr.setText(addressDto.getAddrAddr());
        }


    }
}//------------------------------