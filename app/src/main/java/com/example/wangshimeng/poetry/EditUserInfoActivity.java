package com.example.wangshimeng.poetry;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.SaveCallback;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import task.StringUtil;
import view.DrawCircleView;
import view.SelectDialog;

public class EditUserInfoActivity extends AppCompatActivity {
    EditText edtEditSign, edtEditUserName;
    ImageView imgUserInfoPhoto;
    private Uri uri;
    AVFile photo;
    Bitmap b;
    private String mCurrentPhotoPath;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private static final String LOG_TAG = "HelloCamera";
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    MyLeanCloudApp myLeanCloudApp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_info);
        edtEditSign = (EditText) findViewById(R.id.edtEditSign);
        edtEditUserName = (EditText) findViewById(R.id.edtEditUserName);
        imgUserInfoPhoto = (ImageView) findViewById(R.id.imgUserInfoPhoto);
        //设置值
        edtEditUserName.setText(AVUser.getCurrentUser().getString("username"));
        edtEditSign.setText(AVUser.getCurrentUser().getString("signature"));
        b = null;
        photo = null;
        myLeanCloudApp= (MyLeanCloudApp) this.getApplication();
        imgUserInfoPhoto.setImageBitmap(DrawCircleView.drawCircleView01(myLeanCloudApp.getBitmap()));

