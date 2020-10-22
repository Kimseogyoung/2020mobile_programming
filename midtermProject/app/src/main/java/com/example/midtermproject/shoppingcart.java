package com.example.midtermproject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.util.ArrayList;


public class shoppingcart extends Activity{
    private ArrayList<MyItem> Items=new ArrayList<>();
    private MyAdapter2 myAdapter2=new MyAdapter2();
    private ListView mListView;
    private int totalPrice = 0;
    public static Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shoppingcart);
        setTitle("짱 인형쇼핑몰");

        mListView = (ListView)findViewById(R.id.listView2);

        mContext = this;
        Intent inIntent = getIntent();
        Items=(ArrayList<MyItem>)inIntent.getSerializableExtra("shoppingcart");
        if((MyItem)inIntent.getSerializableExtra("buy")!=null){
            MyItem newitem=(MyItem)inIntent.getSerializableExtra("buy");
            int i=0;
            for(MyItem m:Items){
                if(m.getName().equals(newitem.getName())){
                    m.setNum(m.getNum()+1);
                    i=1;
                    break;
                }
            }
            if(i==0) Items.add(newitem);
        }




        for(MyItem m: Items){
            myAdapter2.addItem(m.getIcon(),m.getName(),m.getPrice(),m.getNum());
            totalPrice+=m.getPrice()*m.getNum();
        }
        mListView.setAdapter(myAdapter2);
        setPrice(totalPrice);


        Button homebtn=(Button)findViewById(R.id.homebtn);
        Button buyingbtn=(Button)findViewById(R.id.buyingbtn2);

        homebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent outIntent= new Intent(getApplicationContext(),MainActivity.class);
                Items=myAdapter2.getmItems();
                outIntent.putExtra("wheretomain",0);
                outIntent.putExtra("shoppingcartOut",Items);
                setResult(RESULT_OK,outIntent);
                finish();//현재 액티비티 종료
            }
        });

        buyingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent outIntent= new Intent(getApplicationContext(),buying.class);
                outIntent.addFlags(Intent.FLAG_ACTIVITY_FORWARD_RESULT);

                Items=myAdapter2.getmItems();
                ArrayList<MyItem> newitems=new ArrayList<>();
                for(MyItem m:Items){
                    if(m.Ischecked())newitems.add(m);
                }
                if(newitems.size()==0){
                    Toast.makeText(getApplicationContext(),"구매가능한 상품이 선택되지않았습니다.",Toast.LENGTH_SHORT).show();
                    return;
                }
                outIntent.putExtra("wheretobuy",1);//1==장바구니에서 구매버튼
                outIntent.putExtra("shoppingcartOut",newitems);
                outIntent.putExtra("totalprice",totalPrice);

                startActivity(outIntent);
                finish();

            }
        });

        final CheckBox allcheckbox=findViewById(R.id.allcheck);

        allcheckbox.setOnClickListener(new View.OnClickListener() {
            //MyAdapter2.java파일에서 모두체크시,하나라도 체크아닐시를 확인하고 allcheckbox값을 바꿔야하는데
            // setOnCheckedChangeListener쓰면 변경 이벤트가 바로 또 나타나서 두번 실행되서 이상해짐. 따라서 온클릭 사용
            @Override
            public void onClick(View view) {
                for(MyItem c:myAdapter2.getmItems()){
                    c.setChecked(allcheckbox.isChecked());

                }
                myAdapter2.notifyDataSetChanged();
            }
        });




    }

    public void setPrice(int num){
        totalPrice=num;
        TextView totalText = findViewById(R.id.totalPrice1);
        totalText.setText("결제총합 : "+totalPrice+"원");
    }
    public void changeCheck(boolean t){
        CheckBox allcheckbox=findViewById(R.id.allcheck);
        allcheckbox.setChecked(t);
    }
}
