package com.example.android.ordercoffee;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        if(quantity > 0){
            EditText name = (EditText)findViewById(R.id.nameText);
            Editable customerName = name.getText();
            createOrderSummary(customerName.toString(), quantity);
        }
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void display(int number) {
        TextView quantityTextView = (TextView) findViewById(
                R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

    public void increment(View view){
        quantity = quantity + 1;
        if(quantity <= 100) {
            display(quantity);
            String priceMessage = "Total: $" + calculateTotalPrice();   //"Total: $" + (quantity * 5);
        }else{
            Context context = getApplicationContext();
            CharSequence text =  getString(R.string.toastMsg);  //"You can order up to 100 coffees only.";
            int duration = Toast.LENGTH_SHORT;
            Toast.makeText(context,text,duration).show();
        }
    }
    public void decrement(View view){
        //int quantity = 1;
        if(quantity > 0) {
            quantity = quantity - 1;
            display(quantity);
            String priceMessage = "Total: $" + calculateTotalPrice();
        }else{
            return;
        }
    }

    private void createOrderSummary(String name , int quantity){
        CheckBox whippedCream = (CheckBox)(findViewById(R.id.whippedcream));
        Boolean chkValue =  whippedCream.isChecked();
        CheckBox choco = (CheckBox)(findViewById(R.id.choc0late));
        Boolean chkValueChoco =  choco.isChecked();
        String result = "Name: " + name + '\n' + "Add whipped cream? "+ chkValue + '\n' + "Add chocolate? " + chkValueChoco + '\n' + "Quantity: " + quantity + '\n' + "Total: $" + calculateTotalPrice() + '\n' + "Thank You!";

        /*

        Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("message/rfc822");
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Ordercoffee for " + name);
        emailIntent.putExtra(Intent.EXTRA_TEXT, result);
        emailIntent.putExtra(Intent.EXTRA_EMAIL,"jyotigupta275@gmail.com");
        if (emailIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(emailIntent);
        }
        */
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT,  "Ordercoffee for " + name);
        intent.putExtra(Intent.EXTRA_TEXT, result);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }

    }
    private int calculateTotalPrice(){
        int totalPrice = quantity * 5;
        CheckBox whippedCream = (CheckBox)(findViewById(R.id.whippedcream));
        Boolean chkValue =  whippedCream.isChecked();
        CheckBox choco = (CheckBox)(findViewById(R.id.choc0late));
        Boolean chkValueChoco =  choco.isChecked();

        if(chkValue && !chkValueChoco){
            totalPrice = totalPrice + quantity * 1;
        }else if(chkValueChoco && !chkValue){
            totalPrice = totalPrice + quantity * 2;
        }else if(chkValue && chkValueChoco){
            totalPrice = totalPrice + quantity * 3;
        }
        return totalPrice;
    }

}

