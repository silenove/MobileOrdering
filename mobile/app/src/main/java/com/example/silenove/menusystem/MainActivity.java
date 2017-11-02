package com.example.silenove.menusystem;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

public class MainActivity extends Activity implements View.OnClickListener,AdapterView.OnItemSelectedListener{

    private ViewPager  appViewPager;
    private PagerAdapter appPagerAdapter;
    private List<View> appViews = new ArrayList<View>();
    private List<Info> DishList = new ArrayList<>();
    private List<Info> StapleList = new ArrayList<>();
    private List<Info> CakeList = new ArrayList<>();
    private List<Info> DrinkList = new ArrayList<>();

//tab
    private LinearLayout appTabDish;
    private LinearLayout appTabStaple;
    private LinearLayout appTabCake;
    private LinearLayout appTabDrink;
//imagebutton
    private ImageButton appDishImg;
    private ImageButton appStapleImg;
    private ImageButton appCakeImg;
    private ImageButton appDrinkImg;
//LsitView
    private ListView appDishlv;
    private ListView appStaplelv;
    private ListView appCakelv;
    private ListView appDrinklv;

    //Spinner
    private Spinner appSpinner;
    private List<SpinnerInfo> spinnerInfos= new ArrayList<>();

    //selected
    private List<SelectedInfo> selectedInfos = new ArrayList<>();
    private int amount;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initAppViews();        //初始ViewPager等显示控件
        initSpinner();         //初始Spinner
        initEvents();          //初始ViewPager点击事件

    }

    private void initAppViews(){
        //ViewPager
        appViewPager = (ViewPager)findViewById(R.id.id_viewpager);
        LayoutInflater appInflater = LayoutInflater.from(this);
        View tabDish = appInflater.inflate(R.layout.view_dish, null);
        appViews.add(tabDish);
        View tabStaple = appInflater.inflate(R.layout.view_staple,null);
        appViews.add(tabStaple);
        View tabCake = appInflater.inflate(R.layout.view_cake,null);
        appViews.add(tabCake);
        View tabDrink = appInflater.inflate(R.layout.view_drink,null);
        appViews.add(tabDrink);

        //Tab
        appTabDish = (LinearLayout)findViewById(R.id.id_tab_dish);
        appTabStaple = (LinearLayout)findViewById(R.id.id_tab_staple);
        appTabCake = (LinearLayout)findViewById(R.id.id_tab_cake);
        appTabDrink = (LinearLayout)findViewById(R.id.id_tab_drink);

        appDishlv = (ListView) tabDish.findViewById(R.id.id_listview_dish);
        appStaplelv = (ListView) tabStaple.findViewById(R.id.id_listview_staple);
        appCakelv = (ListView) tabCake.findViewById(R.id.id_listview_cake);
        appDrinklv = (ListView) tabDrink.findViewById(R.id.id_listview_drink);

        appDishImg = (ImageButton)findViewById(R.id.id_tab_dish_img);
        appStapleImg = (ImageButton)findViewById(R.id.id_tab_staple_img);
        appCakeImg = (ImageButton)findViewById(R.id.id_tab_cake_img);
        appDrinkImg = (ImageButton)findViewById(R.id.id_tab_drink_img);


        //初始ListView
        initLists();


        appPagerAdapter = new PagerAdapter() {

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = appViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(appViews.get(position));
            }

            @Override
            public int getCount() {
                return appViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        appViewPager.setAdapter(appPagerAdapter);
        appViewPager.setOffscreenPageLimit(3);

    }

    //初始ListView，添加商品信息
    private void initLists() {
        DishList.add(new Info(R.drawable.feiliniupai, "菲力牛排" , "239"));
        DishList.add(new Info(R.drawable.xilengniupai, "西冷牛排" , "219"));
        DishList.add(new Info(R.drawable.niuxiaopai, "牛小排" , "199"));
        DishList.add(new Info(R.drawable.shucaishala, "蔬菜沙拉" , "25"));
        DishList.add(new Info(R.drawable.jiroushucaishala, "鸡肉蔬菜沙拉" , "32"));
        DishList.add(new Info(R.drawable.youcuzhishala, "油醋汁沙拉" , "24"));
        DishList.add(new Info(R.drawable.jinqiangyuniuyouguoshala, "金枪鱼牛油果沙拉" , "51"));
        DishList.add(new Info(R.drawable.suannaishuiguoshala, "酸奶水果沙拉" , "33"));
        DishList.add(new Info(R.drawable.mizhijichi, "蜜汁鸡翅" , "28"));
        DishList.add(new Info(R.drawable.suanxiangjichi, "蒜香鸡翅" , "27"));
        DishList.add(new Info(R.drawable.suanxiangjianyangpai, "蒜香煎羊排" , "77"));
        DishList.add(new Info(R.drawable.naiyoumogutang, "奶油蘑菇汤" , "46"));
        StapleList.add(new Info(R.drawable.baocuipisa, "薄脆披萨" , "159"));
        StapleList.add(new Info(R.drawable.hailuzhizunpisa, "海陆至尊披萨" , "179"));
        StapleList.add(new Info(R.drawable.peigenhuotuipisa, "培根火腿披萨" , "139"));
        StapleList.add(new Info(R.drawable.xianxiapeigenbaopisa, "鲜虾培根薄披萨" , "189"));
        StapleList.add(new Info(R.drawable.huotuipisa, "火腿披萨" , "129"));
        StapleList.add(new Info(R.drawable.zhishiliulianpisa,  "芝士榴莲披萨" , "145"));
        StapleList.add(new Info(R.drawable.niurouhanbao,  "牛肉汉堡" , "87"));
        StapleList.add(new Info(R.drawable.shuangcenroubingjishihanbao,  "双层肉饼吉士汉堡" , "109"));
        StapleList.add(new Info(R.drawable.wudihanbao,  "无敌汉堡" , "89"));
        StapleList.add(new Info(R.drawable.yumijirouhanbao, "玉米鸡肉汉堡" , "59"));
        StapleList.add(new Info(R.drawable.zhishiniuroupingguominihanbao, "芝士牛肉迷你汉堡" ,  "119"));
        CakeList.add(new Info(R.drawable.tilamisu,"提拉米苏" , "89"));
        CakeList.add(new Info(R.drawable.heibaimusi,"黑白慕斯" , "79"));
        CakeList.add(new Info(R.drawable.paiduiwuyu,"派对物语" , "77"));
        CakeList.add(new Info(R.drawable.huanyoushijie,"环游世界" , "99"));
        CakeList.add(new Info(R.drawable.kafeiwolutuo,"咖啡沃鲁托" , "66"));
        CakeList.add(new Info(R.drawable.nuoxinhuayuan,"诺心花园" , "59"));
        CakeList.add(new Info(R.drawable.xueyuniuruzhishi,"雪域牛乳芝士" , "85"));
        DrinkList.add(new Info(R.drawable.kabuqinuo,"卡布奇诺" , "45"));
        DrinkList.add(new Info(R.drawable.moka,"摩卡咖啡" , "42"));
        DrinkList.add(new Info(R.drawable.weiyenakafei,"维也纳咖啡" , "47"));
        DrinkList.add(new Info(R.drawable.maqiyaduo,"玛琪雅朵" , "53"));
        DrinkList.add(new Info(R.drawable.natie,"拿铁" , "39"));
        DrinkList.add(new Info(R.drawable.cola,"可乐" , "8"));
        DrinkList.add(new Info(R.drawable.bingzhenguozhilangmujiu,"冰镇果汁朗姆酒" , "28"));
        DrinkList.add(new Info(R.drawable.shengnvguomangguoguozhi,"圣女果芒果果汁" , "15"));
        DrinkList.add(new Info(R.drawable.xiyoupingguozhi,"西柚苹果汁" , "12"));

        appDishlv.setAdapter(new ListAdapter(DishList, this));
        appStaplelv.setAdapter(new ListAdapter(StapleList, this));
        appCakelv.setAdapter(new ListAdapter(CakeList,this));
        appDrinklv.setAdapter(new ListAdapter(DrinkList,this));
    }

    private void initSpinner() {
        appSpinner = (Spinner) findViewById(R.id.id_spinner);

        spinnerInfos.add(new SpinnerInfo(R.drawable.spinner_menu3, "菜单"));
        spinnerInfos.add(new SpinnerInfo(R.drawable.spinner_selected3, "购物车"));
        spinnerInfos.add(new SpinnerInfo(R.drawable.spinner_about, "关于"));

        appSpinner.setAdapter(new SpinnerAdapter(spinnerInfos, this));
        appSpinner.setOnItemSelectedListener(this);

    }



    private void initEvents(){
        appTabDish.setOnClickListener(this);
        appTabStaple.setOnClickListener(this);
        appTabCake.setOnClickListener(this);
        appTabDrink.setOnClickListener(this);

        appViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                int currentItem = appViewPager.getCurrentItem();
                restImg();
                switch (currentItem) {
                    case 0:
                        appDishImg.setImageResource(R.drawable.dish_pressed);
                        break;
                    case 1:
                        appStapleImg.setImageResource(R.drawable.staple_pressed);
                        break;
                    case 2:
                        appCakeImg.setImageResource(R.drawable.cake_pressed);
                        break;
                    case 3:
                        appDrinkImg.setImageResource(R.drawable.drink_pressed);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){
            case R.id.id_tab_dish:
                appViewPager.setCurrentItem(0);
                break;
            case R.id.id_tab_staple:
                appViewPager.setCurrentItem(1);
                break;
            case R.id.id_tab_cake:
                appViewPager.setCurrentItem(2);
                break;
            case R.id.id_tab_drink:
                appViewPager.setCurrentItem(3);
                break;
            default:break;
        }
    }

    private void restImg(){    //重置ImageButton
        appDishImg.setImageResource(R.drawable.dish2);
        appStapleImg.setImageResource(R.drawable.staple2);
        appCakeImg.setImageResource(R.drawable.cake2);
        appDrinkImg.setImageResource(R.drawable.drink2);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        SpinnerInfo info = spinnerInfos.get(position);
        String tab = info.getTab();
        if(tab == "购物车"){
            amount = 0;

            packed();

            if(selectedInfos.size() != 0) {    //activity的跳转，显示购物车界面

                Intent intent = new Intent(this, Main2Activity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("selectedInfos", (Serializable) selectedInfos);
                bundle.putInt("amount",amount);
                intent.putExtras(bundle);
                startActivity(intent);
            }else{                 //提示为选择商品，不显示购物车界面
                new AlertDialog.Builder(this)
                        .setTitle("提示")
                        .setMessage("您还未选择商品！")
                        .setPositiveButton("返回",null)
                        .show();
            }

        }

        if(tab == "关于"){
            new AlertDialog.Builder(this)
                    .setTitle("关于我们")
                    .setMessage("开发人员：文涛同学 ， 宇翔同学 \n 版权所有，翻版必究")
                    .setPositiveButton("确定",null)
                    .show();

        }

        try {    //实现Spinner中的items重复点击时可执行点击事件
            Field field = AdapterView.class.getDeclaredField("mOldSelectedPosition");
            field.setAccessible(true);	//设置mOldSelectedPosition可访问
            field.setInt(appSpinner, AdapterView.INVALID_POSITION); //设置mOldSelectedPosition的值
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    private void packed(){
        selectedInfos.removeAll(selectedInfos);
        for(int i=0;i<appDishlv.getChildCount();i++){
            View childview = appDishlv.getChildAt(i);
            TextView infoText = (TextView) childview.findViewById(R.id.id_grid_info);
            TextView priceText = (TextView) childview.findViewById(R.id.id_grid_price);
            TextView numberText = (TextView) childview.findViewById(R.id.id_grid_number);
            String Sinfo = infoText.getText().toString();
            String  Sprice = priceText.getText().toString();
            String  Snumber = numberText.getText().toString();
            Integer price =  Integer.parseInt(Sprice.trim());
            Integer number =  Integer.parseInt(Snumber.trim());
            if(number != 0){
                amount += price * number;
                selectedInfos.add(new SelectedInfo(DishList.get(i).getMyImgid(),Sinfo,Snumber));
            }
        }

        for(int i=0;i<appStaplelv.getChildCount();i++){
            View childview = appStaplelv.getChildAt(i);
            TextView infoText = (TextView) childview.findViewById(R.id.id_grid_info);
            TextView priceText = (TextView) childview.findViewById(R.id.id_grid_price);
            TextView numberText = (TextView) childview.findViewById(R.id.id_grid_number);
            String Sinfo = infoText.getText().toString();
            String  Sprice = priceText.getText().toString();
            String  Snumber = numberText.getText().toString();
            Integer price =  Integer.parseInt(Sprice.trim());
            Integer number =  Integer.parseInt(Snumber.trim());
            if(number != 0){
                amount += price * number;
                selectedInfos.add(new SelectedInfo(StapleList.get(i).getMyImgid(),Sinfo,Snumber));
            }
        }


        for(int i=0;i<appCakelv.getChildCount();i++){
            View childview = appCakelv.getChildAt(i);
            TextView infoText = (TextView) childview.findViewById(R.id.id_grid_info);
            TextView priceText = (TextView) childview.findViewById(R.id.id_grid_price);
            TextView numberText = (TextView) childview.findViewById(R.id.id_grid_number);
            String Sinfo = infoText.getText().toString();
            String  Sprice = priceText.getText().toString();
            String  Snumber = numberText.getText().toString();
            Integer price =  Integer.parseInt(Sprice.trim());
            Integer number =  Integer.parseInt(Snumber.trim());
            if(number != 0){
                amount += price * number;
                selectedInfos.add(new SelectedInfo(CakeList.get(i).getMyImgid(),Sinfo,Snumber));
            }
        }


        for(int i=0;i<appDrinklv.getChildCount();i++){
            View childview = appDrinklv.getChildAt(i);
            TextView infoText = (TextView) childview.findViewById(R.id.id_grid_info);
            TextView priceText = (TextView) childview.findViewById(R.id.id_grid_price);
            TextView numberText = (TextView) childview.findViewById(R.id.id_grid_number);
            String Sinfo = infoText.getText().toString();
            String  Sprice = priceText.getText().toString();
            String  Snumber = numberText.getText().toString();
            Integer price =  Integer.parseInt(Sprice.trim());
            Integer number =  Integer.parseInt(Snumber.trim());
            if(number != 0){
                amount += price * number;
                selectedInfos.add(new SelectedInfo(DrinkList.get(i).getMyImgid(),Sinfo,Snumber));
            }
        }

    }
}
