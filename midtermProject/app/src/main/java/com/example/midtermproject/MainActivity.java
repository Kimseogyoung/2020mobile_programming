package com.example.midtermproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;

import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ListView mListView;
    private MyItem selectedItem;
    private ArrayList<MyItem> ItemsInCart = new ArrayList<>();
    MyAdapter mMyAdapter = new MyAdapter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("짱 인형쇼핑몰");

        /* 위젯과 멤버변수 참조 획득 */
        mListView = (ListView)findViewById(R.id.listView);
        mListView.setChoiceMode(ListView.CHOICE_MODE_SINGLE);//한개 선택가능

        //mListView.setItemChecked();

        final RelativeLayout buyingTap=(RelativeLayout)findViewById(R.id.buyingTap);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            View lastview;
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                if(lastview!=null)lastview.setBackgroundColor(Color.WHITE);

                view.setBackgroundColor(Color.parseColor("#FFDEDE"));
                buyingTap.setVisibility(View.VISIBLE);
                selectedItem = mMyAdapter.getItem(position);
                Log.e("hi","클릭");
                lastview=view;

            }
        });
        Button normalshoppingbtn = (Button)findViewById(R.id.shoppingcartBtn0);
        Button shoppingbtn = (Button)findViewById(R.id.shoppingcartBtn);
        Button buyingbtn = (Button)findViewById(R.id.buyingbtn1);
        Button backbtn= (Button)findViewById(R.id.backbtn);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buyingTap.setVisibility(View.INVISIBLE);
            }
        });
        //구매액티비티
        buyingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), buying.class);
                intent.putExtra("buy",selectedItem);
                intent.putExtra("wheretobuy",0);//0이면 main버튼에서 구매버튼
                startActivityForResult(intent,0);
            }
        });

        //장바구니액티비티
        shoppingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getApplicationContext(), shoppingcart.class);
                intent.putExtra("shoppingcart",ItemsInCart);
                intent.putExtra("buy",selectedItem);
                startActivityForResult(intent,0);

            }
        });
        normalshoppingbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), shoppingcart.class);
                intent.putExtra("shoppingcart",ItemsInCart);
                startActivityForResult(intent,0);
            }
        });




        /* 아이템 추가 및 어댑터 등록 */
        dataSetting();
    }

    private void dataSetting(){

        mMyAdapter.addItem(R.drawable.image1, "[특 대형]곰인형 빅사이즈", "짱크고 귀여운 인형입니다. 애인에게 선물하세요",55000);
        mMyAdapter.addItem(R.drawable.image2, "나비보벳따우~ 강아지 인형", "노래하는 귀여운 인형입니다.",10000);
        mMyAdapter.addItem(R.drawable.image3, "라이언 인형", "눈썹이 매력적인 귀여운 인형입니다.",30000);
        mMyAdapter.addItem(R.drawable.image4, "[넷마블X짱쇼핑몰]캐릭터 인형", "왠지 이 인형을 보기만하면.. 어떤게임 인트로 사운드가 생각나!",35000);
        mMyAdapter.addItem(R.drawable.image5, "꿀잼 티모인형 ", "꿀밤 백대 때려주고 싶을 만큼 귀엽다!",59000);
        mMyAdapter.addItem(R.drawable.image6, "[겨울궁전] 엘사 미니돌", "프리미엄 인형입니다. 직접 인형을 꾸미는 재미가 있는 인형.",129000);
        mMyAdapter.addItem(R.drawable.image7, "블링블링 공주세트", "인형 구매시 공주 궁전 증정이벤트 실시! 공주왕자아이들에게 선물하세요.",69000);
        mMyAdapter.addItem(R.drawable.image8, "쭈니", "갖고싶다.",90000);
        mMyAdapter.addItem(R.drawable.image9, "인형", "못생긴 인형순위 1위",10000);

        /* 리스트뷰에 어댑터 등록 */
        mListView.setAdapter(mMyAdapter);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode ==RESULT_OK){
            Log.e("hi", data.getIntExtra("wheretomain",0)+"");
            if(data.getIntExtra("wheretomain",0)==0) {
                ItemsInCart = (ArrayList<MyItem>) data.getSerializableExtra("shoppingcartOut");
            }
            else if(data.getIntExtra("wheretomain",0)==1){
                Log.e("hi", "bybebyeybey");
                ItemsInCart.clear();
                String a=data.getStringExtra("ordermessage");
                Toast.makeText(getApplicationContext(),a,Toast.LENGTH_SHORT).show();
            }


        }

    }
}
