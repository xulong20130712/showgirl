package zzuli.edu.cn.lish13;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

public class MenuActivity extends Activity {
	
	int width;
	GridView grid;//声明 图片显示 类似九宫格 的 控件
	TextView message; //声明 TextView 控件 显示文本信息
	TextView author_message;//声明 TextView 控件 显示文本信息
	
	ImageResource ir = ImageResource.getImageResource();//声明   ImageResource类 使用 getImageResource方法
	ToastTool toast;//声明 ToastTool类
	Handler handler; //声明 Handler 用来传递消息
	
  
    public void onCreate(Bundle savedInstanceState) {//android生命周期的第一个方法 用来准备数据
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);//使用layout文件夹下的 main布局
        
        float density = getResources().getDisplayMetrics().density;//获取屏幕分辨率、宽高（存疑！）
        width = (int) (getWindowManager().getDefaultDisplay().getWidth()/density);//取得屏幕的宽度 
        message = (TextView) findViewById(R.id.gallary_text_926);//确定控件ID
        grid = (GridView) findViewById(R.id.gallary_picture_menu_925);//确定控件ID
        author_message = (TextView) findViewById(R.id.gallary_text_927);//确定控件ID
        
        loadingPicture();//使用loadingPicture（）方法
    }
    
    public void loadingPicture(){
    	new AsyncTask<Object, Object, Object>() {//	进度条类 异步处理
			@Override
			protected Object doInBackground(Object... params) {//后台执行，比较耗时的操作都可以放在这里。
				publishProgress();//来更新任务的进度。
				ir.loadingBitmap(getResources(), width, 3);//对图片进行缩放的方法 
				return null;
			}

			@Override
			protected void onPostExecute(Object result) {//在doInBackground 执行完成后，onPostExecute 方法将被UI thread调用，后台的计算结果将通过该方法传递到UI thread. 
				handler.removeCallbacks(update);//removeCallbacks方法是删除指定的Runnable对象，使线程对象停止运行。
				message.setVisibility(View.GONE);//message设置为不可见
				author_message.setVisibility(View.GONE);//author_message设置为不可见
				grid.setVisibility(View.VISIBLE);//grid设置为可见
				grid.setNumColumns(3);//设置GridView的列数
				grid.setHorizontalSpacing(20);//两列之间的间距
				grid.setVerticalSpacing(40);//两行之间的间距
				grid.setAdapter(adapter);//使用适配器
				grid.setOnItemClickListener(new OnItemClickListener() {//GridView 的监听器
					
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int position, long arg3) {
						Intent intent = new Intent();//实例化Intent
						intent.setClass(MenuActivity.this, ShowActivity.class);//设置跳转路径
						Bundle bundle = new Bundle();//实例化Bundle类 传值
						bundle.putInt("num",position);//传 列表的 位置值 到ShowActivity
						intent.putExtras(bundle);//intent发送Bundle
						MenuActivity.this.startActivity(intent);//开始跳转
					}
				});
				adapter.notifyDataSetChanged();//在adapter的数据发生变化以后通知UI主线程根据新的数据重新画图。
				super.onPostExecute(result);
			}

			
			protected void onProgressUpdate(Object... values) {//在publishProgress方法被调用后，UI thread将调用这个方法从而在界面上展示任务的进展情况
				
				handler = new Handler();//实例化handler
				//显示加载进度
				handler.post(update);//根据线程来更新进度
				
				super.onProgressUpdate(values);
			}
		}.execute();//执行 异步操作
    }
    
    BaseAdapter adapter = new BaseAdapter() {
		
		
		public View getView(int position, View convertView, ViewGroup parent) {
			ImageView iv = new ImageView(MenuActivity.this);//显示任意图像
			iv.setMaxWidth(width / 3 - 30);//设置宽度
			iv.setAdjustViewBounds(true);//是否保持宽高比
			iv.setImageBitmap(ir.getIconBitmap(position));//设置图片 使用ImageResource类中集合当中的图片
			return iv;
		}
		
		@Override
		public long getItemId(int position) {//得到ID
			return position;
		}
		
		@Override
		public Object getItem(int arg0) {//得到位置
			return arg0;
		}
		
		@Override
		public int getCount() {//得到大小
			return ir.size();
		}
	};

	@Override
	protected void onDestroy() {
		System.exit(0);
		super.onDestroy();
	}


	public boolean onKeyDown(int keyCode, KeyEvent event) {//返回键监听
		if (keyCode == KeyEvent.KEYCODE_BACK){//如果点击返回键
			
			if(toast.isDoubleClick("再次点击返回按钮即可退出此应用⋯⋯")){
				finish();
			}
			
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}


	protected void onResume() {//当恢复Activity，也就是生命周期处在在 onResume中
		toast = ToastTool.getToast(MenuActivity.this);
		super.onResume();
		toast.showMessage("双击返回按钮即可退出此应用⋯⋯");//提示信息
	}
	
	Runnable update = new Runnable() {//实例化线程
		@Override
		public void run() {
			int progress = ir.getProgress();//得到文件的总大小
			if(null != message){
				message.setText("数据加载中（"+progress+"%），请稍等⋯⋯\n\n");//如果message不是空，就让显示文本
			}
			if(100 == progress){
				handler.removeCallbacks(update);//等于100 也即是说 加载完毕 就停止线程,也就是关闭此定时器
			} else {
				handler.postDelayed(update, 200);//使用PostDelayed方法，两秒后调用此Runnable对象,实际上也就实现了一个0.2s的一个定时器
			}
		}
	};
}