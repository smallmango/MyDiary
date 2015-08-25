package com.mango.mydiary.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.mango.mydiary.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/8/24 0024.
 */
public class AddPhoto extends Activity implements View.OnClickListener {

    private static final int TAKE_PHOTO = 1;
    private static final int CHOOSE_PHOTO = 2;
    private static final int SCALE=3;
    private String title; //日记标题
    private Uri imageUri; //图片路径
    private ImageView diaryImage;
    private Button addImage;
    private Button chooseImage;
    private Button takeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.add_photo);
        init(); //初始化控件
        getDiaryTitle();

    }

    private void getDiaryTitle() {
        Bundle bundle = getIntent().getExtras();
        title = bundle.getString("diaryTitle", "isnull");
        //Log.i("ms",title);
    }


    private void init() {
        diaryImage = (ImageView) findViewById(R.id.diaryImage);
        addImage = (Button) findViewById(R.id.addImage);
        chooseImage = (Button) findViewById(R.id.chooseImage);
        takeImage = (Button) findViewById(R.id.takeImage);
        takeImage.setOnClickListener(this);
        chooseImage.setOnClickListener(this);
        addImage.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.takeImage:
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); //调用系统相机
                Uri imageUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(),"image.jpg"));
                //Log.i("ms",imageUri+"");
                //指定照片保存路径（SD卡），image.jpg为一个临时文件，每次拍照后这个图片都会被替换
                intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
                //直接使用，没有缩小
                startActivityForResult(intent, TAKE_PHOTO);  //用户点击了从相机获取
                break;
            case R.id.chooseImage:
                Intent openAlbumIntent = new Intent(Intent.ACTION_GET_CONTENT); //指定相册的语句
                openAlbumIntent.setType("image/*"); //不能少
                startActivityForResult(openAlbumIntent, CHOOSE_PHOTO);
                break;
            /*case R.id.addImage:

                break;*/
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case TAKE_PHOTO:
                //Log.i("ms", "see it");
                if (resultCode == RESULT_OK) {
                    String status = Environment.getExternalStorageState();
                    if (status.equals(Environment.MEDIA_MOUNTED)) { //是否有SD卡

                        Bitmap bitmap = BitmapFactory.decodeFile(Environment.getExternalStorageDirectory() + "/image.jpg");

                        String imgName = createPhotoFileName(); //给图片设定一个名字,要唯一
                        //写一个方法将此文件保存到本应用下面啦
                        savePicture(imgName, bitmap);

                        if (bitmap != null) {
                            //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                            Bitmap smallBitmap = zoomBitmap(bitmap, bitmap.getWidth() / SCALE, bitmap.getHeight() / SCALE);

                            diaryImage.setImageBitmap(smallBitmap);
                        }
                        Toast.makeText(this, "已保存本应用的files文件夹下", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(this, "没有SD卡", Toast.LENGTH_LONG).show();
                    }
                }
                break;
            case CHOOSE_PHOTO:
                if (resultCode == RESULT_OK) {
                    ContentResolver resolver = getContentResolver();
                    //照片的原始资源地址
                    Uri originalUri = data.getData();
                    try {
                        //使用ContentProvider通过URI获取原始图片
                        Bitmap photo = MediaStore.Images.Media.getBitmap(resolver, originalUri);
                        if (photo != null) {
                            //为防止原始图片过大导致内存溢出，这里先缩小原图显示，然后释放原始Bitmap占用的内存
                            Bitmap smallBitmap = zoomBitmap(photo, photo.getWidth() / SCALE, photo.getHeight() / SCALE);
                            //释放原始图片占用的内存，防止out of memory异常发生
                            photo.recycle();
                            diaryImage.setImageBitmap(smallBitmap);
                        }
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
        /** 缩放Bitmap图片 **/

    public Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
        int w = bitmap.getWidth();
        int h = bitmap.getHeight();
        Matrix matrix = new Matrix();
        float scaleWidth = ((float) width / w);
        float scaleHeight = ((float) height / h);
        matrix.postScale(scaleWidth, scaleHeight);// 利用矩阵进行缩放不会造成内存溢出
        Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
        return newbmp;
    }


    /**Save image to the SD card**/
    public static void savePhotoToSDCard(String path, String photoName, Bitmap photoBitmap) {
        if (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED)) {
            File dir = new File(path);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            File photoFile = new File(path, photoName); //在指定路径下创建文件
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = new FileOutputStream(photoFile);
                if (photoBitmap != null) {
                    if (photoBitmap.compress(Bitmap.CompressFormat.PNG, 100,
                            fileOutputStream)) {
                        fileOutputStream.flush();
                    }
                }
            } catch (FileNotFoundException e) {
                photoFile.delete();
                e.printStackTrace();
            } catch (IOException e) {
                photoFile.delete();
                e.printStackTrace();
            } finally {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**创建图片不同的文件名**/
    private String createPhotoFileName() {
        String fileName = "";
        Date date = new Date(System.currentTimeMillis());  //系统当前时间
        SimpleDateFormat dateFormat = new SimpleDateFormat("'IMG'_yyyyMMdd_HHmmss");
        fileName = dateFormat.format(date) + ".jpg";
        return fileName;
    }



    /**保存图片到本应用下**/
    private void savePicture(String fileName,Bitmap bitmap) {

        FileOutputStream fos =null;
        try {//直接写入名称即可，没有会被自动创建；私有：只有本应用才能访问，重新内容写入会被覆盖
            fos = this.openFileOutput(fileName, Context.MODE_PRIVATE);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);// 把图片写入指定文件夹中
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(null != fos) {
                    fos.close();
                    fos = null;
                }
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

}
