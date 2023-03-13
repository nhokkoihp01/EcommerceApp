package nlu.edu.vn.ecommerce.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import nlu.edu.vn.ecommerce.R;
import nlu.edu.vn.ecommerce.models.NewProductModel;

public class NewProductAdapter extends RecyclerView.Adapter<NewProductAdapter.ViewHolder> {
    private Context context;
    private List<NewProductModel> newProductModels;

    public NewProductAdapter(Context context, List<NewProductModel> newProductModels) {
        this.context = context;
        this.newProductModels = newProductModels;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.new_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Glide.with(context).load(newProductModels.get(position).getImg_url()).into(holder.newImg);
        holder.txtNewProductName.setText(newProductModels.get(position).getName());
        holder.txtNewPrice.setText(String.valueOf(newProductModels.get(position).getPrice()));


    }

    @Override
    public int getItemCount() {
        return newProductModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView newImg;
        private TextView txtNewProductName;
        private TextView txtNewPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newImg = itemView.findViewById(R.id.new_img);
            txtNewProductName = itemView.findViewById(R.id.new_product_name);
            txtNewPrice = itemView.findViewById(R.id.new_price);

        }
    }


}
