package cc.seeed.iot.ui_smartconfig;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.urlimageviewhelper.UrlImageViewHelper;

import java.util.ArrayList;

import cc.seeed.iot.R;
import cc.seeed.iot.udp.ConfigNodeData;
import cc.seeed.iot.ui_setnode.GroveFilterRecyclerAdapter;
import cc.seeed.iot.ui_setnode.SetupIotNodeActivity;
import cc.seeed.iot.webapi.model.GroverDriver;

/**
 * Created by tenwong on 15/6/25.
 */
public class ConfigNodeListRecyclerAdapter extends RecyclerView.Adapter<ConfigNodeListRecyclerAdapter.MainViewHolder> {
    private ArrayList<ConfigNodeData> localNodes;
    private Context context;

    SparseBooleanArray selector;
    private GroveFilterRecyclerAdapter.MainViewHolder.MyItemClickListener mItemClickListener;

    public ConfigNodeListRecyclerAdapter(ArrayList<ConfigNodeData> localNodes) {
        this.localNodes = localNodes;
    }

    @Override
    public MainViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.context = parent.getContext();
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.config_node_list_item, parent, false);

        return new MainViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MainViewHolder holder, final int position) {
        ConfigNodeData localNode = localNodes.get(position);
        holder.mMacView.setText(localNode.mac);
        holder.mIpView.setText(localNode.ip);

    }

    public void selectItem(int position) {
        notifyDataSetChanged();
    }


    public void clearSelectItem() {
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return localNodes.size();
    }

    public static class MainViewHolder extends RecyclerView.ViewHolder {
        TextView mMacView;
        TextView mIpView;
        View mView;

        public MainViewHolder(View itemView) {
            super(itemView);
            mView = itemView;
            mMacView = (TextView) itemView.findViewById(R.id.txtvmac);
            mIpView = (TextView) itemView.findViewById(R.id.txtvip);

        }

    }

}
