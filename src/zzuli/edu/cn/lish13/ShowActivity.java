package zzuli.edu.cn.lish13;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;

public class ShowActivity extends Activity {

	Bitmap bitmap;//声明位图类
	ShowGirlView sgv;//声明ShowGirlView
	
	ToastTool toast;//声明ToastTool
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        Intent intent = getIntent();//实例化intent 接受值 也就是上一个页面中的位置值
        int num = intent.getIntExtra("num", 0);//实例化 int类型的num 来接受值
        
        int width = getWindowManager().getDefaultDisplay().getWidth();//得到屏幕的宽度
        int height = getWindowManager().getDefaultDisplay().getHeight();//得到屏幕的长度
        sgv = new ShowGirlView(ShowActivity.this, width, height, num);//实例化ShowActivity类
        
        setContentView(sgv);//使用ShowActivity类作为布局
    }

	@Override
	protected void onDestroy() {//生命周期 onDestroy()
		if(null != sgv)
			sgv.realease();//realease()是用来回收内存的 可以到ShowGirlView中找到其方法
		super.onDestroy();
	}
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {//返回键监听
		if (keyCode == KeyEvent.KEYCODE_BACK){
			
			if(toast.isDoubleClick("再次点击返回按钮即可返回主菜单⋯⋯")){
				finish();
			}
			
			return true;
		} else {
			return super.onKeyDown(keyCode, event);
		}
	}

	@Override
	protected void onResume() {//当恢复Activity，也就是生命周期处在在 onResume中
		toast = ToastTool.getToast(ShowActivity.this);
		super.onResume();
		toast.showMessage("双击返回按钮即可返回主菜单⋯⋯");
	}
}
