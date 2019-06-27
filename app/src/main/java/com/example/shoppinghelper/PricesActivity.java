package com.example.shoppinghelper;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class PricesActivity extends AppCompatActivity {

    private static final String TAG = "ViewDatabase";

    String barcode, productName, priceKonzum, priceSpar, priceTisak, type, searchQuery;
    TextView tvBarcode, tvProductName, tvPriceKonzum, tvPriceSpar, tvPriceTisak;
    Button btnUpdateProduct, btnAddProduct;

    FirebaseDatabase mFirebase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = mFirebase.getReference();
    DatabaseReference barRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prices);

        barcode = getIntent().getStringExtra("barcode");
        type = getIntent().getStringExtra("op");
        searchQuery = getIntent().getStringExtra("query");

        setUpUi();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (type.equals("picture")){
                    barRef = mDatabase.child(barcode);
                    barRef.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            Item item = new Item();
                            item.setName(dataSnapshot.child("name").getValue(String.class));
                            item.setKonzum(dataSnapshot.child("konzum").getValue(String.class));
                            item.setSpar(dataSnapshot.child("spar").getValue(String.class));
                            item.setTisak(dataSnapshot.child("tisak").getValue(String.class));
                            productName = item.getName();
                            priceKonzum = item.getKonzum();
                            priceSpar = item.getSpar();
                            priceTisak = item.getTisak();

                            showData();
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
                else if (type.equals("string")){
                    mDatabase.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot ds : dataSnapshot.getChildren()) {
                                String name = ds.child("name").getValue(String.class);
                                if (name.contains(searchQuery)){
                                    Item item = new Item();
                                    item.setName(ds.child("name").getValue(String.class));
                                    item.setKonzum(ds.child("konzum").getValue(String.class));
                                    item.setSpar(ds.child("spar").getValue(String.class));
                                    item.setTisak(ds.child("tisak").getValue(String.class));
                                    barcode = ds.getKey();
                                    productName = item.getName();
                                    priceKonzum = item.getKonzum();
                                    priceSpar = item.getSpar();
                                    priceTisak = item.getTisak();


                                    showData();
                                }
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }


            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w(TAG, "Failed to read value.", error.toException());
            }
        });
    }

    private void showData() {
        String showName = getResources().getString(R.string.name);
        String showBarcode = getResources().getString(R.string.barcode);
        String barcodeFinal = showBarcode + " " + barcode;
        String nameFinal = showName + " " + productName;
        tvBarcode.setText(barcodeFinal);
        tvProductName.setText(nameFinal);
        tvPriceKonzum.setText(priceKonzum);
        tvPriceSpar.setText(priceSpar);
        tvPriceTisak.setText(priceTisak);
        if (productName == null){
//            btnAddProduct.setVisibility(View.VISIBLE);
            Intent intent = new Intent(PricesActivity.this, AddToDatabaseActivity.class);
            intent.putExtra("barcode", barcode);
            intent.putExtra("title", "Add to database");
            intent.putExtra("op", "add");
            startActivity(intent);
        }
    }

    private void setUpUi() {
        tvBarcode = findViewById(R.id.tvProductBarcode);
        tvProductName = findViewById(R.id.tvProductName);
        tvPriceKonzum = findViewById(R.id.tvPriceKonzum);
        tvPriceSpar = findViewById(R.id.tvPriceSpar);
        tvPriceTisak = findViewById(R.id.tvPriceTisak);
        btnAddProduct = findViewById(R.id.btnAddProduct);
        btnUpdateProduct = findViewById(R.id.btnUpdateProduct);

        btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PricesActivity.this, AddToDatabaseActivity.class);
                intent.putExtra("barcode", barcode);
                intent.putExtra("title", "Add to database");
                intent.putExtra("op", "add");
                startActivity(intent);
            }
        });

        btnUpdateProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PricesActivity.this, AddToDatabaseActivity.class);
                intent.putExtra("barcode", barcode);
                intent.putExtra("title", "Update product details");
                intent.putExtra("name", productName);
                intent.putExtra("priceKonzum", priceKonzum);
                intent.putExtra("priceSpar", priceSpar);
                intent.putExtra("priceTisak", priceTisak);
                intent.putExtra("op", "update");
                startActivity(intent);
            }
        });
    }
}