//        AVFile file = AVUser.getCurrentUser().getAVFile("photo");
////        AVFile file=myLeanCloudApp.getFile();
//        if (file == null) {
//            b = BitmapFactory.decodeResource(getResources(), R.drawable.touxiang);
//            compressImage com=new compressImage();
//            b= com.compressImage(b);
//            imgUserInfoPhoto.setImageBitmap(DrawCircleView.drawCircleView01(b));
//        } else {
//            file.getThumbnailUrl(true, 100, 100);
//            file.getDataInBackground(new GetDataCallback() {
//                @Override
//                public void done(byte[] bytes, AVException e) {
//                    // bytes 就是文件的数据流
//                    b = getPicFromBytes(bytes);
//                    compressImage com=new compressImage();
//                    b= com.compressImage(b);
//                    imgUserInfoPhoto.setImageBitmap(DrawCircleView.drawCircleView01(b));
//                }
//            }, new ProgressCallback() {
//                @Override
//                public void done(Integer integer) {
//                    // 下载进度数据，integer 介于 0 和 100。
//                }
//            });
//        }


        // Bitmap b = BitmapFactory.decodeResource(getResources(),R.drawable.touxiang);
        // b = BitmapFactory.decodeResource(getResources(), R.drawable.touxiang);


    }


    //将字节数组转换为ImageView可调用的Bitmap对象
    public static Bitmap getPicFromBytes(byte[] bytes) {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
    }

    //保存用户信息
    public void saveUserInfo(View view) {

        AVUser.getCurrentUser().put("username", edtEditUserName.getText().toString());
        AVUser.getCurrentUser().put("signature", edtEditSign.getText().toString());

//        AVUser.getCurrentUser().put("photo", photo);
//        myLeanCloudApp= (MyLeanCloudApp) this.getApplication();
        AVUser.getCurrentUser().put("photo", photo);
        AVUser.getCurrentUser().saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    myLeanCloudApp.setBitmap(b);

//                    Intent intent = new Intent();
//                    intent.setClass(EditUserInfoActivity.this, HomeActivity.class);
//                    EditUserInfoActivity.this.startActivity(intent);
//                    finish();
                    onBackPressed();
                }
            }
        });

    }

    //返回用户中心
    public void returnToCenter(View view) {
//        Intent intent = new Intent();
//        intent.setClass(this, UserCenterActivity.class);
//        this.startActivity(intent);
//        finish();
        onBackPressed();
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
    public void changePhoto(View view) {
        List<String> names = new ArrayList<>();
        names.add("拍照");
        names.add("相册");
        showDialog(new SelectDialog.SelectDialogListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0: // 直接调起相机

                        dispatchTakePictureIntent();

//                        String sdPath= Environment.getExternalStorageDirectory().getPath();
//                        String fileName=sdPath+"/myphoto/"+System.currentTimeMillis()+".jpg";
//                        uri= Uri.fromFile(new File(fileName));

                        // 利用系统自带的相机应用:拍照
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        uri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE);
//                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                        startActivityForResult(intent, 1);



                        // create a file to save the image

                        // 此处这句intent的值设置关系到后面的onActivityResult中会进入那个分支，即关系到data是否为null，如果此处指定，则后来的data为null
                        // set the image file name

//                        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
////                        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
//                        startActivityForResult(intent,1);
//                        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(intent, 1);

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
                        Intent intent = new Intent(Intent.ACTION_PICK,
                                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 2);
                        break;
                    default:
                        break;
                }

            }
        }, names);
    }

    /**
     * Create a File for saving an image or video
     */
    private static File getOutputMediaFile(int type) {
        // To be safe, you should check that the SDCard is mounted
        // using Environment.getExternalStorageState() before doing this.

        File mediaStorageDir = null;
        try {
            // This location works best if you want the created images to be
            // shared
            // between applications and persist after your app has been
            // uninstalled.
            mediaStorageDir = new File(
                    Environment
                            .getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),
                    "MyCameraApp");

            Log.d(LOG_TAG, "Successfully created mediaStorageDir: "
                    + mediaStorageDir);

        } catch (Exception e) {
            e.printStackTrace();
            Log.d(LOG_TAG, "Error in Creating mediaStorageDir: "
                    + mediaStorageDir);
        }

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists()) {
            if (!mediaStorageDir.mkdirs()) {
                // 在SD卡上创建文件夹需要权限：
                // <uses-permission
                // android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
                Log.d(LOG_TAG,
                        "failed to create directory, check if you have the WRITE_EXTERNAL_STORAGE permission");
                return null;
            }
        }

        // Create a media file name
        File mediaFile;

        mediaFile = new File(mediaStorageDir.getPath() + File.separator
                + "IMG_" + ".jpg");


        return mediaFile;
    }


    /**
     * Create a file Uri for saving an image or video
     */
    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }



    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Ensure that there's a camera activity to handle the intent
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.wangshimeng.poetry",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, 1);
            }
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if (RESULT_OK == resultCode) {
//                    Log.d(LOG_TAG, "RESULT_OK");
//
//                        Log.d(LOG_TAG,
//                                "data IS null, file saved on target position.");
//                        // If there is no thumbnail image data, the image
//                        // will have been stored in the target output URI.
//
//                        // Resize the full image to fit in out image view.
//                        int width = imgUserInfoPhoto.getWidth();
//                        int height = imgUserInfoPhoto.getHeight();
//
//                        BitmapFactory.Options factoryOptions = new BitmapFactory.Options();
//
//                        factoryOptions.inJustDecodeBounds = true;
//                        BitmapFactory.decodeFile(uri.getPath(), factoryOptions);
//
//                        int imageWidth = factoryOptions.outWidth;
//                        int imageHeight = factoryOptions.outHeight;
//
//                        // Determine how much to scale down the image
//                        int scaleFactor = Math.min(imageWidth / width, imageHeight
//                                / height);
//
//                        // Decode the image file into a Bitmap sized to fill the
//                        // View
//                        factoryOptions.inJustDecodeBounds = false;
//                        factoryOptions.inSampleSize = scaleFactor;
//                        factoryOptions.inPurgeable = true;
//
//                        Bitmap bitmap = BitmapFactory.decodeFile(uri.getPath(),
//                                factoryOptions);
                galleryAddPic();
                setPic();

//                System.out.println(Environment.getExternalStorageDirectory() + uri.getPath());
//                Toast.makeText(EditUserInfoActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
                String photoPath = mCurrentPhotoPath;
                System.out.println("photoPath"+photoPath);
                try {
                    photo = AVFile.withAbsoluteLocalPath("LeanCloud"+AVUser.getCurrentUser().getUsername()+".png", photoPath);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
                photo.addMetaData("width", 100);
                photo.addMetaData("height", 100);
                photo.saveInBackground(new SaveCallback() {
                    @Override
                    public void done(AVException e) {
                        System.out.println(photo.getUrl() + "");
                    }
                });


//                try {
//                    b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//
//                    Bitmap b1= compressImage.compressImage(b);
//                    imgUserInfoPhoto.setImageBitmap(DrawCircleView.drawCircleView01(b1));
//                    String photoPath = StringUtil.getRealPathFromURI(getApplicationContext(), uri);
//
//                    System.out.println(uri.toString() + "uri");
//                    System.out.println("1:" + photoPath);
////                    myLeanCloudApp= (MyLeanCloudApp) this.getApplication();
////                    myLeanCloudApp.setFile(AVFile.withAbsoluteLocalPath("LeanCloud.png", photoPath));
//
//                    photo = AVFile.withAbsoluteLocalPath("LeanCloud.png", photoPath);
//                    AVFile.withAbsoluteLocalPath("LeanCloud.png", photoPath).saveInBackground(new SaveCallback() {
//                        @Override
//                        public void done(AVException e) {
//                            System.out.println(photo.getUrl() + "");
////                            System.out.println(myLeanCloudApp.getFile().getUrl().toString() + "app");
//                        }
//                    });
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
            } else if (resultCode == RESULT_CANCELED) {
                // User cancelled the image capture
            } else {
                // Image capture failed, advise user
            }

//            uri = data.getData();
            //  imgUserInfoPhoto.setImageURI(uri);
//            Toast.makeText(EditUserInfoActivity.this,uri.toString(),Toast.LENGTH_SHORT).show();
            //   System.out.print(Environment.getExternalStorageDirectory() + uri.getPath());
        } else {
            if (RESULT_OK == resultCode) {
                uri = data.getData();
//                imgUserInfoPhoto.setImageURI(uri);
                try {
//                    b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
//                    compressImage com=new compressImage();
//                    b= com.compressImage(b);
//                    imgUserInfoPhoto.setImageBitmap(DrawCircleView.drawCircleView01(b));

                    System.out.println(Environment.getExternalStorageDirectory() + uri.getPath());
                    Toast.makeText(EditUserInfoActivity.this, uri.toString(), Toast.LENGTH_SHORT).show();
                    String photoPath = StringUtil.getRealPathFromURI(getApplicationContext(), uri);

                    mCurrentPhotoPath=photoPath;
                    setPic();

                    System.out.println(photoPath);
                    photo = AVFile.withAbsoluteLocalPath("LeanCloud"+AVUser.getCurrentUser().getUsername()+".png", photoPath);
                    photo.addMetaData("width", 100);
                    photo.addMetaData("height", 100);
                    photo.saveInBackground(new SaveCallback() {
                        @Override
                        public void done(AVException e) {
                            System.out.println(photo.getUrl() + "");
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }
    }






    private void galleryAddPic() {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(mCurrentPhotoPath);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        this.sendBroadcast(mediaScanIntent);
    }

    private void setPic() {
        // Get the dimensions of the View
        int targetW = imgUserInfoPhoto.getWidth();
        int targetH = imgUserInfoPhoto.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        b = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);
        imgUserInfoPhoto.setImageBitmap(DrawCircleView.drawCircleView01(b));



//        imgUserInfoPhoto.setImageBitmap(bitmap);
    }

    private static Bitmap compress(Bitmap image) {

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        while (baos.toByteArray().length / 1024 > 100) {
            // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            baos.reset();// 重置baos即清空baos
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 这里压缩options%，把压缩后的数据存放到baos中
            options -= 10;// 每次都减少10
            if (options <= 0) {
                break;
            }
        }
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        // 把ByteArrayInputStream数据生成图片
        return bitmap;
    }

}
