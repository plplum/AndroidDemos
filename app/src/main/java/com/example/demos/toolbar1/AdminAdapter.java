package com.example.demos.toolbar1;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.demos.R;

import java.util.ArrayList;
import java.util.List;


public class AdminAdapter extends RecyclerView.Adapter<ViewHolder> {

    private List<String> mDatas;
    private LayoutInflater mInflater;

    public AdminAdapter(Context context, List<String> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public AdminViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        AdminViewHolder holder = new AdminViewHolder(mInflater.inflate(R.layout.listview_item_admin, parent, false));
        return holder;
    }


    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final AdminViewHolder viewHolder = (AdminViewHolder) holder;
        String admin = mDatas.get(position);
        viewHolder.textAccount.setText(admin);

        if (mOnItemClickLitener != null) {
           /* viewHolder.adminLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mOnItemClickLitener.onItemClick(v, position);
                }
            });

            viewHolder.adminLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    mOnItemClickLitener.onItemLongClick(v, position);
                    return false;
                }
            });*/

        }

    }

    @Override
    public int getItemCount() {
        return mDatas.size();
    }

    public void addData(int position, String admin) {
        mDatas.add(position, admin);
        notifyItemInserted(position);
    }


    public void removeData(int position) {
        mDatas.remove(position);
        notifyItemRemoved(position);
    }


    class AdminViewHolder extends ViewHolder {

        TextView textAccount;


        public AdminViewHolder(View view) {
            super(view);
            textAccount = (TextView) view.findViewById(R.id.name);
        }
    }

    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

}