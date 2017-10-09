package com.example.myviewpager;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.app.Activity;

import static android.content.ContentValues.TAG;

/**
 * 倒计时demo
 * @author SHI
 * 2016年3月16日 19:08:07
 */
public class MainActivity extends Activity {

	/**截至时间数据源**/
	private List<Date> listData;
    /**当前时间**/
    private long time_Current;
	/**ListView控件**/
	 private ListView listView;
	 /**适配器**/
	 private MyCountAdapter myCountAdapter;
	
	//这里很重要，使用Handler的延时效果，每隔一秒刷新一下适配器，以此产生倒计时效果
    private Handler handler_timeCurrent = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            time_Current = time_Current+1000;
//			myCountAdapter.notifyDataSetChanged();
			updateItem(1);
            handler_timeCurrent.sendEmptyMessageDelayed(0,1000);
        }
    };

//	/**
//	 * 第一种方法 更新对应view的内容
//	 *
//	 * @param position 要更新的位置
//	 */
//	private void updateSingle(int position) {
//		/**第一个可见的位置**/
//		int firstVisiblePosition = listView.getFirstVisiblePosition();
//		/**最后一个可见的位置**/
//		int lastVisiblePosition = listView.getLastVisiblePosition();
//
//		/**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
//		if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
//			/**获取指定位置view对象**/
//			View view = listView.getChildAt(position - firstVisiblePosition);
//			TextView textView = (TextView) view.findViewById(R.id.textView);
//			textView.setText(datas.get(position));
//		}
//	}

	/**
	 * 第三种方法 调用一次getView()方法；Google推荐的做法
	 *
	 * @param position 要更新的位置
	 */
	private void updateItem(int position) {
		Log.e(TAG, "updateItem: 被执行"+position );
		/**第一个可见的位置**/
		int firstVisiblePosition = listView.getFirstVisiblePosition();
		/**最后一个可见的位置**/
		int lastVisiblePosition = listView.getLastVisiblePosition();

		/**在看见范围内才更新，不可见的滑动后自动会调用getView方法更新**/
		if (position >= firstVisiblePosition && position <= lastVisiblePosition) {
			/**获取指定位置view对象**/
			View view = listView.getChildAt(position - firstVisiblePosition);
			myCountAdapter.getView(position, view, listView);
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		listView = (ListView) findViewById(R.id.listView);




		//模拟活动截至时间数据
		listData = new ArrayList<Date>();
		listData.add(new Date(2016,3,16,8,20,31));
		listData.add(new Date(2016,3,16,8,21,20));
		listData.add(new Date(2016,3,16,13,21,22));
		listData.add(new Date(2016,3,16,8,21,20));
		listData.add(new Date(2016,3,16,8,21,23));
		listData.add(new Date(2016,3,16,14,21,20));
		listData.add(new Date(2016,3,16,8,21,23));
		listData.add(new Date(2016,3,16,8,21,24));
		listData.add(new Date(2016,3,16,8,21,20));
		listData.add(new Date(2016,3,16,8,22,25));
		listData.add(new Date(2016,3,16,8,23,20));
		listData.add(new Date(2016,3,16,8,24,26));
		listData.add(new Date(2016,3,16,8,25,20));
		listData.add(new Date(2016,3,16,8,24,25));
		listData.add(new Date(2016,3,16,8,25,20));
		listData.add(new Date(2016,3,16,8,24,26));
		listData.add(new Date(2016,3,16,11,20,20));
		listData.add(new Date(2016,3,16,14,40,20));
		listData.add(new Date(2016,3,16,8,44,20));
		listData.add(new Date(2016,3,16,10,20,20));
		listData.add(new Date(2016,3,16,10,20,20));
		listData.add(new Date(2016,3,16,10,20,20));
		listData.add(new Date(2016,3,16,10,20,20));
		listData.add(new Date(2016,3,16,10,20,20));
		listData.add(new Date(2016,3,16,10,20,20));
		listData.add(new Date(2016,3,16,10,20,20));
		listData.add(new Date(2016,3,16,10,20,20));
		listData.add(new Date(2016,3,16,10,20,20));
		listData.add(new Date(2016,3,16,10,20,20));
		listData.add(new Date(2016,3,16,10,20,20));

		//模拟当前服务器时间数据
		Date date = new Date(2016,3,16,8,20,20);
		time_Current = date.getTime();
		
		myCountAdapter = new MyCountAdapter();
		listView.setAdapter(myCountAdapter);
		
        handler_timeCurrent.sendEmptyMessageDelayed(0,1000);
	}
	
	//防止当前Activity结束以后,   handler依然继续循环浪费资源
	@Override
	protected void onDestroy() {
		handler_timeCurrent.removeCallbacksAndMessages(null);
		super.onDestroy();
	}
	
	public class MyCountAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return listData.size();
		}

		@Override
		public Object getItem(int position) {
			return listData.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			Log.e(TAG, "getView: 被执行"+position );
			ViewHolder holder = null;
			if(convertView == null){
				convertView = View.inflate(MainActivity.this, R.layout.item_adapter_listview, null);
				holder = new ViewHolder();
				holder.tv_hour = (TextView) convertView.findViewById(R.id.tv_hour);
				holder.tv_minute = (TextView) convertView.findViewById(R.id.tv_minute);
				holder.tv_second = (TextView) convertView.findViewById(R.id.tv_second);
				convertView.setTag(holder);
			}else{
				holder = (ViewHolder) convertView.getTag();
			}
			Date date_finish = listData.get(position);
			updateTextView( date_finish.getTime()-time_Current, holder);

			return convertView;
		}

		/****
		 * 刷新倒计时控件
		 */
		public void updateTextView(long times_remain,ViewHolder hoder) {
			
			if (times_remain <= 0) {
				hoder.tv_hour.setText("00");
				hoder.tv_minute.setText("00");
				hoder.tv_second.setText("00");
				return;
			}
			//秒钟
			long time_second = (times_remain/1000)%60;
			String str_second;
			if (time_second < 10) {
				str_second = "0" + time_second;
			} else {
				str_second = "" + time_second;
			}
			
			long time_temp = ((times_remain / 1000) - time_second) / 60;
			//分钟
			long time_minute = time_temp % 60;
			String str_minute;
			if (time_minute < 10) {
				str_minute = "0" + time_minute;
			} else {
				str_minute = "" + time_minute;
			}
			
			time_temp = (time_temp - time_minute) / 60;
			//小时
			long time_hour = time_temp;
			String str_hour;
			if (time_hour < 10) {
				str_hour = "0" + time_hour;
			} else {
				str_hour = "" + time_hour;
			}
			
			hoder.tv_hour.setText(str_hour);
			hoder.tv_minute.setText(str_minute);
			hoder.tv_second.setText(str_second);
			
		}
		
		private class ViewHolder{
			/** 小时 **/
			private TextView tv_hour;
			/** 分钟 **/
			private TextView tv_minute;
			/** 秒 **/
			private TextView tv_second;		
		}
	}
	

}
