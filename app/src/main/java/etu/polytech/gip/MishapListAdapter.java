package etu.polytech.gip;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class MishapListAdapter extends RecyclerView.Adapter<MishapListAdapter.ViewHolder>{

    public List<MishapModel> mishapModelList;

    public MishapListAdapter(List<MishapModel> mishapModelList ){

        this.mishapModelList = mishapModelList;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.txtLocation.setText(mishapModelList.get(position).getLocation());
        holder.txtDescription.setText(mishapModelList.get(position).getDescription());
        holder.txtType.setText(mishapModelList.get(position).getType());
        holder.txtSeverity.setText(mishapModelList.get(position).getSeverity());
        holder.txtTime.setText(mishapModelList.get(position).getTime());
        holder.txtDate.setText(mishapModelList.get(position).getDate());
    }

    @Override
    public int getItemCount() {
        return mishapModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        View anotherView;

        public TextView txtLocation;
        public TextView txtDescription;
        public TextView txtType;
        public TextView txtSeverity;
        public TextView txtTime;
        public TextView txtDate;

        public ViewHolder(View itemView) {
            super(itemView);
            anotherView = itemView;

            txtLocation = (TextView) anotherView.findViewById(R.id.locationItem);
            txtDescription = (TextView) anotherView.findViewById(R.id.descriptionItem);
            txtType = (TextView) anotherView.findViewById(R.id.typeItem);
            txtSeverity = (TextView) anotherView.findViewById(R.id.severityItem);
            txtTime = (TextView) anotherView.findViewById(R.id.timeItem);
            txtDate = (TextView) anotherView.findViewById(R.id.dateItem);
        }
    }
}
