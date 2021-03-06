package com.example.gohome.Member.Adapter;

import android.content.Context;
import android.content.Intent;
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
import com.example.gohome.Entity.ResponseAdoptAppliment;
import com.example.gohome.Entity.ResponseHelpAppliment;
import com.example.gohome.Member.Activity.MemberHandleOperationActivity;
import com.example.gohome.R;
import com.ramotion.foldingcell.FoldingCell;

import java.util.HashSet;
import java.util.List;

public class MemberCheckDoneFoldingCellAdapter extends RecyclerView.Adapter {



    //数据源
    private List<ResponseAdoptAppliment.responseAdoptAppliment> adoptApplimentList;
    private List<ResponseHelpAppliment.responseHelpAppliment> helpApplimentList;
    private Context context;

    private final String s0 = "♀";
    private final String s1 = "♂";

    //记录cell的折叠情况
    private HashSet<Integer> unfoldedIndexes = new HashSet<>();
    //按钮请求监听
    private View.OnClickListener defaultRequestBtnClickListener;
    //点击的返回
    private MemberCheckDoneFoldingCellAdapter.ItemClickCallBack clickCallBack;
    //记录信息类型  ，0为领养，1位救助
    public int type;


    public void setClickCallBack(MemberCheckDoneFoldingCellAdapter.ItemClickCallBack clickCallBack) {
        this.clickCallBack = clickCallBack;
    }

    public interface ItemClickCallBack{
        void onItemClick(int pos);
    }


    public MemberCheckDoneFoldingCellAdapter(Context context, List list, int type){

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

        ImageView petPhoto1;   //显示申请领养动物的图片
        // title：领养动物姓名、申请人信息提示文字，申请信息类型，申请日期
        TextView petName1, applicantMessage, type, titleDate;
        //content： 申请日期、宠物姓名、宠物性别、宠物年龄、疫苗情况、绝育情况
        TextView contentDate, petName2, petGender, petAge, applicantName, applicantJob,
                applicantAddress, applicantDescription, applicantTelephone, resultDescription,state;

        ImageView  vaccine,sterilization ;
        TextView contentRequestBtn;
        FoldingCell cell;


        public AdoptViewHolder(View itemView) {
            super(itemView);

            //cell title的内容
            petPhoto1 = itemView.findViewById(R.id.iv_memberCheckDoneTitlePetPhoto);
            petName1 = itemView.findViewById(R.id.tv_memberCheckDoneTitlePetName);
            applicantMessage = itemView.findViewById(R.id.tv_memberCheckDoneApplicantMessage);
            type = itemView.findViewById(R.id.tv_memberCheckDoneType);

            titleDate = itemView.findViewById(R.id.tv_memberCheckDoneTitleDate);

            //cell content的内容
//            petPhoto2 = itemView.findViewById(R.id.iv_memberCheckUndoAdoptContentPetPhoto);
            petName2 = itemView.findViewById(R.id.tv_memberCheckDoneAdoptContentPetName);
            contentDate = itemView.findViewById(R.id.tv_memberCheckDoneAdoptContentDate);
            petGender = itemView.findViewById(R.id.tv_memberCheckDoneAdoptPetGender);
            petAge = itemView.findViewById(R.id.tv_memberCheckDoneAdoptPetAge);
            vaccine = itemView.findViewById(R.id.iv_memberCheckDoneAdoptVaccine);
            sterilization = itemView.findViewById(R.id.iv_memberCheckDoneAdoptSterilization);
            applicantName = itemView.findViewById(R.id.tv_memberCheckDoneAdoptApplicantName);
            applicantJob = itemView.findViewById(R.id.tv_memberCheckDoneAdoptApplicantJob);
            applicantAddress = itemView.findViewById(R.id.tv_memberCheckDoneAdoptApplicantAddress);
            applicantTelephone = itemView.findViewById(R.id.tv_memberCheckDoneAdoptApplicantTel);
            applicantDescription = itemView.findViewById(R.id.tv_memberCheckDoneAdoptApplicantDescription);
            resultDescription = itemView.findViewById(R.id.tv_memberCheckDoneAdoptResultDescription);
            state = itemView.findViewById(R.id.tv_memberCheckDoneAdoptState);


            vaccine = itemView.findViewById(R.id.iv_memberCheckDoneAdoptVaccine);
            sterilization = itemView.findViewById(R.id.iv_memberCheckDoneAdoptSterilization);


            cell = itemView.findViewById(R.id.fc_memberCheckDoneAdopt);
        }


    }

