package com.example.gohome.Member.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gohome.Entity.AdoptAppliment;
import com.example.gohome.Entity.HelpAppliment;
import com.example.gohome.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

public class MemberCheckUndoFoldingCellAdapter extends RecyclerView.Adapter {

    //数据源
    private List<AdoptAppliment> adoptApplimentList;
    private List<HelpAppliment> helpApplimentList;
    private Context context;

    private final String s0 = "♀";
    private final String s1 = "♂";

    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    //按钮请求监听
    private View.OnClickListener defaultRequestBtnClickListener;
    //点击返回
    private ItemClickCallBack clickCallBack;

    public int type;    //记录信息类型  ，0为领养，1位救助


    public void setClickCallBack(ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack{
        void onItemClick(int pos);
    }


    public MemberCheckUndoFoldingCellAdapter(Context context, List list, int type){

        this.type = type;

        if(type == 0){
            this.adoptApplimentList = list;
        }
        else{
            this.helpApplimentList = list;
        }
        this.context = context;

    }


    //负责每一个item
    public class AdoptViewHolder extends RecyclerView.ViewHolder{

        ImageView petPhoto1, petPhoto2;   //显示申请领养动物的图片
        // title：领养动物姓名、申请人信息提示文字，申请信息类型，申请日期
        TextView petName1, applicantMessage, type, titleDate;
        //content： 申请日期、宠物姓名、宠物性别、宠物年龄、疫苗情况、绝育情况
        TextView contentDate, petName2, petGender, petAge, applicantName, applicantJob,
                applicantAddress, applicantDescription, applicantTelephone;

        ImageView  vaccine,sterilization ;
        TextView publisher, time;
        TextView contentRequestBtn;
        FoldingCell cell;


        public AdoptViewHolder(View itemView) {
            super(itemView);

            //cell title的内容
            petPhoto1 = itemView.findViewById(R.id.iv_memberCheckUndoAdoptTitlePetPhoto);
            petName1 = itemView.findViewById(R.id.tv_memberCheckUndoAdoptTitlePetName);
            applicantMessage = itemView.findViewById(R.id.tv_memberCheckUndoAdoptApplicantMessage);
            type = itemView.findViewById(R.id.tv_memberCheckUndoAdoptType);

            titleDate = itemView.findViewById(R.id.tv_memberCheckUndoAdoptTitleDate);
            type = itemView.findViewById(R.id.tv_memberCheckUndoAdoptType);

            //cell content的内容
            petPhoto2 = itemView.findViewById(R.id.iv_memberCheckUndoAdoptContentPetPhoto);
            petName2 = itemView.findViewById(R.id.tv_memberCheckUndoAdoptContentPetName);
            contentDate = itemView.findViewById(R.id.tv_memberCheckUndoAdoptContentDate);
            petGender = itemView.findViewById(R.id.tv_memberCheckUndoAdoptPetGender);
            petAge = itemView.findViewById(R.id.tv_memberCheckUndoAdoptPetAge);
            vaccine = itemView.findViewById(R.id.iv_memberCheckUndoAdoptVaccine);
            sterilization = itemView.findViewById(R.id.iv_memberCheckUndoAdoptSterilization);
            applicantName = itemView.findViewById(R.id.tv_memberCheckUndoAdoptApplicantName);
            applicantJob = itemView.findViewById(R.id.tv_memberCheckUndoAdoptApplicantJob);
            applicantAddress = itemView.findViewById(R.id.tv_memberCheckUndoAdoptApplicantAddress);
            applicantTelephone = itemView.findViewById(R.id.tv_memberCheckUndoAdoptApplicantTel);
            applicantDescription = itemView.findViewById(R.id.tv_memberCheckUndoAdoptApplicantDescription);
            //通过审核按钮
            contentRequestBtn = itemView.findViewById(R.id.btn_memberCheckUndoAdopt);


            vaccine = itemView.findViewById(R.id.iv_memberCheckUndoAdoptVaccine);
            sterilization = itemView.findViewById(R.id.iv_memberCheckUndoAdoptSterilization);


            cell = itemView.findViewById(R.id.fc_memberCheckUndoAdopt);
        }


    }

    //负责每一个求助信息item
    public class HelpViewHolder extends RecyclerView.ViewHolder{

        ImageView petPhoto1, petPhoto2;   //显示申请领养动物的图片
        // title：领养动物姓名、申请人信息提示文字，申请信息类型，申请日期
        TextView petName1, applicantMessage, type, titleDate;
        //content： 申请日期、宠物姓名、宠物性别、宠物年龄、疫苗情况、绝育情况
        TextView contentDate, petName2, petGender, petAge, applicantName,
                applicantAddress, applicantDescription, applicantTelephone;

        ImageView  vaccine,sterilization ;
        TextView contentRequestBtn;
        FoldingCell cell;


        public HelpViewHolder(View itemView) {
            super(itemView);

            //cell title的内容
            petPhoto1 = itemView.findViewById(R.id.iv_memberCheckUndoHelpTitlePetPhoto);
            petName1 = itemView.findViewById(R.id.tv_memberCheckUndoHelpTitlePetName);
            applicantMessage = itemView.findViewById(R.id.tv_memberCheckUndoHelpApplicantMessage);
            type = itemView.findViewById(R.id.tv_memberCheckUndoHelpType);

            titleDate = itemView.findViewById(R.id.tv_memberCheckUndoHelpTitleDate);
            type = itemView.findViewById(R.id.tv_memberCheckUndoHelpType);

            //cell content的内容
            petPhoto2 = itemView.findViewById(R.id.iv_memberCheckUndoHelpContentPetPhoto);
            petName2 = itemView.findViewById(R.id.tv_memberCheckUndoHelpContentPetName);
            contentDate = itemView.findViewById(R.id.tv_memberCheckUndoHelpContentDate);
            petGender = itemView.findViewById(R.id.tv_memberCheckUndoHelpPetGender);
            petAge = itemView.findViewById(R.id.tv_memberCheckUndoHelpPetAge);
            vaccine = itemView.findViewById(R.id.iv_memberCheckUndoHelpVaccine);
            sterilization = itemView.findViewById(R.id.iv_memberCheckUndoHelpSterilization);
            applicantName = itemView.findViewById(R.id.tv_memberCheckUndoHelpApplicantName);
            applicantAddress = itemView.findViewById(R.id.tv_memberCheckUndoHelpApplicantAddress);
            applicantTelephone = itemView.findViewById(R.id.tv_memberCheckUndoHelpApplicantTel);
            applicantDescription = itemView.findViewById(R.id.tv_memberCheckUndoHelpApplicantDescription);
            //通过审核按钮
            contentRequestBtn = itemView.findViewById(R.id.btn_memberCheckUndoHelp);


            vaccine = itemView.findViewById(R.id.iv_memberCheckUndoHelpVaccine);
            sterilization = itemView.findViewById(R.id.iv_memberCheckUndoHelpSterilization);


            cell = itemView.findViewById(R.id.fc_memberCheckUndoHelp);
        }


    }


    //创建新View，被LayoutManager所调用
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){

        System.out.println("viewType:"+viewType);
        System.out.println("type:"+type);

        //类型为领养申请
        if(type == 0){

            View view = (FoldingCell)LayoutInflater.from(context).inflate(R.layout.fragment_member_check_undo_adopt_item_cell,null);
            return new AdoptViewHolder(view);
        }
        else{  //类型为求助申请
            View view = (FoldingCell)LayoutInflater.from(context).inflate(R.layout.fragment_member_check_undo_adopt_item_cell,null);
            return new AdoptViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {


//        //信息类型为申请领养类型
        if(type == 0) {


        AdoptViewHolder adoptViewHolder = new AdoptViewHolder(viewHolder.itemView);
        FoldingCell foldingCell = (FoldingCell) viewHolder.itemView;

        String gender = !adoptApplimentList.get(position).getPetGender() ? s0 : s1;
        int icon1 = !adoptApplimentList.get(position).getVaccine() ? R.drawable.no : R.drawable.yes;
        int icon2 = !adoptApplimentList.get(position).getSterilization() ? R.drawable.no : R.drawable.yes;


        //title
        Glide.with(context).load(adoptApplimentList.get(position).getPetPhotoId()).into(adoptViewHolder.petPhoto1);
        adoptViewHolder.petName1.setText(adoptApplimentList.get(position).getPetName());
        adoptViewHolder.applicantMessage.setText(adoptApplimentList.get(position).getApplyName() + "发来了申请，点击查看详情");
        adoptViewHolder.titleDate.setText(adoptApplimentList.get(position).getDate().getYear() + "年" + adoptApplimentList.get(position).getDate().getMonth() + "月" +
                adoptApplimentList.get(position).getDate().getDay() + "日");
        if (adoptApplimentList.get(position).getPetType().equals("0")) {    //类型为0则表示领养申请
            adoptViewHolder.type.setText("领养申请");
        }

        //content
        Glide.with(context).load(adoptApplimentList.get(position).getPetPhotoId()).into(adoptViewHolder.petPhoto2);
        adoptViewHolder.petName2.setText(adoptApplimentList.get(position).getPetName());
        adoptViewHolder.petGender.setText(gender);
        adoptViewHolder.petAge.setText(adoptApplimentList.get(position).getPetAge());
        adoptViewHolder.contentDate.setText(adoptApplimentList.get(position).getDate().getYear() + "年" + adoptApplimentList.get(position).getDate().getMonth() + "月" +
                adoptApplimentList.get(position).getDate().getDay() + "日");
        adoptViewHolder.applicantName.setText(adoptApplimentList.get(position).getApplyName());
        adoptViewHolder.applicantJob.setText(adoptApplimentList.get(position).getJob());
        adoptViewHolder.applicantAddress.setText(adoptApplimentList.get(position).getAddress());
        adoptViewHolder.applicantTelephone.setText(adoptApplimentList.get(position).getTelephone());
        adoptViewHolder.applicantDescription.setText(adoptApplimentList.get(position).getDescription());

        Glide.with(context).load(icon1).into(adoptViewHolder.vaccine);
        Glide.with(context).load(icon2).into(adoptViewHolder.sterilization);
//            adoptViewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
        //设置审核通过点击事件
        adoptViewHolder.contentRequestBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("点击了第" + position + "个通过审核按钮");
                Toast.makeText(context, "点击了第" + position + "个通过审核按钮", Toast.LENGTH_LONG).show();
            }
        });

        //控制cell的折叠与收缩
        adoptViewHolder.cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (clickCallBack != null) {
                    clickCallBack.onItemClick(position);
                    // 切换已单击的单元格状态
                    ((FoldingCell) viewHolder.itemView).toggle(false);
                    // 在适配器中注册所选单元格的状态已被切换
                    registerToggle(position);

                    if (unfoldedIndexes.contains(position)) {
                        foldingCell.unfold(true);
                    } else {
                        foldingCell.fold(true);
                    }

                }
            }
        });

        }else{   //信息为求助申请


            HelpViewHolder helpViewHolder = new HelpViewHolder(viewHolder.itemView);
            FoldingCell foldingCell = (FoldingCell)viewHolder.itemView;

            String gender = !helpApplimentList.get(position).getPetGender() ? s0 : s1;
            int icon1 = !helpApplimentList.get(position).getVaccine() ? R.drawable.no : R.drawable.yes;
            int icon2 = !helpApplimentList.get(position).getSterilization() ? R.drawable.no : R.drawable.yes;

            //title
            Glide.with(context).load(helpApplimentList.get(position).getPetPhotoId()).into(helpViewHolder.petPhoto1);
            helpViewHolder.petName1.setText(helpApplimentList.get(position).getPetName());
            helpViewHolder.applicantMessage.setText(helpApplimentList.get(position).getApplicantName()+"发来了申请，点击查看详情");
            helpViewHolder.titleDate.setText(helpApplimentList.get(position).getDate().getYear()+"年"+helpApplimentList.get(position).getDate().getMonth()+"月"+
                    helpApplimentList.get(position).getDate().getDay()+"日");
            if(helpApplimentList.get(position).getPetType().equals("0")){    //类型为0则表示领养申请
                helpViewHolder.type.setText("领养申请");
            }

            //content
            Glide.with(context).load(helpApplimentList.get(position).getPetPhotoId()).into(helpViewHolder.petPhoto2);
            helpViewHolder.petName2.setText(helpApplimentList.get(position).getPetName());
            helpViewHolder.petGender.setText(gender);
            helpViewHolder.petAge.setText(helpApplimentList.get(position).getPetAge());
            helpViewHolder.contentDate.setText(helpApplimentList.get(position).getDate().getYear()+"年"+helpApplimentList.get(position).getDate().getMonth()+"月"+
                    helpApplimentList.get(position).getDate().getDay()+"日");
            helpViewHolder.applicantName.setText(helpApplimentList.get(position).getApplicantName());
            helpViewHolder.applicantAddress.setText(helpApplimentList.get(position).getApplicantAddress());
            helpViewHolder.applicantTelephone.setText(helpApplimentList.get(position).getApplicantTel());
            helpViewHolder.applicantDescription.setText(helpApplimentList.get(position).getDescription());

            Glide.with(context).load(icon1).into(helpViewHolder.vaccine);
            Glide.with(context).load(icon2).into(helpViewHolder.sterilization);
//            adoptViewHolder.contentRequestBtn.setOnClickListener(defaultRequestBtnClickListener);
            //设置审核通过点击事件
            helpViewHolder.contentRequestBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("点击了第"+position+"个通过审核按钮");
                    Toast.makeText(context,"点击了第"+position+"个通过审核按钮",Toast.LENGTH_LONG).show();
                }
            });


            //控制cell的折叠与收缩
            helpViewHolder.cell.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(clickCallBack != null){
                        clickCallBack.onItemClick(position);
                        // 切换已单击的单元格状态
                        ((FoldingCell) viewHolder.itemView).toggle(false);
                        // 在适配器中注册所选单元格的状态已被切换
                        registerToggle(position);

                        if(unfoldedIndexes.contains(position)) {
                            foldingCell.unfold(true);
                        } else {
                            foldingCell.fold(true);
                        }

                    }
                }
            });

        }

    }



    @Override
    public int getItemCount() {
        if(type == 0){
            return adoptApplimentList == null ? 0 : adoptApplimentList.size();
        }else {
            return helpApplimentList == null ? 0 : helpApplimentList.size();
        }
    }

    public void registerToggle(int position) {
        if (unfoldedIndexes.contains(position))
            registerFold(position);
        else
            registerUnfold(position);
    }


    private void registerFold(int position) {
        unfoldedIndexes.remove(position);
    }

    private void registerUnfold(int position) {
        unfoldedIndexes.add(position);
    }

    public View.OnClickListener getDefaultRequestBtnClickListener() {
        return defaultRequestBtnClickListener;
    }

    public void setDefaultRequestBtnClickListener(View.OnClickListener defaultRequestBtnClickListener) {
        this.defaultRequestBtnClickListener = defaultRequestBtnClickListener;
    }

}
