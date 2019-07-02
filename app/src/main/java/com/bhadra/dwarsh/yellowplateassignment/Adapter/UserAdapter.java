package com.bhadra.dwarsh.yellowplateassignment.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.bhadra.dwarsh.yellowplateassignment.Pojo.GitUsers;
import com.bhadra.dwarsh.yellowplateassignment.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.MyViewHolder> implements Filterable {
    private Context context;
    private List<GitUsers> userList;
    private List<GitUsers> userListFiltered;
    private UserAdapterListener listener;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, url;
        public ImageView thumbnail;

        public MyViewHolder(View view) {
            super(view);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // send selected contact in callback
                    listener.onUserSelected(userListFiltered.get(getAdapterPosition()));
                }
            });
            name = (TextView) itemView.findViewById(R.id.username);
            url = (TextView) itemView.findViewById(R.id.userurl);
            thumbnail = (ImageView) itemView.findViewById(R.id.userimage);
        }

    }
    public UserAdapter(Context context, List<GitUsers> userList, UserAdapterListener listener) {
        this.context = context;
        this.listener = listener;
        this.userList = userList;
        this.userListFiltered = userList;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.user_row_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.name.setText(userListFiltered.get(position).getId() + ":" + userListFiltered.get(position).getLogin());
        holder.url.setText(userListFiltered.get(position).getHtml_url());
        Glide.with(context).load(userListFiltered.get(position).getAvatar_url() + ".png").asBitmap().centerCrop().into(holder.thumbnail);

    }


    @Override
    public int getItemCount() {
        return userListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    userListFiltered = userList;
                } else {
                    List<GitUsers> filteredList = new ArrayList<>();
                    for (GitUsers row : userList) {

                        if (row.getLogin().toLowerCase().contains(charString.toLowerCase()) || row.getId().contains(charSequence)) {
                            filteredList.add(row);
                        }
                    }

                    userListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = userListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userListFiltered = (ArrayList<GitUsers>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface UserAdapterListener {
        void onUserSelected(GitUsers gitUsers);
    }
}