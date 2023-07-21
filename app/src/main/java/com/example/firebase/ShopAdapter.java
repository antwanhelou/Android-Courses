package com.example.firebase;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;
public class ShopAdapter extends ArrayAdapter<Shop> {
    private Context context;
    private List<Shop> productList;

    public ShopAdapter(Context context, List<Shop> productList) {
        super(context, 0, productList);
        this.context = context;
        this.productList = productList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemView = convertView;
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.product_item, parent, false);
        }

        Shop currentProduct = productList.get(position);

        ImageView imageViewProduct = itemView.findViewById(R.id.imageViewProduct);
        TextView textViewProductName = itemView.findViewById(R.id.textViewProductName);
        TextView textViewProductPrice = itemView.findViewById(R.id.textViewProductPrice);
        TextView textViewProductQuantity = itemView.findViewById(R.id.textViewProductQuantity);
        Button buttonAddToCart = itemView.findViewById(R.id.buttonAddToCart);

        Glide.with(context).load(currentProduct.getImage()).into(imageViewProduct);
        textViewProductName.setText(currentProduct.getName());
        textViewProductPrice.setText(String.format("$%.2f", currentProduct.getPrice()));
        textViewProductQuantity.setText(String.format("Quantity: %d", currentProduct.getQuantity()));

        buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (currentProduct.getId() == null) {
                    // Skip this product
                    return;
                }

                // Get the current user ID from SharedPreferences
                SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                String userId = sharedPreferences.getString("phone", "");

                DatabaseReference userCartRef = FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(userId)
                        .child("cart");

                DatabaseReference productRef = FirebaseDatabase.getInstance().getReference()
                        .child("products")
                        .child(currentProduct.getId());

                userCartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean isProductInCart = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Shop existingItem = snapshot.getValue(Shop.class);
                            if (existingItem != null && snapshot.getKey() != null && snapshot.getKey().equals(currentProduct.getId())) {
                                Toast.makeText(ShopAdapter.this.getContext(), "Product added to cart", Toast.LENGTH_LONG).show();
                                // Product exists in cart, so increment quantity
                                int quantity = existingItem.getQuantity();
                                existingItem.setQuantity(quantity + 1);
                                userCartRef.child(snapshot.getKey()).setValue(existingItem);
                                isProductInCart = true;
                                break;
                            }
                        }

                        if (!isProductInCart) {
                            Toast.makeText(ShopAdapter.this.getContext(), "Product added to cart", Toast.LENGTH_LONG).show();
                            // Product doesn't exist in cart, so add it
                            Shop newItem = new Shop();
                            newItem.setId(currentProduct.getId());
                            newItem.setImage(currentProduct.getImage());
                            newItem.setName(currentProduct.getName());
                            newItem.setPrice(currentProduct.getPrice());
                            newItem.setQuantity(1); // Only add 1 item initially
                            if (newItem.getId() != null) {
                                userCartRef.child(newItem.getId()).setValue(newItem);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        // Log the error
                    }
                });
            }
        });

        return itemView;
    }
}
