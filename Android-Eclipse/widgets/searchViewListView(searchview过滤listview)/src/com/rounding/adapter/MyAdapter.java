package com.rounding.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter implements Filterable {
	private Context context; 
	private List<String> data , copyData ;		

	public MyAdapter(Context context, List<String> data) {
		super();
		this.context = context;
		this.data = data;
		copyData = data ;
	}

	@Override
	public int getCount() {
		return data.size() ;
	}

	@Override
	public Object getItem(int position) {
		return data.get(position) ;
	}

	@Override
	public long getItemId(int position) {
		return position ;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view ;
		if(null == convertView) {
			view = LayoutInflater.from(context).inflate(android.R.layout.simple_list_item_1, null) ;
		}
		else {
			view = convertView ;
		}
		TextView tv = (TextView) view.findViewById(android.R.id.text1) ;
		tv.setText(data.get(position)) ;
		return view ;
	}

	private Filter myFilter ;
	@Override
	public Filter getFilter() {
		if(null == myFilter) {
			myFilter = new MyFilter() ;
		}
		return myFilter ;
	}

	class MyFilter extends Filter{
		// 定义过滤规则
		protected FilterResults performFiltering(CharSequence constraint) {
			List<String> filterData = new ArrayList<String>() ;
			
			if(constraint != null && constraint.toString().trim().length() > 0) {				
				String key = constraint.toString().trim().toLowerCase() ;
				for (String item : copyData) {
					if(item.toLowerCase().indexOf(key) != -1) {
						filterData.add(item) ;
					}
				}
			}
			else {	//如果搜索框为空，就恢复原始数据
				filterData = copyData ;
			}
			FilterResults results = new FilterResults() ;
			results.values = filterData ;
			results.count = filterData.size() ;
			return results ;
		}

		@Override
		protected void publishResults(CharSequence constraint,
				FilterResults results) {
			
			data = (List<String>) results.values ;
			if(results.count > 0) {
				notifyDataSetChanged() ;
			}
			else {
				notifyDataSetInvalidated() ;
			}
		}
		
	} ;

}