    //负责每一个求助信息item
    public class HelpViewHolder extends RecyclerView.ViewHolder{

        ImageView petPhoto1;   //显示申请领养动物的图片
        // title：领养动物姓名、申请人信息提示文字，申请信息类型，申请日期
        TextView petName1, applicantMessage, type, titleDate;
        //content： 申请日期、宠物姓名、宠物性别、宠物年龄、疫苗情况、绝育情况
        TextView contentDate, petName2, petGender, petAge, applicantName,
                applicantAddress, applicantDescription, applicantTelephone,resultDescription,state;

        ImageView  vaccine,sterilization ;
        TextView contentRequestBtn;
        FoldingCell cell;


        public HelpViewHolder(View itemView) {
            super(itemView);

            //cell title的内容
            petPhoto1 = itemView.findViewById(R.id.iv_memberCheckDoneHelpTitlePetPhoto);
            petName1 = itemView.findViewById(R.id.tv_memberCheckDoneHelpTitlePetName);
            applicantMessage = itemView.findViewById(R.id.tv_memberCheckDoneHelpApplicantMessage);
            type = itemView.findViewById(R.id.tv_memberCheckDoneHelpType);
            titleDate = itemView.findViewById(R.id.tv_memberCheckDoneHelpTitleDate);

            //cell content的内容
//            petPhoto2 = itemView.findViewById(R.id.iv_memberCheckDoingHelpContentPetPhoto);
            petName2 = itemView.findViewById(R.id.tv_memberCheckDoneHelpContentPetName);
            contentDate = itemView.findViewById(R.id.tv_memberCheckDoneHelpContentDate);
            petGender = itemView.findViewById(R.id.tv_memberCheckDoneHelpPetGender);
            petAge = itemView.findViewById(R.id.tv_memberCheckDoneHelpPetAge);
            vaccine = itemView.findViewById(R.id.iv_memberCheckDoneHelpVaccine);
            sterilization = itemView.findViewById(R.id.iv_memberCheckDoneHelpSterilization);
            applicantName = itemView.findViewById(R.id.tv_memberCheckDoneHelpApplicantName);
            applicantAddress = itemView.findViewById(R.id.tv_memberCheckDoneHelpApplicantAddress);
            applicantTelephone = itemView.findViewById(R.id.tv_memberCheckDoneHelpApplicantTel);
            applicantDescription = itemView.findViewById(R.id.tv_memberCheckDoneHelpApplicantDescription);

            vaccine = itemView.findViewById(R.id.iv_memberCheckDoneHelpVaccine);
            sterilization = itemView.findViewById(R.id.iv_memberCheckDoneHelpSterilization);

            resultDescription = itemView.findViewById(R.id.tv_memberCheckDoneHelpResultDescription);
            state = itemView.findViewById(R.id.tv_memberCheckDoneHelpState);



            cell = itemView.findViewById(R.id.fc_memberCheckDoneHelp);
        }


    }


