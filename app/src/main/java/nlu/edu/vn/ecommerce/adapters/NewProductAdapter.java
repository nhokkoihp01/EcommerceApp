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
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(newProductModels.get(position).getImg_url()).into(holder.newImg);
        holder.txtNewProductName.setText(newProductModels.get(position).getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        int price = newProductModels.get(position).getPrice();
        holder.txtNewPrice.setText(decimalFormat.format(price)+"đ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("detailed",newProductModels.get(position));
                context.startActivity(intent);
            }
        });


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
