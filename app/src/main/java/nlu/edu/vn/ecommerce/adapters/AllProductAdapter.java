package nlu.edu.vn.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.text.DecimalFormat;
import java.util.List;

import nlu.edu.vn.ecommerce.R;
import nlu.edu.vn.ecommerce.activities.DetailActivity;
import nlu.edu.vn.ecommerce.models.AllProductModel;

public class AllProductAdapter extends RecyclerView.Adapter<AllProductAdapter.ViewHolder> {
    private Context context;
    private List<AllProductModel> allProductModels;

    public AllProductAdapter(Context context, List<AllProductModel> allProductModels) {
        this.context = context;
        this.allProductModels = allProductModels;
    }

    @NonNull
    @Override
    public AllProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AllProductAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.all_product, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AllProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(allProductModels.get(position).getImg_url()).into(holder.itemImage);
        holder.itemName.setText(allProductModels.get(position).getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        int price = allProductModels.get(position).getPrice();
        holder.itemPrice.setText(decimalFormat.format(price)+"đ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("allProduct", allProductModels.get(position));
                context.startActivity(intent);
            }
        });


    }

    @Override
    public int getItemCount() {
        return allProductModels.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView itemImage;
        private TextView itemName;
        private TextView itemPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemImage = itemView.findViewById(R.id.item_image);
            itemPrice = itemView.findViewById(R.id.item_cost);
            itemName = itemView.findViewById(R.id.item_name);

        }
    }
}
