package tech.ankainn.edanapplication.ui.formOne;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.navigation.fragment.NavHostFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentSwitchableBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import tech.ankainn.edanapplication.ui.common.ScopeNavHostFragment;
import tech.ankainn.edanapplication.util.InjectorUtil;

public class ActivitiesFragment extends BindingFragment<FragmentSwitchableBinding> {

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        ViewModelProvider.Factory factory = InjectorUtil.provideFormOneViewModelFactory(requireContext());
        SwitchableViewModel viewModel = new ViewModelProvider(this, factory).get(SwitchableViewModel.class);

        binding().setTitle(getString(R.string.title_activities));
        binding().setBottomVisibility(true);
        binding().setOptionsVisibility(false);
        binding().setBtnText(getString(R.string.other_activities));

        String[] names = getResources().getStringArray(R.array.activities);
        SwitchableAdapter adapter = new SwitchableAdapter(names);
        binding().recyclerView.setAdapter(adapter);

        viewModel.getActivities().observe(getViewLifecycleOwner(), selectableData -> {
            binding().setData(selectableData);
            adapter.submitList(selectableData.list);
        });

        binding().btnMore.setOnClickListener(v -> {
            NavHostFragment.findNavController(this)
                    .navigate(ActivitiesFragmentDirections.actionToOthers());
        });
    }
}
