package com.mrfu.pulltorefresh;


import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.mrfu.pulltorefresh.lib.PullRefreshListView;
import com.mrfu.pulltorefresh.lib.PullRefreshListener;

/***
 * @author MrFu
 */
public class MainActivity extends Activity implements PullRefreshListener, OnItemClickListener {
	private Context mContext;
	private PullRefreshListView pullRefreshListView;
	private List<Model> models = new ArrayList<Model>();

	int selectItem = -1;
	int selectTop = 0;
	View selectView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initData();
		mContext = this;
		pullRefreshListView = (PullRefreshListView)findViewById(R.id.listview);
		pullRefreshListView.setPullRefreshListener(this);
//		pullRefreshListView.setOnScrollListener(this);
		ListView listView = (ListView)pullRefreshListView.getAbsListView();
		listView.setAdapter(new ListViewAdapter());
		listView.setOnItemClickListener(this);
		pullRefreshListView.setRefreshing();
	}
	private void initData() {
		Model model = null;
		for (int i = 0; i < 12; i++) {
			model = new Model("A=" + i, "B", "C", "D");
		}
		models.add(model);
	}
	@Override
	public void onPullDownRefresh() {
		pullRefreshListView.postDelayed(new Runnable() {
			@Override
			public void run() {
				pullRefreshListView.reset();
			}
		}, 2000);
	}
	@Override
	public void onPullUpRefresh() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		
	}
	
	private class ListViewAdapter extends BaseAdapter{

		@Override
		public int getCount() {
			return models.size();
		}

		@Override
		public Object getItem(int position) {
			return models.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			HoldView holdView = null;
			if (convertView == null) {
				holdView = new HoldView();
				convertView = View.inflate(mContext, R.layout.item_listview, null);
				holdView.tv_title = (TextView)convertView.findViewById(R.id.tv_title);
				holdView.tv_time = (TextView)convertView.findViewById(R.id.tv_time);
				holdView.tv_nb = (TextView)convertView.findViewById(R.id.tv_nb);
				convertView.setTag(holdView);
			} else {
				holdView = (HoldView) convertView.getTag();
			}
			Model model = models.get(position);
			holdView.tv_title.setText(model.nameString);
			holdView.tv_time.setText(model.nameString1);
			holdView.tv_nb.setText("+" + model.nameString2);
			return convertView;
		}
		private class HoldView {
			private TextView tv_title;
			private TextView tv_time;
			private TextView tv_nb;
		}
	}
	
}
