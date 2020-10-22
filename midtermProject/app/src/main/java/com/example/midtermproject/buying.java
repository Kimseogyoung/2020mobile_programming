package com.example.midtermproject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;


import java.util.ArrayList;

public class buying extends Activity {
    ArrayList<MyItem> Items= new ArrayList<>();
    int totalPrice;
    String waytopay="무통장 입금";
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buying);
        setTitle("짱 인형쇼핑몰");

        totalPrice=0;


        Intent inIntent = getIntent();
        if(inIntent.getIntExtra("wheretobuy",0)==0){
            //main to buy
            Items.add((MyItem)inIntent.getSerializableExtra("buy"));
            Items.get(0).setNum(1);
            totalPrice=Items.get(0).getPrice();
        }
        else if(inIntent.getIntExtra("wheretobuy",0)==1){
            //cart to buy
            Items = (ArrayList<MyItem>)inIntent.getSerializableExtra("shoppingcartOut");
            totalPrice=inIntent.getIntExtra("totalprice",0);
        }

        TextView itemList=(TextView)findViewById(R.id.orderedItems);
        TextView totalText=(TextView)findViewById(R.id.totalPrice2);
        Button orderbtn=(Button)findViewById(R.id.buyingbtn3);
        RadioGroup radioGroup=(RadioGroup)findViewById(R.id.radiogroup);
        RadioButton rdo=(RadioButton)findViewById(R.id.rd1);
        final CheckBox finalcheck=(CheckBox)findViewById(R.id.essential_check);//결제동의
        rdo.setChecked(true);

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(RadioGroup radioGroup, int i) {
                    RadioButton rd=(RadioButton)findViewById(radioGroup.getCheckedRadioButtonId());
                    waytopay = rd.getText().toString();
                }
        });

        String text="";
        for(MyItem m: Items){
            text += m.getName()+" - "+m.getPrice()+"원 "+m.getNum()+"개\n";
        }
        itemList.setText(text);

        totalText.setText("결제 총합 : "+totalPrice);

        orderbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(finalcheck.isChecked()==false){Toast.makeText(getApplicationContext(),"결제동의에 체크해주세요.",Toast.LENGTH_SHORT).show(); return;}

                Intent outIntent= new Intent();
                String message=Items.get(0).getName()+" 외"+(Items.size()-1)+" 개의 상품을\n"+totalPrice+" 원에 "+waytopay+" 결제 하셨습니다.";
                Items.clear();

                outIntent.putExtra("wheretomain",1);
                outIntent.putExtra("ordermessage",message);
                setResult(RESULT_OK,outIntent);
                finish();
            }
        });




    }

}
