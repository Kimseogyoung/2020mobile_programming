package com.example.midtermproject;

import android.content.Context;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MyAdapter2 extends BaseAdapter {
    /* 아이템을 세트로 담기 위한 어레이 */
    private ArrayList<MyItem> mItems = new ArrayList<>();
    ArrayList<MyItem> newItems=new ArrayList<>();
    private int checkCount=0;
    private ArrayList<CheckBox> boxes =new ArrayList<>();
    @Override
    public int getCount() {
        return mItems.size();
    }

    @Override
    public MyItem getItem(int position) {
        return mItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }


    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        Context context = parent.getContext();

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.listview_custom2, parent, false);
        }


        /* 'listview_custom'에 정의된 위젯에 대한 참조 획득 */
        ImageView iv_img = (ImageView) convertView.findViewById(R.id.iv_img) ;//이미지
        TextView tv_name = (TextView) convertView.findViewById(R.id.tv_name) ;//이름
        TextView tv_price = (TextView) convertView.findViewById(R.id.tv_price) ;//가격

        Button removebtn=(Button)convertView.findViewById(R.id.removebtn);//삭제버튼
        Button upbutton=(Button)convertView.findViewById(R.id.numupbtn);//수량 +1
        Button downbutton=(Button)convertView.findViewById(R.id.numdownbtn);//수량 -1
        TextView num=(TextView)convertView.findViewById(R.id.numtext);//수량 텍스트

        final CheckBox checkBox=(CheckBox)convertView.findViewById(R.id.check);//체크박스
        boxes.add(checkBox);
        /* 각 리스트에 뿌려줄 아이템을 받아오는데 mMyItem 재활용 */
        final MyItem myItem = getItem(position);

        /* 각 위젯에 세팅된 아이템을 뿌려준다 */
        iv_img.setImageResource(myItem.getIcon());
        tv_name.setText(myItem.getName());
        tv_price.setText(myItem.getPrice()+"원");
        num.setText(""+myItem.getNum()+"");

        //체크박스 변경시 이벤트
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    getItem(position).setChecked(true);
                    checkCount++;
                    if(checkCount>=getCount()) ((shoppingcart)shoppingcart.mContext).changeCheck(true);
                }
                else if(b==false){
                    getItem(position).setChecked(false);
                    checkCount--;
                    ((shoppingcart)shoppingcart.mContext).changeCheck(false);
                }
                changeTotalPrice();
            }
        });
        if(getItem(position).Ischecked())
            checkBox.setChecked(true);
        else
            checkBox.setChecked(false);

        //수량 업버튼 클릭 이벤트
        upbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getItem(position).setNum(mItems.get(position).getNum()+1);
                Log.e("hi",""+(mItems.get(position)).getNum());
                changeTotalPrice();
                notifyDataSetChanged();

            }
        });
        //수량 다운버튼클릭시 이벤트
        downbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if((myItem.getNum()>0)){
                    getItem(position).setNum(mItems.get(position).getNum()-1);
                    changeTotalPrice();
                    notifyDataSetChanged();
                }
            }
        });

        removebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(mItems.get(position).Ischecked())checkCount--;//체크되어있었다면 체크카운트 --  체크되어있지않았다면 원래상관없으므로 패스
                mItems.remove(position);
                changeTotalPrice();
                notifyDataSetChanged();



            }
        });



        return convertView;
    }
    public ArrayList<CheckBox> getBoxes(){
        return boxes;
    }
    public ArrayList<MyItem> getmItems(){
        return mItems;
    }

    public void changeTotalPrice(){
        int i=0;
        for(MyItem m:mItems){
            if(m.Ischecked()){
                i+=m.getPrice()*m.getNum();
            }
        }
        ((shoppingcart)shoppingcart.mContext).setPrice(i);
    }
    /* 아이템 데이터 추가를 위한 함수. 자신이 원하는대로 작성 */
    public void addItem(int img, String name,int price, int num) {

        MyItem mItem = new MyItem();

        /* MyItem에 아이템을 setting한다. */
        mItem.setIcon(img);
        mItem.setName(name);
        mItem.setPrice(price);
        mItem.setNum(num);

        /* mItems에 MyItem을 추가한다. */
        mItems.add(mItem);
        checkCount++;

    }

}