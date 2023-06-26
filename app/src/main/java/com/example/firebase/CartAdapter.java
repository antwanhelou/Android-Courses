package com.example.firebase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.bumptech.glide.Glide;

import java.util.List;

public class CartAdapter extends ArrayAdapter<Shop> {
    private Context context;
    private List<Shop> productList;

    public CartAdapter(Context context, List<Shop> productList) {
        super(context, 0, productList);
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;

        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.cart_item, parent, false);
        }

        Shop currentProduct = productList.get(position);

        ImageView imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
        TextView textViewProductName = itemView.findViewById(R.id.textViewProductName);
        TextView textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
        TextView textViewProductQuantity = itemView.findViewById(R.id.textViewProductQuantity);

        // Load image using Glide library
        Glide.with(context).load(currentProduct.getImage()).into(imageViewProduct);

        textViewProductName.setText(currentProduct.getName());
        textViewProductPrice.setText(String.format("$%.2f", currentProduct.getPrice()));
        textViewProductQuantity.setText(String.format("Quantity: %d", currentProduct.getQuantity()));

        return itemView;
    }
}
