package com.macaria.app.ui.homeScreen.profile.faq.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.macaria.app.R;
import com.macaria.app.databinding.FaqFragmentBinding;
import com.macaria.app.models.BaseModel;
import com.macaria.app.ui.homeScreen.profile.faq.adapter.FaqAdapter;
import com.macaria.app.ui.homeScreen.profile.faq.models.FaqModel;
import com.macaria.app.ui.homeScreen.profile.faq.vm.FaqViewModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.adapters.OrderHistoryAdapter;
import com.macaria.app.ui.homeScreen.profile.orderHistory.models.OrderHistoryModel;
import com.macaria.app.ui.homeScreen.profile.orderHistory.vm.OrderHistoryViewModel;
import com.macaria.app.utilities.MyHelper;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class FaqFragment extends Fragment {
    private FaqFragmentBinding binding ;
    private FaqViewModel viewModel ;

    public FaqFragment() {}

    @Inject
    MyHelper helper;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FaqFragmentBinding.inflate(inflater, container, false);
        init();
        return binding.getRoot();
    }

    private void init(){
        viewModel = new ViewModelProvider(this).get(FaqViewModel.class);
        helper.showLoading(requireActivity());
        viewModel.getFaq();
        getFaqResponse();
    }

    private void getFaqResponse(){
        viewModel.getModelMutableLiveData().observe(getViewLifecycleOwner(), new Observer<BaseModel<List<FaqModel>>>() {
            @Override
            public void onChanged(BaseModel<List<FaqModel>> listBaseModel) {
                helper.dismissLoading();
                initRec(listBaseModel);
            }
        });
    }

    private void initRec(BaseModel<List<FaqModel>> listBaseModel) {
        FaqAdapter addressAdapter = new FaqAdapter(getActivity());
        binding.recycler.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        binding.recycler.setAdapter(addressAdapter);
        addressAdapter.addData(listBaseModel.getItem().getData());
    }
}