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

import org.w3c.dom.Text;

import java.text.DecimalFormat;
import java.util.List;

import nlu.edu.vn.ecommerce.R;
import nlu.edu.vn.ecommerce.activities.DetailActivity;
import nlu.edu.vn.ecommerce.models.PopularProductModel;

public class PopularProductAdapter extends RecyclerView.Adapter<PopularProductAdapter.ViewHolder> {
    private Context context;
    private List<PopularProductModel> popularProductModels;

    public PopularProductAdapter(Context context, List<PopularProductModel> popularProductModels) {
        this.context = context;
        this.popularProductModels = popularProductModels;
    }

    @NonNull
    @Override
    public PopularProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PopularProductAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.popular_products, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Glide.with(context).load(popularProductModels.get(position).getImg_url()).into(holder.imagePopular);
        holder.productName.setText(popularProductModels.get(position).getName());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        int price = popularProductModels.get(position).getPrice();
        holder.price.setText(decimalFormat.format(price) + "đ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailActivity.class);
                intent.putExtra("popularDetailed", popularProductModels.get(position));
                context.startActivity(intent);
            }
        });

    }


    @Override
    public int getItemCount() {
        return popularProductModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imagePopular;
        private TextView productName;
        private TextView price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imagePopular = itemView.findViewById(R.id.popular_product_img);
            productName = itemView.findViewById(R.id.popular_product_name);
            price = itemView.findViewById(R.id.popular_price);


        }
    }
}
