package nlu.edu.vn.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.Serializable;
import java.util.List;

import nlu.edu.vn.ecommerce.R;
import nlu.edu.vn.ecommerce.models.AddressModel;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private Context context;
    private List<AddressModel> addressModelList;
    private SelectedAddress selectedAddress;
    private RadioButton selectedRadioButton;

    public AddressAdapter(Context context, List<AddressModel> addressModelList, SelectedAddress selectedAddress) {
        this.context = context;
        this.addressModelList = addressModelList;
        this.selectedAddress = selectedAddress;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AddressAdapter.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        holder.address.setText(addressModelList.get(position).getUserAddress());
        holder.radioButtonSelectAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (AddressModel address : addressModelList) {
                    address.setSelected(false);
                }
                addressModelList.get(position).setSelected(true);
                if (selectedRadioButton != null) {
                    selectedRadioButton.setChecked(false);
                }
                selectedRadioButton = (RadioButton) v;
                selectedRadioButton.setChecked(true);
                selectedAddress.setAddress(addressModelList.get(position).getUserAddress());

            }
        });
    }

    @Override
    public int getItemCount() {
        return addressModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView address;
        private RadioButton radioButtonSelectAddress;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            address = itemView.findViewById(R.id.address_add);
            radioButtonSelectAddress = itemView.findViewById(R.id.select_address);

        }
    }

    public interface SelectedAddress {
        void setAddress(String address);
    }
}
