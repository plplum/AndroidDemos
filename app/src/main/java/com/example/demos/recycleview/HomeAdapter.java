package com.example.demos.recycleview;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.example.demos.R;

import java.util.List;

//class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyViewHolder>
class HomeAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>
{

	private List<String> mDatas;
	private LayoutInflater mInflater;

	private boolean[] flag;
	private boolean[] flag1;

	//上拉加载更多状态-默认为-1   页面首次打开不显示上拉加载更多
	private int load_more_status=-1;

	private static final int TYPE_ITEM = 0;  //普通Item View
	private static final int TYPE_FOOTER = 1;  //顶部FootView

	//上拉加载更多
	public static final int  PULLUP_LOAD_MORE=0;
	//正在加载中
	public static final int  LOADING_MORE=1;


	public interface OnItemClickLitener
	{
		void onItemClick(View view, int position);
		void onItemLongClick(View view, int position);
	}

	private OnItemClickLitener mOnItemClickLitener;

	public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener)
	{
		this.mOnItemClickLitener = mOnItemClickLitener;
	}
	

	public HomeAdapter(Context context, List<String> datas)
	{
		mInflater = LayoutInflater.from(context);
		mDatas = datas;
		flag = new boolean[mDatas.size()];//此处添加一个boolean类型的数组
		flag1 = new boolean[mDatas.size()];
	}

/*	@Override
	public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		MyViewHolder holder = new MyViewHolder(mInflater.inflate(R.layout.item_home, parent, false));
		return holder;
	}*/

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		//进行判断显示类型，来创建返回不同的View
		if(viewType==TYPE_ITEM){
			View view=mInflater.inflate(R.layout.item_home,parent,false);
			//这边可以做一些属性设置，甚至事件监听绑定
			//view.setBackgroundColor(Color.RED);
			MyViewHolder itemViewHolder=new MyViewHolder(view);
			return itemViewHolder;
		}else if(viewType==TYPE_FOOTER){
			View foot_view=mInflater.inflate(R.layout.recycler_load_more_layout,parent,false);
			//这边可以做一些属性设置，甚至事件监听绑定
			//view.setBackgroundColor(Color.RED);
			FootViewHolder footViewHolder=new FootViewHolder(foot_view);
			return footViewHolder;
		}
		return null;
	}



/*	@Override
	public void onBindViewHolder(final MyViewHolder holder, final int position)
	{
		holder.tv.setText(mDatas.get(position));

		holder.checkBox.setOnCheckedChangeListener(null);//先设置一次CheckBox的选中监听器，传入参数null
		holder.checkBox.setChecked(flag[position]);//用数组中的值设置CheckBox的选中状态

		//再设置一次CheckBox的选中监听器，当CheckBox的选中状态发生改变时，把改变后的状态储存在数组中
		holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				flag[position] = b;
			}
		});

		holder.toggleButton.setOnCheckedChangeListener(null);
		holder.toggleButton.setChecked(flag1[position]);
		holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
				flag1[position] = b;
			}
		});


		// 如果设置了回调，则设置点击事件
		if (mOnItemClickLitener != null)
		{
			holder.itemView.setOnClickListener(new OnClickListener()
			{
				@Override
				public void onClick(View v)
				{
					int pos = holder.getLayoutPosition();
					mOnItemClickLitener.onItemClick(holder.itemView, pos);
				}
			});
			
			holder.itemView.setOnLongClickListener(new OnLongClickListener()
			{
				@Override
				public boolean onLongClick(View v)
				{
					int pos = holder.getLayoutPosition();
					mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
					removeData(pos);
					return false;
				}
			});
		}
	}*/

	@Override
	public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, final int position)
	{
		if(viewHolder instanceof MyViewHolder) {
			final MyViewHolder holder = (MyViewHolder) viewHolder;
			holder.tv.setText(mDatas.get(position));

			holder.checkBox.setOnCheckedChangeListener(null);//先设置一次CheckBox的选中监听器，传入参数null
			holder.checkBox.setChecked(flag[position]);//用数组中的值设置CheckBox的选中状态

			//再设置一次CheckBox的选中监听器，当CheckBox的选中状态发生改变时，把改变后的状态储存在数组中
			holder.checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
					flag[position] = b;
				}
			});

			holder.toggleButton.setOnCheckedChangeListener(null);
			holder.toggleButton.setChecked(flag1[position]);
			holder.toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
				@Override
				public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
					flag1[position] = b;
				}
			});


			// 如果设置了回调，则设置点击事件
			if (mOnItemClickLitener != null)
			{
				holder.itemView.setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v)
					{
						int pos = holder.getLayoutPosition();
						mOnItemClickLitener.onItemClick(holder.itemView, pos);
					}
				});

				holder.itemView.setOnLongClickListener(new OnLongClickListener()
				{
					@Override
					public boolean onLongClick(View v)
					{
						int pos = holder.getLayoutPosition();
						mOnItemClickLitener.onItemLongClick(holder.itemView, pos);
						removeData(pos);
						return false;
					}
				});
			}
		}else if (viewHolder instanceof FootViewHolder){
			FootViewHolder footViewHolder=(FootViewHolder)viewHolder;
			if (load_more_status!=-1){
				footViewHolder.footLayout.setVisibility(View.VISIBLE);
			}
			switch (load_more_status){
				case PULLUP_LOAD_MORE:
					footViewHolder.foot_view_item_tv.setText("上拉加载更多...");
					break;
				case LOADING_MORE:
					footViewHolder.foot_view_item_tv.setText("正在加载更多数据...");
					break;
			}
		}
	}


	@Override
	public int getItemCount()
	{
		return mDatas.size() +1 ;
	}

	public void addData(int position)
	{
		mDatas.add(position, "Insert One");
		notifyItemInserted(position);
	}


	public void removeData(int position)
	{
		mDatas.remove(position);
		notifyItemRemoved(position);
	}

	class MyViewHolder extends ViewHolder
	{

		TextView tv;
		CheckBox checkBox;
		ToggleButton toggleButton;

		public MyViewHolder(View view)
		{
			super(view);
			tv = (TextView) view.findViewById(R.id.id_num);
			checkBox = (CheckBox) view.findViewById(R.id.checkbox);
			toggleButton = (ToggleButton) view.findViewById(R.id.toggleButton);
		}
	}

	public void initFlagArrays() {
		flag = new boolean[mDatas.size()];//此处添加一个boolean类型的数组
		flag1 = new boolean[mDatas.size()];
	}


	/**
	 * //上拉加载更多
	 * PULLUP_LOAD_MORE=0;
	 *  //正在加载中
	 * LOADING_MORE=1;
	 * //加载完成已经没有更多数据了
	 * NO_MORE_DATA=2;
	 * @param status
	 */
	public void changeMoreStatus(int status){
		load_more_status=status;
		notifyDataSetChanged();
	}


	/**
	 * 底部FootView布局
	 */
	public static class FootViewHolder extends  RecyclerView.ViewHolder{
		private TextView foot_view_item_tv;
		private LinearLayout footLayout;
		public FootViewHolder(View view) {
			super(view);
			foot_view_item_tv=(TextView)view.findViewById(R.id.foot_view_item_tv);
			footLayout=(LinearLayout)view.findViewById(R.id.foot_layout);
		}
	}


	/**
	 * 进行判断是普通Item视图还是FootView视图
	 * @param position
	 * @return
	 */
	@Override
	public int getItemViewType(int position) {
		// 最后一个item设置为footerView
		if (position + 1 == getItemCount()) {
			return TYPE_FOOTER;
		} else {
			return TYPE_ITEM;
		}
	}

}