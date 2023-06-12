package com.example.firebase.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.firebase.R;
import com.example.firebase.Shop;
import com.example.firebase.ShopAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class shopFragment extends Fragment {

    private ListView listViewProducts;
    private List<Shop> productList;
    private DatabaseReference databaseReference;
    private ShopAdapter adapter;
    private FirebaseUser currentUser;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_shop, container, false);

        listViewProducts = root.findViewById(R.id.listView_products);
        productList = new ArrayList<>();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("products");
        currentUser = FirebaseAuth.getInstance().getCurrentUser();

        adapter = new ShopAdapter(requireContext(), productList);
        listViewProducts.setAdapter(adapter);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("products");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                productList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Shop product = snapshot.getValue(Shop.class);
                    if (product != null) {
                        product.setId(snapshot.getKey());
                        productList.add(product);
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });


        // Set item click listener
        listViewProducts.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Handle item click
                Shop selectedProduct = productList.get(position);
                // Add the selected product to the cart
                addToCart(selectedProduct);
            }
        });

        return root;
    }

    private void addToCart(Shop product) {
        if (currentUser != null) {
            // Get the current user ID
            String userId = currentUser.getUid();

            // Get a reference to the user's cart in the database
            DatabaseReference userCartRef = FirebaseDatabase.getInstance().getReference()
                    .child("users")
                    .child(userId)
                    .child("cart");

            // Generate a unique key for the product in the cart
            String productId = userCartRef.push().getKey();

            // Update the product id before storing it


            // Add the product to the user's cart
            userCartRef.child(productId).setValue(product, new DatabaseReference.CompletionListener() {
                @Override
                public void onComplete(DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                    if (databaseError == null) {
                        // Show a success message
                        Toast.makeText(requireContext(), "Product added to cart", Toast.LENGTH_SHORT).show();
                    } else {
                        // Show error message
                        Toast.makeText(requireContext(), "Failed to add product", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        } else {
            // User is not logged in, handle accordingly
            Toast.makeText(requireContext(), "Please log in to add products to cart", Toast.LENGTH_SHORT).show();
        }
    }
}
