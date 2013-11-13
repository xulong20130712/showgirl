package zzuli.edu.cn.lish13;

import java.util.ArrayList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

public class ImageResource {
	
	private List<Bitmap> bitmapResource;
	private static ImageResource imageResource;
	
	private static int fore[];
	private static int back[];
	
	private ImageResource(){
		bitmapResource = new ArrayList<Bitmap>();
		fore = new int[]{R.drawable.a0, R.drawable.a1, R.drawable.a2,
				R.drawable.a3, R.drawable.a4, R.drawable.a5,
				R.drawable.a6, R.drawable.a7, R.drawable.a8,
				R.drawable.a9, R.drawable.a910, R.drawable.a911,
				R.drawable.a912, R.drawable.a913, R.drawable.a914};
		back = new int[]{R.drawable.b0, R.drawable.b1, R.drawable.b2,
				R.drawable.b3, R.drawable.b4, R.drawable.b5,
				R.drawable.b6, R.drawable.b7, R.drawable.b8,
				R.drawable.b9, R.drawable.b910, R.drawable.b911,
				R.drawable.b912, R.drawable.b913, R.drawable.b914};
	}
	
	public static ImageResource getImageResource(){
		if(imageResource==null){
			imageResource = new ImageResource();
		}
		return imageResource;
	}
	
	public Bitmap getBackBitmap(Resources resources, int num){//构造方法
		
		Bitmap bitmap = BitmapFactory.decodeResource(resources, back[num]);
		
		return bitmap;
	}
	
	public Bitmap getForeBitmap(Resources resources, int num){//构造方法
		
		Bitmap bitmap = BitmapFactory.decodeResource(resources, fore[num]);//加载图片
		
		return bitmap;//返回BitMap类
	}

	public void loadingBitmap(Resources resources, int width, int num){

		BitmapFactory.Options opts = new BitmapFactory.Options();//BitmapFactory.Options这个类
		//仅返回图片的 宽高  这样就不会占用太多的内存，也就不会那么频繁的发生OOM了。
		opts.inJustDecodeBounds = true;//该值设为true那么将不返回实际的bitmap对象，不给其分配内存空间但是可以得到一些解码边界信息即图片大小等信息。
		Bitmap temp = BitmapFactory.decodeResource(resources, fore[0], opts);//加载图片 缩放 从fore【】中第一位开始
		int radio = (int) Math.ceil(opts.outWidth / (width*1.0 / num - 30));//向上取整 结果是7
		//Math.ceil(12.2)//返回13
		//Math.ceil(12.7)//返回13
		//Math.ceil(12.0)// 返回12
		opts.inSampleSize = radio;//属性值inSampleSize表示缩略图大小为原始图片大小的几分之一
		if(null != temp){
			temp.recycle();//回收
		}
		System.out.println(radio);
		
		opts.inJustDecodeBounds = false;//inJustDecodeBounds设为false，就可以根据已经得到的缩放比例得到自己想要的图片缩放图了。
		
		for(int i = 0; i < fore.length; i++){
			Bitmap bitmap = BitmapFactory.decodeResource(resources, fore[i], opts);//载入图片
			bitmapResource.add(bitmap);//循环添加到集合中
		}
	}
	
	public Bitmap getIconBitmap(int num){
		return bitmapResource.get(num);
	}
	
	public int size(){
		return bitmapResource.size();
	}
	
	public int getProgress(){
		return (int) (100.0*bitmapResource.size()/fore.length);//得到文件当前大小
	}
}