    //创建新View，被LayoutManager所调用
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){

        //类型为领养申请
        if(type == 0){

            View view = LayoutInflater.from(context).inflate(R.layout.fragment_member_check_done_adopt_item_cell,null);
            return new MemberCheckDoneFoldingCellAdapter.AdoptViewHolder(view);
        }
        else{  //类型为求助申请
            View view = LayoutInflater.from(context).inflate(R.layout.fragment_member_check_done_help_item_cell,null);
            return new MemberCheckDoneFoldingCellAdapter.HelpViewHolder(view);
        }

    }


    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {


//        //信息类型为申请领养类型
        if(type == 0) {

            MemberCheckDoneFoldingCellAdapter.AdoptViewHolder adoptViewHolder = new MemberCheckDoneFoldingCellAdapter.AdoptViewHolder(viewHolder.itemView);
            FoldingCell foldingCell = (FoldingCell) viewHolder.itemView;

            String gender = !adoptApplimentList.get(position).getPetGender() ? s0 : s1;
            int icon1 = !adoptApplimentList.get(position).getVaccine() ? R.drawable.no : R.drawable.yes;
            int icon2 = !adoptApplimentList.get(position).getSterilization() ? R.drawable.no : R.drawable.yes;

            //title
            Glide.with(context).load(adoptApplimentList.get(position).getPetPhotoId()).into(adoptViewHolder.petPhoto1);
            adoptViewHolder.petName1.setText(adoptApplimentList.get(position).getPetName());
            adoptViewHolder.applicantMessage.setText(adoptApplimentList.get(position).getApplyName() + "的申请，点击查看详情");
            adoptViewHolder.titleDate.setText(adoptApplimentList.get(position).getDate() );
            if (adoptApplimentList.get(position).getPetType().equals("0")) {    //类型为0则表示领养申请
                adoptViewHolder.type.setText("领养申请");
            }

            //content
            System.out.println("description:"+adoptApplimentList.get(position).getDescription());
            System.out.println("state:"+adoptApplimentList.get(position).getState());

            adoptViewHolder.petName2.setText(adoptApplimentList.get(position).getPetName());
            adoptViewHolder.petGender.setText(gender);
            adoptViewHolder.petAge.setText(adoptApplimentList.get(position).getPetAge());
            adoptViewHolder.contentDate.setText(adoptApplimentList.get(position).getDate() );
            adoptViewHolder.applicantName.setText(adoptApplimentList.get(position).getApplyName());
            adoptViewHolder.applicantJob.setText(adoptApplimentList.get(position).getJob());
            adoptViewHolder.applicantAddress.setText(adoptApplimentList.get(position).getAddress());
            adoptViewHolder.applicantTelephone.setText(adoptApplimentList.get(position).getTelephone());
            adoptViewHolder.applicantDescription.setText(adoptApplimentList.get(position).getDescription());
            adoptViewHolder.resultDescription.setText(adoptApplimentList.get(position).getResultDescription());
            if (adoptApplimentList.get(position).getState() == 0){  //状态值为0，已取消（申请失败）
                adoptViewHolder.state.setText("已取消");
            }
            if(adoptApplimentList.get(position).getState() == 1){  //状态值为1，已成功申请、领养
                adoptViewHolder.state.setText("已成功");
            }

            Glide.with(context).load(icon1).into(adoptViewHolder.vaccine);
            Glide.with(context).load(icon2).into(adoptViewHolder.sterilization);

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

            MemberCheckDoneFoldingCellAdapter.HelpViewHolder helpViewHolder = new MemberCheckDoneFoldingCellAdapter.HelpViewHolder(viewHolder.itemView);
            FoldingCell foldingCell = (FoldingCell)viewHolder.itemView;

            String gender = !helpApplimentList.get(position).getPetGender() ? s0 : s1;
            int icon1 = !helpApplimentList.get(position).getVaccine() ? R.drawable.no : R.drawable.yes;
            int icon2 = !helpApplimentList.get(position).getSterilization() ? R.drawable.no : R.drawable.yes;

            //title
            Glide.with(context).load(helpApplimentList.get(position).getPetPhotoId()).into(helpViewHolder.petPhoto1);
            helpViewHolder.petName1.setText(helpApplimentList.get(position).getPetName());
            helpViewHolder.applicantMessage.setText(helpApplimentList.get(position).getApplicantName()+"的申请，点击查看详情");
            helpViewHolder.titleDate.setText(helpApplimentList.get(position).getDate());
            if(helpApplimentList.get(position).getPetType().equals("0")){    //类型为0则表示领养申请
                helpViewHolder.type.setText("救助申请");
            }

            //content
            helpViewHolder.petName2.setText(helpApplimentList.get(position).getPetName());
            helpViewHolder.petGender.setText(gender);
            helpViewHolder.petAge.setText(helpApplimentList.get(position).getPetAge());
            helpViewHolder.contentDate.setText(helpApplimentList.get(position).getDate());
            helpViewHolder.applicantName.setText(helpApplimentList.get(position).getApplicantName());
            helpViewHolder.applicantAddress.setText(helpApplimentList.get(position).getAddress());
            helpViewHolder.applicantTelephone.setText(helpApplimentList.get(position).getApplicantTel());
            helpViewHolder.applicantDescription.setText(helpApplimentList.get(position).getDescription());
            helpViewHolder.resultDescription.setText(helpApplimentList.get(position).getResultDescription());
            if (helpApplimentList.get(position).getState() == 0){  //状态值为0，已取消（申请失败）
                helpViewHolder.state.setText("已取消");
            }
            if(helpApplimentList.get(position).getState() == 1){  //状态值为1，已成功申请救助
                helpViewHolder.state.setText("已成功");
            }

            Glide.with(context).load(icon1).into(helpViewHolder.vaccine);
            Glide.with(context).load(icon2).into(helpViewHolder.sterilization);


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
