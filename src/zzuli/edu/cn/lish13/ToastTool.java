package zzuli.edu.cn.lish13;

import java.util.Date;

import android.content.Context;
import android.widget.Toast;

public class ToastTool {
	
	private static Toast toast;//声明 toast类
	private static ToastTool tt;//声明一个内部类 ToastTool类
	private static Context context;//声明 Context类
	
	private static long preTime;//声明 长整形
	//双击的最大时间间隔
	private static long totalTime = 2000;
	
	private ToastTool(Context context){
		this.context = context;
		preTime = 0;
	};
	
	public static ToastTool getToast(Context context){
		if(null == tt){
			tt = new ToastTool(context);//如果tt是空的话，也就是说 没有实例化，那么就实例化一个
		}
		return tt;
	}
	
	/**
	 * 输出消息
	 * @param message
	 */
	public void showMessage(String message){
		if(null != toast)
			toast.cancel();
		toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
		toast.show();
	}
	
	/**
	 * 判断是否双击，不是的话，输出消息
	 * @param message
	 * @return
	 */
	public boolean isDoubleClick(String message){
		
		if(null != toast)
			toast.cancel();//如果toast不是空，则取消上次提示，显示此次提示
		
		long nowTime = new Date().getTime();//得到系统时间
		if(nowTime < preTime + totalTime){//如果 变量preTime 加上 totalTime间隔时间 大于系统时间 返回true
			return true;
		} else {
			toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);//提示信息
			toast.show();
			preTime = nowTime;//系统时间赋予变量preTime 返回flase
			return false;
		}
	}
}
