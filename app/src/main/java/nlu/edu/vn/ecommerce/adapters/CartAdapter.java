package nlu.edu.vn.ecommerce.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.RecyclerView;


import java.text.DecimalFormat;
import java.util.List;

import nlu.edu.vn.ecommerce.R;
import nlu.edu.vn.ecommerce.models.CartModel;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    private Context context;
    private List<CartModel> cartModelList;
    private int totalAmount = 0;


    public CartAdapter(Context context, List<CartModel> cartModelList) {
        this.context = context;
        this.cartModelList = cartModelList;
    }

    @NonNull
    @Override
    public CartAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.ViewHolder holder, int position) {
        holder.currentDate.setText(cartModelList.get(position).getCurrentDate());
        holder.currentTime.setText(cartModelList.get(position).getCurrentTime());
        holder.productName.setText(cartModelList.get(position).getProductName());
        holder.productPrice.setText(cartModelList.get(position).getProductPrice());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        int totalPrice = cartModelList.get(position).getTotalPrice();
        holder.totalPrice.setText(decimalFormat.format(totalPrice)+"đ");
        holder.totalQuantity.setText(cartModelList.get(position).getTotalQuantity());

        totalAmount = totalAmount + cartModelList.get(position).getTotalPrice();
        Intent intent = new Intent("MyTotalAmount");
        intent.putExtra("totalAmount",totalAmount);

        LocalBroadcastManager.getInstance(context).sendBroadcast(intent);



    }

    @Override
    public int getItemCount() {
        return cartModelList.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView currentDate;
        private TextView currentTime;
        private TextView productName;
        private TextView productPrice;
        private TextView totalPrice;
        private TextView totalQuantity;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            currentDate = itemView.findViewById(R.id.current_date);
            currentTime = itemView.findViewById(R.id.current_time);
            productName = itemView.findViewById(R.id.product_name);
            productPrice = itemView.findViewById(R.id.product_price);
            totalPrice = itemView.findViewById(R.id.total_price);
            totalQuantity = itemView.findViewById(R.id.total_quantity);
        }
    }

}
