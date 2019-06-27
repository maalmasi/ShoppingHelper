package com.example.shoppinghelper;

        import android.content.Intent;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.View;
        import android.widget.Button;
        import android.widget.EditText;
        import android.widget.TextView;
        import android.widget.Toast;

        import com.google.firebase.database.DatabaseReference;
        import com.google.firebase.database.FirebaseDatabase;

public class AddToDatabaseActivity extends AppCompatActivity {

    String barcode, title, name, priceKonzum, priceSpar, priceTisak, op;
    EditText etName, etKonzum, etSpar, etTisak;
    Button btnAddToDatabase;
    TextView tvTitle;

    FirebaseDatabase mFirebase = FirebaseDatabase.getInstance();
    DatabaseReference mDatabase = mFirebase.getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_to_database);

        barcode = getIntent().getStringExtra("barcode");
        title = getIntent().getStringExtra("title");
        name = getIntent().getStringExtra("name");
        priceKonzum = getIntent().getStringExtra("priceKonzum");
        priceSpar = getIntent().getStringExtra("priceSpar");
        priceTisak = getIntent().getStringExtra("priceTisak");
        op = getIntent().getStringExtra("op");

        setUpUi();
    }

    private void setUpUi() {
        tvTitle = findViewById(R.id.tvTitle);
        tvTitle.setText(title);
        etName = findViewById(R.id.etAddName);
        etKonzum = findViewById(R.id.etAddKonzumPrice);
        etSpar = findViewById(R.id.etAddSparPrice);
        etTisak = findViewById(R.id.etAddTisakPrice);

        if(op.equals("update")){
            etName.setText(name);
            etKonzum.setText(priceKonzum);
            etSpar.setText(priceSpar);
            etTisak.setText(priceTisak);
        }

        btnAddToDatabase = findViewById(R.id.btnAddToDatabase);
        btnAddToDatabase.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = etName.getText().toString();
                String konzum = etKonzum.getText().toString();
                String spar = etSpar.getText().toString();
                String tisak = etTisak.getText().toString();

                if (name.equals("") || (konzum.equals("") && spar.equals("") && tisak.equals(""))){
                    Toast.makeText(AddToDatabaseActivity.this, "Fill out all fields", Toast.LENGTH_SHORT).show();
                }
                else{
                    Item item = new Item(name, konzum, spar, tisak);
                    mDatabase.child(barcode).setValue(item);
                    Intent intent = new Intent(AddToDatabaseActivity.this, PricesActivity.class);
                    intent.putExtra("barcode", barcode);
                    intent.putExtra("op", "picture");
                    startActivity(intent);
                }
            }
        });
    }
}
