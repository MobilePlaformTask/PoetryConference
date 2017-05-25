package task;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

/**
 * Created by lenovo on 2017/5/23.
 */

public class compressImage {
    public static Bitmap compressImage(Bitmap image) {
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
        Log.d("baosqq",""+baos.size()+"");
        Log.d("baos",""+baos.toByteArray().length+"");
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());
        Log.d("baos",""+ isBm.toString().length()+"");
        // 把压缩后的数据baos存放到ByteArrayInputStream中
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);
        // 把ByteArrayInputStream数据生成图片
        return bitmap;
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        baos.reset();
//        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
//        int options = 100;
//        Log.d("baos1:",""+baos.size());
//        while ( baos.toByteArray().length / 1024>20) {    //循环判断如果压缩后图片是否大于50kb,大于继续压缩
//            // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
//            baos.reset();// 重置baos即清空baos
//            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
//            // 这里压缩options%，把压缩后的数据存放到baos中
//            options -= 5;// 每次都减少10
//            if (options <= 0) {
//                break;
//            }
//        }
//        Log.d("baosqq",""+baos.size()+"");
//        Log.d("baos",""+baos.toByteArray().length+"");
//        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中
//        Log.d("baos",""+ isBm.toString().length()+"");
//        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片
//
//        return bitmap;


    }
}
