package com.example.firebase.ui.Cart;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.firebase.CartAdapter;
import com.example.firebase.R;
import com.example.firebase.Shop;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment {

    private ListView listViewCart;
    private CartAdapter adapter;
    private List<Shop> cartItemList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        listViewCart = view.findViewById(R.id.listView_cart);
        adapter = new CartAdapter(getActivity(), cartItemList);
        listViewCart.setAdapter(adapter);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String userId = sharedPreferences.getString("phone", "");

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference()
                .child("users")
                .child(userId)
                .child("cart");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                cartItemList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Shop cartItem = snapshot.getValue(Shop.class);
                    if (cartItem != null) {
                        cartItemList.add(cartItem);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Log the error
            }
        });

        // Clear Cart button
        Button buttonClearCart = view.findViewById(R.id.buttonClearCart);
        buttonClearCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference()
                        .child("users")
                        .child(userId)
                        .child("cart");

                DatabaseReference productRef = FirebaseDatabase.getInstance().getReference()
                        .child("products");

                // Get all items in the cart
                cartRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            Shop cartItem = snapshot.getValue(Shop.class);
                            if (cartItem != null) {
                                String productId = cartItem.getId();
                                int quantityInCart = cartItem.getQuantity();

                                // Get the corresponding product from the product list
                                productRef.child(productId).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot productSnapshot) {
                                        Shop product = productSnapshot.getValue(Shop.class);
                                        if (product != null) {
                                            if (product.getQuantity() < quantityInCart) {
                                                // Remove the item from the cart
                                                cartRef.child(productId).removeValue();

                                                // Display a message to the user about the out-of-stock product
                                                Toast.makeText(getActivity(), product.getName() + " is out of stock and removed from your cart.", Toast.LENGTH_SHORT).show();
                                            } else {
                                                // Deduct the product quantity by the quantity in the cart
                                                product.setQuantity(product.getQuantity() - quantityInCart);
                                                // Update the product in the database
                                                productRef.child(productId).setValue(product);

                                                // Remove the item from the cart
                                                cartRef.child(productId).removeValue();
                                            }
                                        }
                                    }

                                    @Override
                                    public void onCancelled(@NonNull DatabaseError databaseError) {
                                        // Log the error
                                    }
                                });
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

        return view;
    }
}