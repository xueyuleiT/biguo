package com.example.zenghui.overyearspaper.Fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.zenghui.overyearspaper.Adapter.PhotoAdapter;
import com.example.zenghui.overyearspapers.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import me.iwf.photopicker.PhotoPickerActivity;
import me.iwf.photopicker.utils.PhotoPickerIntent;

/**
 * Created by zenghui on 16/1/10.
 */
public class Fragment_three extends BaseFragment implements View.OnClickListener {
    private View rootView;

    private boolean isInit = false;
    private ImageView myHeadIv;
    PhotoAdapter photoAdapter;
    ArrayList<String> selectedPhotos = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (rootView == null) {
            rootView = inflater.inflate(R.layout.fragment_three, null);
        }
        return rootView;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        photoAdapter = new PhotoAdapter(getContext(), selectedPhotos);
        myHeadIv = (ImageView) rootView.findViewById(R.id.my_head_iv);
        myHeadIv.setOnClickListener(this);
    }

    public void init() {

        if (!isInit) {
            isInit = true;
            Log.d("", "init ===========>");
        }

    }
    public final static int REQUEST_CODE = 1;
    public static final int RESULT_OK = -1;
    @Override
    public void onClick(View v) {
        if(v.getId()==myHeadIv.getId()){
            PhotoPickerIntent intent = new PhotoPickerIntent(getContext());
            intent.setPhotoCount(1);
            intent.setShowCamera(true);
            startActivityForResult(intent, REQUEST_CODE);
        }
    }

    public  void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        List<String> photos = null;
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            if (data != null) {
                photos = data.getStringArrayListExtra(PhotoPickerActivity.KEY_SELECTED_PHOTOS);
//                Bitmap bitmap = BitmapFactory.decodeFile(photos.get(0));
//                myHeadIv.setImageBitmap(bitmap);
                Glide.with(getContext())
                        .load(photos.get(0))
                        .centerCrop()
                        .thumbnail(0.1f)
                        .placeholder(R.drawable.ic_photo_black_48dp)
                        .error(R.drawable.ic_broken_image_black_48dp)
                        .into(myHeadIv);
                //保存图片到本地

            }
        }
    }

    public void saveMyBitmap(String bitName,Bitmap mBitmap) {
        File f = new File("/sdcard/" + bitName + ".png");
        try {
            f.createNewFile();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            Log.e("xuqunxing", "在保存图片时出错：" + e.toString());
        }
        FileOutputStream fOut = null;
        try {
            fOut = new FileOutputStream(f);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
