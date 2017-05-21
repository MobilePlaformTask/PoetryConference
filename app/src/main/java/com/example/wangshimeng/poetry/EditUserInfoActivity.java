package com.example.wangshimeng.poetry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.GetDataCallback;
import com.avos.avoscloud.ProgressCallback;
import com.avos.avoscloud.SaveCallback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import task.StringUtil;
import view.DrawCircleView;
import view.SelectDialog;


public class EditUserInfoActivity extends AppCompatActivity {
    EditText edtEditSign,edtEditUserName;
    ImageView imgUserInfoPhoto;
    private Uri uri;
    AVFile photo;
    Bitmap b;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        edtEditSign= (EditText) findViewById(R.id.edtEditSign);
        edtEditUserName= (EditText) findViewById(R.id.edtEditUserName);
        imgUserInfoPhoto= (ImageView) findViewById(R.id.imgUserInfoPhoto);
        //设置值
        edtEditUserName.setText(AVUser.getCurrentUser().getString("username"));
        edtEditSign.setText(AVUser.getCurrentUser().getString("signature"));
        b=null;
        photo=null;

        AVFile file=AVUser.getCurrentUser().getAVFile("photo");
        file.getDataInBackground(new GetDataCallback() {
            @Override
            public void done(byte[] bytes, AVException e) {
                // bytes 就是文件的数据流
                b=getPicFromBytes(bytes);
                imgUserInfoPhoto.setImageBitmap(DrawCircleView.drawCircleView01(b));
            }
        }, new ProgressCallback() {
            @Override
            public void done(Integer integer) {
                // 下载进度数据，integer 介于 0 和 100。
            }
        });

       // Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.touxiang);
       // b = BitmapFactory.decodeResource(getResources(), R.drawable.touxiang);


    }


    //将字节数组转换为ImageView可调用的Bitmap对象
    public static Bitmap getPicFromBytes(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }
    //保存用户信息
    public void saveUserInfo(View view){

        AVUser.getCurrentUser().put("username",edtEditUserName.getText().toString());
        AVUser.getCurrentUser().put("signature",edtEditSign.getText().toString());

        AVUser.getCurrentUser().put("photo",photo);

        AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e==null){
                    Intent intent = new Intent();
                    intent.setClass(EditUserInfoActivity.this, UserCenterActivity.class);
                    EditUserInfoActivity.this.startActivity(intent);
                }
            }
        });

    }
    //返回用户中心
    public void returnToCenter(View view){
        Intent intent = new Intent();
        intent.setClass(this, UserCenterActivity.class);
        this.startActivity(intent);
    }
    private SelectDialog showDialog(SelectDialog.SelectDialogListener listener, List<String> names) {
        SelectDialog dialog = new SelectDialog(this, R.style
                .transparentFrameWindowStyle,
                listener, names);
        if (!this.isFinishing()) {
            dialog.show();
        }
        return dialog;
    }
    //更换用户头像
    //用户换头像
    public void changePhoto(View view){
        List<String> names = new ArrayList<>();
        names.add("拍照");
        names.add("相册");
        showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // 直接调起相机
                        /**
                         * 0.4.7 目前直接调起相机不支持裁剪，如果开启裁剪后不会返回图片，请注意，后续版本会解决
                         *
                         * 但是当前直接依赖的版本已经解决，考虑到版本改动很少，所以这次没有上传到远程仓库
                         *
                         * 如果实在有所需要，请直接下载源码引用。
                         */

//                        String sdPath= Environment.getExternalStorageDirectory().getPath();
//                        String fileName=sdPath+"/myphoto/"+System.currentTimeMillis()+".jpg";
//                        uri= Uri.fromFile(new File(fileName));
//
//                        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                        startActivityForResult(intent,1);
                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(intent, 1);

                        //打开选择,本次允许选择的数量
//                        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
//                        Intent intent = new Intent(WxDemoActivity.this, ImageGridActivity.class);
//                        intent.putExtra(ImageGridActivity.EXTRAS_TAKE_PICKERS,true); // 是否是直接打开相机
//                        startActivityForResult(intent, REQUEST_CODE_SELECT);
                        break;
                    case 1:
                        //打开选择,本次允许选择的数量
//                        ImagePicker.getInstance().setSelectLimit(maxImgCount - selImageList.size());
//                        Intent intent1 = new Intent(WxDemoActivity.this, ImageGridActivity.class);
//                        startActivityForResult(intent1, REQUEST_CODE_SELECT);
                        intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                        break;
                    default:
                        break;
                }

            }
        }, names);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==1){
            uri = data.getData();
            imgUserInfoPhoto.setImageURI(uri);
//            Toast.makeText(EditUserInfoActivity.this,uri.toString(),Toast.LENGTH_SHORT).show();
            System.out.print(Environment.getExternalStorageDirectory() + uri.getPath());
        }
        else{
            uri = data.getData();
            imgUserInfoPhoto.setImageURI(uri);
            try {
                Bitmap b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                imgUserInfoPhoto.setImageBitmap(DrawCircleView.drawCircleView01(b));
                System.out.println(Environment.getExternalStorageDirectory() + uri.getPath());
                Toast.makeText(EditUserInfoActivity.this,uri.toString(),Toast.LENGTH_SHORT).show();
                String photoPath = StringUtil.getRealPathFromURI(getApplicationContext(), uri);
                System.out.println(photoPath);
//                AVFile file = new AVFile("resume.txt",b.getBytes());
                photo = AVFile.withAbsoluteLocalPath("LeanCloud.png", photoPath);
                photo.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        System.out.println(photo.getUrl()+"");
                    }
                });

            } catch (IOException e) {
                e.printStackTrace();
            }



        }
    }



}
