package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cacau2.ecovoicetest.R;

import java.util.ArrayList;

import Base.Data.DataImageTree;
import butterknife.BindView;

public class AdapterAddImageTree extends RecyclerView.Adapter<AdapterAddImageTree.ViewHolder> {

    private ArrayList<DataImageTree> mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    TextView myTextView;


    // data is passed into the constructor
    public AdapterAddImageTree(Context context, ArrayList<DataImageTree> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }


    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.custom_image_row, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.imageView.setImageURI(mData.get(position).getUriFromString());
        holder.mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(position);
            }
        });
    }
    public void delete(int position){
        mData.remove(position);
        notifyDataSetChanged();
    }
    public void insertData(ArrayList<DataImageTree> mData){

        mData.addAll(mData);
        notifyDataSetChanged();
    }

    // total number of cells
    @Override
    public int getItemCount() {
        if (mData != null)
            return mData.size();
        return 0;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public ImageView imageView;
        public Button mButton;
        ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img_imagem_row);
            itemView.setOnClickListener(this);
            mButton = itemView.findViewById(R.id.btn_remove_image);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    DataImageTree getItem(int id) {
        return mData.get(id);
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}