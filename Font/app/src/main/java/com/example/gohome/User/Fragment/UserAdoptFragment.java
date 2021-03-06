package com.example.gohome.User.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.gohome.Entity.AdoptInfo;
import com.example.gohome.Entity.ResponseAdoptInfo;
import com.example.gohome.R;
import com.example.gohome.User.Adapter.FoldingCellListAdapter;
import com.google.gson.Gson;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.qlh.dropdownmenu.DropDownMenu;
import com.qlh.dropdownmenu.view.MultiMenusView;
import com.qlh.dropdownmenu.view.SingleMenuView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class UserAdoptFragment extends Fragment {

    private static final int SUCCESS = 1;
    private static final int FAIL = -1;
    private static final int ZERO = 0; //记录请求回来的数据条数是否为零
    private static final int PAGE_SIZE = 6;
    private static int CUR_PAGE_NUM = 1;

    private View rootView;

    private XRecyclerView recyclerView;
    private FoldingCellListAdapter adapter;

    private int times = 0; // 记录加载更多次数

    private List<AdoptInfo> infoList;

    private String description1 = "性格乖巧，比较黏人，已驱虫。希望找一个爱它的主人，有责任心不抛弃，接受定期回访。";
    private String description2 = "有偿领养，疫苗已打，未绝育，有证，定期内外驱虫。性格活泼，能吃好动。希望能找到一个对它好的有爱心有经验的铲屎官。";
    private String description3 = "要求：有时间陪伴，有病治病，吃安全狗粮，有耐心。基本情况：不在家大小便，必须出去上，不破坏东西，很听话。";
    private String description4 = "身体健康，做了狂犬和驱虫，性格乖巧粘人。最近在发情期还未绝育，希望你能带去配种或绝育。";

    private DropDownMenu mDropDownMenu;
    private MultiMenusView multiMenusView;//多级菜单
    private SingleMenuView singleMenuView1;//单级菜单
    private SingleMenuView singleMenuView2;//单级菜单

    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView != null) {
            ViewGroup parent = (ViewGroup) rootView.getParent();
            if (parent != null) {
                parent.removeView(rootView);
            }
        } else {
            rootView = inflater.inflate(R.layout.fragment_user_adopt, null);
            initDropMenus();
            getData();
            initRecyclerView();
        }
        return rootView;
    }

    private void initRecyclerView() {

        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        adapter = new FoldingCellListAdapter(getContext(), infoList);

        recyclerView = rootView.findViewById(R.id.user_lv_adopt);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setArrowImageView(R.drawable.iconfont_downgrey);
        recyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        recyclerView.getDefaultRefreshHeaderView().setRefreshTimeVisible(true);
        recyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);

        recyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(() -> {
                    @SuppressLint("HandlerLeak")
                    Handler handler = new Handler(){
                        @Override
                        public void handleMessage(Message msg){
                            switch (msg.what){
                                case SUCCESS:
                                    Log.i("刷新", "成功");
                                    adapter.setList(infoList);
                                    adapter.notifyDataSetChanged();
                                    break;
                                case FAIL:
                                    Log.i("刷新", "失败");
                                    break;
                                case ZERO:
                                    Log.i("刷新", "0");
                                    break;
                            }
                            recyclerView.refreshComplete();
                        }
                    };

                    new Thread(()->{
                        CUR_PAGE_NUM = 1;
                        Request request = new Request.Builder()
                                .url(getResources().getString(R.string.serverBasePath) +
                                        getResources().getString(R.string.getAdoptMessage)
                                        + "/?pageNum="+ CUR_PAGE_NUM +"&pageSize="+ PAGE_SIZE +"&state=0")
                                .get()
                                .build();
                        Message msg = new Message();
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Call call = okHttpClient.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i("获取: ", e.getMessage());
                                msg.what = FAIL;
                                handler.sendMessage(msg);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                ResponseAdoptInfo adoptMessage = new Gson().fromJson(response.body().string(),
                                        ResponseAdoptInfo.class);
                                infoList = adoptMessage.getAdoptInfoList();
                                if(infoList.size() == 0) {
                                    msg.what = ZERO;
                                } else {
                                    msg.what = SUCCESS;
                                }
                                handler.sendMessage(msg);
                                Log.i("获取: ", String.valueOf(infoList.size()));
                            }
                        });
                    }).start();
                }, 1500);
            }

            @Override
            public void onLoadMore() {
                new Handler().postDelayed(() -> {
                    @SuppressLint("HandlerLeak")
                    Handler handler = new Handler(){
                        @Override
                        public void handleMessage(Message msg){
                            recyclerView.loadMoreComplete();
                            switch (msg.what){
                                case SUCCESS:
                                    Log.i("加载", "成功");
                                    adapter.notifyDataSetChanged();
                                    break;
                                case FAIL:
                                    Log.i("加载", "失败");
                                    break;
                                case ZERO:
                                    Log.i("加载", "0");
                                    recyclerView.setNoMore(true);
                                    break;
                            }
                        }
                    };

                    new Thread(()->{
                        CUR_PAGE_NUM++;
                        Request request = new Request.Builder()
                                .url(getResources().getString(R.string.serverBasePath) +
                                        getResources().getString(R.string.getAdoptMessage)
                                        + "/?pageNum="+ CUR_PAGE_NUM +"&pageSize=" + PAGE_SIZE + "&state=0")
                                .get()
                                .build();
                        Message msg = new Message();
                        OkHttpClient okHttpClient = new OkHttpClient();
                        Call call = okHttpClient.newCall(request);
                        call.enqueue(new Callback() {
                            @Override
                            public void onFailure(Call call, IOException e) {
                                Log.i("获取: ", e.getMessage());
                                msg.what = FAIL;
                                handler.sendMessage(msg);
                            }

                            @Override
                            public void onResponse(Call call, Response response) throws IOException {

                                ResponseAdoptInfo adoptMessage = new Gson().fromJson(response.body().string(),
                                        ResponseAdoptInfo.class);
                                infoList.addAll(adoptMessage.getAdoptInfoList());
                                if(CUR_PAGE_NUM  * PAGE_SIZE  >= adoptMessage.getTotal() ){
                                    msg.what = ZERO;
                                } else {
                                    msg.what = SUCCESS;
                                }
                                handler.sendMessage(msg);
                                Log.i("获取: ", String.valueOf(infoList.size()));
                            }
                        });
                    }).start();
                }, 1500);
            }
        });

    }

    private void getData(){
        infoList = new ArrayList<>();

        @SuppressLint("HandlerLeak")
        Handler handler = new Handler() {
            public void handleMessage(Message message){
                switch (message.what){
                    case SUCCESS:
                        Log.i("获取: ", "成功");
                        adapter.setList(infoList);
                        adapter.notifyDataSetChanged();
                        break;

                    case FAIL:
                        Log.i("获取: ", "失败");
                        break;

                    case ZERO:
                        Log.i("获取: ", "0");
                        break;
                }
            }
        };

        new Thread(()->{
           Request request = new Request.Builder()
                   .url(getResources().getString(R.string.serverBasePath) +
                           getResources().getString(R.string.getAdoptMessage)
                           + "/?pageNum=1&pageSize=" + PAGE_SIZE + "&state=0")
                   .get()
                   .build();
            Message msg = new Message();
            OkHttpClient okHttpClient = new OkHttpClient();
            Call call = okHttpClient.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    Log.i("获取: ", e.getMessage());
                    msg.what = FAIL;
                    handler.sendMessage(msg);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                    ResponseAdoptInfo adoptMessage = new Gson().fromJson(response.body().string(),
                            ResponseAdoptInfo.class);
                    infoList = adoptMessage.getAdoptInfoList();
                    if(infoList.size() == 0) {
                        msg.what = ZERO;
                    } else {
                        msg.what = SUCCESS;
                    }
                    handler.sendMessage(msg);
                    Log.i("获取: ", String.valueOf(infoList.size()));
                }
            });
        }).start();
    }

    boolean flag1 = true, flag2 = true;
    private void add1(){
        if(flag1) {
            infoList.add(new AdoptInfo(R.drawable.cat3, "九九", "猫猫", 0, "3个月", description2, "湖北武汉", "3小时前", true, false, "春回大地"));
            infoList.add(new AdoptInfo(R.drawable.dog3, "十十", "狗狗", 1, "3岁", description3, "河南郑州", "3小时前", true, false, "福满人间"));
            infoList.add(new AdoptInfo(R.drawable.cat4, "佳佳", "猫猫", 0, "2个月", description1, "新疆阿克苏", "4小时前", true, false, "彩云追月"));
            infoList.add(new AdoptInfo(R.drawable.dog4, "依依", "狗狗", 0, "2岁半", description4, "香港深水埗", "5小时前", true, false, "骏马迎春"));
            infoList.add(new AdoptInfo(R.drawable.cat5, "冰冰", "猫猫", 1, "1岁半", description2, "吉林长春", "5小时前", true, false, "鲤跃龙门"));
            flag1 = false;
        }
    }
    private void add2() {
        if (flag2) {
            infoList.add(new AdoptInfo(R.drawable.dog5, "圆圆", "狗狗", 0, "1岁10个月", description1, "江苏南京", "1天前", true, false, "门迎百福"));
            infoList.add(new AdoptInfo(R.drawable.cat6, "扁扁", "猫猫", 1, "2岁2个月", description2, "台湾高雄", "1天前", true, false, "户纳千祥"));
            infoList.add(new AdoptInfo(R.drawable.dog6, "哼哈", "狗狗", 1, "2岁2个月", description3, "安徽合肥", "2天前", true, false, "五谷丰登"));
            infoList.add(new AdoptInfo(R.drawable.cat7, "中分", "猫猫", 1, "2岁", description4, "哥谭市", "3天前", true, false, "六畜兴旺"));
            flag2 = false;
        }
    }
    private void load() {
        if (times == 0) {
            new Handler().postDelayed(() -> {
                add1();
                recyclerView.loadMoreComplete();
                adapter.notifyDataSetChanged();
            }, 1000);
        } else if (times == 1) {
            new Handler().postDelayed(() -> {
                add2();
                recyclerView.loadMoreComplete();
                adapter.notifyDataSetChanged();
            }, 1000);
        } else {
            new Handler().postDelayed(() -> {
                recyclerView.loadMoreComplete();
                recyclerView.setNoMore(true);
                adapter.notifyDataSetChanged();
            }, 1000);
        }
        times++;
    }
    private void initAdoptInfo() {
        infoList = new ArrayList<>();
        infoList.add(new AdoptInfo(R.drawable.timg,"一一","狗狗",1,"2岁", description3,"广东广州","1小时前",true,false,"花开富贵"));
        infoList.add(new AdoptInfo(R.drawable.cat,"二二","猫猫",0,"7个月", description1,"四川成都","1小时前",true,false,"竹报平安"));
        infoList.add(new AdoptInfo(R.drawable.dog,"三三","狗狗",1,"3岁", description4,"辽宁铁岭","1小时前",true,false,"山间明月"));
        infoList.add(new AdoptInfo(R.drawable.cat1,"四四", "猫猫",0,"11个月", description2,"内蒙古鄂尔多斯","2小时前",true,false,"江上春风"));
        infoList.add(new AdoptInfo(R.drawable.dog1,"五五","狗狗",0,"4个月", description1,"浙江杭州","2小时前",true,false,"龙腾虎跃"));
        infoList.add(new AdoptInfo(R.drawable.cat2,"六六","猫猫",1,"1岁3个月", description2,"上海普陀","2小时前",true,false,"水啸山吟"));
        infoList.add(new AdoptInfo(R.drawable.dog2,"七七","狗狗",1,"1岁5个月", description3,"湖南长沙","3小时前",true,false,"木鱼水心"));
    }

    // 筛选
    private void initFilterListener() {
        multiMenusView.setOnSelectListener(showText -> {
            mDropDownMenu.setTabText(showText);
            mDropDownMenu.closeMenu();
        });
        singleMenuView1.setOnSelectListener((position, showText) -> {
            mDropDownMenu.setTabText(showText);
            mDropDownMenu.closeMenu();
        });
        singleMenuView2.setOnSelectListener((position, showText) -> {
            mDropDownMenu.setTabText(showText);
            mDropDownMenu.closeMenu();
        });
    }

    private void initDropMenus() {
        mDropDownMenu = rootView.findViewById(R.id.dropDownMenu);
        //菜单头部选项
        String[] headers = new String[]{"地区", "类型", "性别"};

        //菜单列表视图
        List<View> popupViews = new ArrayList<>();

        //初始化多级菜单
        final String[] levelOneMenu = {"北京市", "天津市", "上海市", "重庆市",
                "辽宁省", "吉林省", "黑龙江省", "内蒙古自治区",
                "河北省", "河南省", "山东省", "山西省",
                "江苏省", "浙江省", "福建省", "安徽省",
                "湖北省", "湖南省", "江西省",
                "广东省", "广西壮族自治区", "海南省",
                "四川省", "贵州省", "云南省",
                "陕西省", "甘肃省", "宁夏回族自治区", "青海省",
                "西藏自治区", "新疆维吾尔自治区",
                "香港特别行政区", "澳门特别行政区", "台湾省"};
        final String[][] levelTwoMenu = {
                {"东城区", "西城区", "朝阳区", "海淀区", "顺义区", "通州区", "大兴区", "房山区", "昌平区", "密云区", "怀柔区", "丰台区", "延庆区", "平谷区", "门头沟区", "石景山区"},
                {"和平区", "河东区", "河西区", "南开区", "河北区", "红桥区", "滨海新区", "东丽区", "西青区", "津南区", "北辰区", "武清区", "宝坻区", "宁河区", "静安区", "蓟州区"},
                {"黄浦区", "徐汇区", "长宁区", "静安区", "普陀区", "蛇口区", "杨浦区", "闵行区", "宝山区", "嘉定区", "浦东新区", "金山区", "松江区", "青浦区", "奉贤区", "崇明区"},
                {"渝中区", "万州区", "涪陵区", "江北区", "大渡口区", "沙坪坝区", "九龙坡区", "南岸区", "北碚区", "綦江区", "大足区", "渝北区", "巴南区", "黔江区", "长寿区", "江峰区", "合川区", "永川区", "南川区", "璧山区", "铜梁区", "潼南区", "荣昌区", "开州区", "梁平区", "武陵区", "城口县", "丰都县", "垫江县", "忠县", "云阳县", "奉节县", "巫山县", "巫溪县", "石柱土家族自治县", "秀山土家族苗族自治县", "酉阳土家族苗族自治县", "彭水苗族土家族自治县"},

                {"沈阳市", "大连市", "铁岭市", "本溪市", "朝阳市", "阜新市", "辽阳市", "盘锦市", "营口市", "抚顺市", "锦州市", "鞍山市", "丹东市", "葫芦岛市"},//辽宁省14个
                {"长春市", "吉林市", "通化市", "四平市", "松原市", "辽源市", "白城市", "白山市", "延边朝鲜族自治州"},//吉林省9个
                {"哈尔滨市", "大庆市", "牡丹江市", "齐齐哈尔市", "绥化市", "鸡西市", "双鸭山市", "鹤岗市", "七台河市", "伊春市", "佳木斯市", "黑河市", "大兴安岭地区"},//黑龙江省13个
                {"呼和浩特市", "包头市", "赤峰市", "通辽市", "乌海市", "呼伦贝尔市", "鄂尔多斯市", "巴彦淖尔市", "乌兰察布市", "兴安盟", "锡林郭勒盟", "阿拉善盟"},//内蒙古自治区12个

                {"石家庄市", "保定市", "沧州市", "邯郸市", "唐山市", "承德市", "邢台市", "衡水市", "廊坊市", "张家口市", "秦皇岛市"},//河北省11个
                {"郑州市", "开封市", "洛阳市", "南阳市", "信阳市", "濮阳市", "新乡市", "商丘市", "周口市", "焦作市", "漯河市", "许昌市", "安阳市", "济源市", "鹤壁市", "驻马店市", "三门峡市", "平顶山市"},//河南省18个
                {"济南市", "德州市", "济宁市", "临沂市", "青岛市", "泰安市", "威海市", "淄博市", "菏泽市", "烟台市", "莱芜市", "滨州市", "东营市", "聊城市", "枣庄市", "日照市", "潍坊市"},//山东省17个
                {"太原市", "大同市", "忻州市", "阳泉市", "长治市", "晋中市", "临汾市", "运城市", "朔州市", "晋城市", "吕梁市"},//山西省11个

                {"南京市", "常州市", "徐州市", "淮安市", "南通市", "宿迁市", "无锡市", "扬州市", "盐城市", "苏州市", "泰州市", "镇江市", "连云港市"},//江苏省13个
                {"杭州市", "湖州市", "嘉兴市", "金华市", "丽水市", "宁波市", "衢州市", "绍兴市", "台州市", "温州市", "舟山市"},//浙江省11个
                {"福州市", "宁德市", "泉州市", "厦门市", "莆田市", "南平市", "龙岩市", "三明市", "漳州市"},//福建省9个
                {"合肥市", "蚌埠市", "阜阳市", "淮北市", "六安市", "滁州市", "宿州市", "淮南市", "安庆市", "池州市", "亳州市", "黄山市", "宣城市", "芜湖市", "铜陵市", "马鞍山市"},//安徽省16个

                {"武汉市", "荆门市", "黄石市", "荆州市", "宜昌市", "孝感市", "襄阳市", "咸宁市", "仙桃市", "黄冈市", "十堰市", "随州市", "潜江市", "天门市", "鄂州市", "恩施土家族苗族自治州", "神农架林区"},//湖北省17个
                {"长沙市", "岳阳市", "郴州市", "衡阳市", "娄底市", "邵阳市", "湘潭市", "益阳市", "株洲市", "常德市", "永州市", "怀化市", "张家界市", "湘西土家族苗族自治州"},//湖南省14个
                {"南昌市", "赣州市", "九江市", "上饶市", "鹰潭市", "吉安市", "萍乡市", "宜春市", "新余市", "抚州市", "景德镇市"},//江西省11个

                {"广州市", "深圳市", "佛山市", "东莞市", "中山市", "珠海市", "惠州市", "韶关市", "河源市", "梅州市", "潮州市", "揭阳市", "汕头市", "汕尾市", "清远市", "云浮市", "江门市", "茂名市", "阳江市", "肇庆市", "湛江市"},//广东省21个
                {"南宁市", "崇左市", "桂林市", "贺州市", "钦州市", "梧州市", "百色市", "来宾市", "河池市", "北海市", "玉林市", "柳州市", "贵港市", "防城港市"},//广西壮族自治区14个
                {"海口市", "三亚市", "文昌市", "儋州市", "琼海市", "万宁市", "五指山市", "东方市", "澄迈县", "屯昌县", "定安县", "临高县", "乐东黎族自治县", "陵水黎族自治县", "白沙黎族自治县", "昌江黎族自治县", "保亭黎族苗族自治县", "琼中黎族苗族自治县", "三沙市"},//海南省19个

                {"成都市", "广元市", "绵阳市", "南充市", "宜宾市", "内江市", "自贡市", "广安市", "遂宁市", "巴中市", "德阳市", "乐山市", "眉山市", "雅安市", "资阳市", "泸州市", "达州市", "攀枝花市", "甘孜藏族自治州", "凉山彝族自治州", "阿坝藏族羌族自治州"},//四川省21个
                {"贵阳市", "铜仁市", "遵义市", "安顺市", "毕节市", "六盘水市", "黔南布依族苗族自治州", "黔西南布依族苗族自治州", "黔东南苗族侗族自治州"},//贵州省9个
                {"昆明市", "丽江市", "玉溪市", "昭通市", "曲靖市", "普洱市", "保山市", "临沧市", "楚雄彝族自治州", "大理白族自治州", "迪庆藏族自治州", "怒江傈僳族自治州", "文山壮族苗族自治州", "西双版纳傣族自治州", "德宏傣族景颇族自治州", "红河哈尼族彝族自治州"},//云南省16个

                {"西安市", "汉中市", "渭南市", "延安市", "榆林市", "咸阳市", "宝鸡市", "商洛市", "铜川市", "安康市"},//陕西省10个
                {"兰州市", "张掖市", "白银市", "酒泉市", "陇南市", "天水市", "金昌市", "武威市", "平凉市", "定西市", "庆阳市", "嘉峪关市", "临夏回族自治州", "甘南藏族自治州"},//甘肃省14个
                {"银川市", "中卫市", "吴忠市", "固原市", "石嘴山市"},//宁夏回族自治区5个
                {"西宁市", "玉树藏族自治州", "果洛藏族自治州", "黄南藏族自治州", "海东市", "海北藏族自治州", "海南藏族自治州", "海西蒙古族藏族自治州"},//青海省8个

                {"拉萨市", "昌都市", "林芝市", "山南市", "日喀则市", "阿里地区", "那曲地区"},//西藏自治区7个
                {"乌鲁木齐市", "哈密市", "北屯市", "双河市", "吐鲁番市", "石河子市", "五家渠市", "铁门关市", "阿拉尔市", "克拉玛依市", "可克达拉市", "图木舒克市", "喀什地区", "和田地区", "塔城地区", "阿克苏地区", "阿勒泰地区", "昌吉回族自治州", "伊犁哈萨克自治州", "巴音郭楞蒙古自治州", "博尔塔拉蒙古自治州", "克孜勒苏柯尔克孜自治州"},//新疆维吾尔自治区22个

                {"东区", "北区", "南区", "中西区", "湾仔区", "荃湾区", "元朗区", "沙田区", "观塘区", "离岛区", "葵青区", "屯门区", "西贡区", "大埔区", "九龙城区", "黄大仙区", "油尖旺区", "深水埗区"},//香港特别行政区18个
                {"澳门半岛", "路环", "氹仔", "路氹城"},//澳门特别行政区4个
                {"台北市", "新北市", "桃园市", "台中市", "台南市", "高雄市", "基隆市", "新竹市", "嘉义市"},//台湾省20个

        };
        multiMenusView = new MultiMenusView(this.getContext(), levelOneMenu, levelTwoMenu);
        popupViews.add(multiMenusView);

        //初始化单级菜单
        String[] typeArrays = {"不限", "猫咪", "狗子", "仓鼠", "乌龟", "其它"};
        String[] genderArrays = {"不限", "公", "母"};

        singleMenuView1 = new SingleMenuView(this.getContext(), typeArrays);
        singleMenuView2 = new SingleMenuView(this.getContext(), genderArrays);

        popupViews.add(singleMenuView1);
        popupViews.add(singleMenuView2);

        //初始化内容视图
        View contentView = LayoutInflater.from(this.getContext()).inflate(R.layout.fragment_user_adopt, null);

        //装载
        mDropDownMenu.setDropDownMenu(Arrays.asList(headers), popupViews, contentView);

        initFilterListener();
    }
}