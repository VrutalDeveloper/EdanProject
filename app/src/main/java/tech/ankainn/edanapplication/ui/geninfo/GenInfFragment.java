package tech.ankainn.edanapplication.ui.geninfo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import tech.ankainn.edanapplication.R;
import tech.ankainn.edanapplication.databinding.FragmentGenInfBinding;
import tech.ankainn.edanapplication.ui.common.BindingFragment;
import timber.log.Timber;

public class GenInfFragment extends BindingFragment<FragmentGenInfBinding> implements
        DatePickerFragment.DateListener, TimePickerFragment.HourListener{

    private GenInfViewModel viewModel;

    @Override
    protected FragmentGenInfBinding makeBinding(LayoutInflater inflater, ViewGroup container) {
        return FragmentGenInfBinding.inflate(inflater, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        NavController navController = Navigation.findNavController(requireActivity(), R.id.fragment_container);

        int form = GenInfFragmentArgs.fromBundle(requireArguments()).getForm();
        int graphId = form == 1 ? R.id.form_one_host_graph : R.id.form_two_host_graph;
        ViewModelProvider viewModelProvider = new ViewModelProvider(navController.getViewModelStoreOwner(graphId));

        viewModel = viewModelProvider.get(GenInfViewModel.class);

        viewModel.getGenInfData().observe(getViewLifecycleOwner(),
                genInfData -> binding().setGenInfo(genInfData));

        binding().textDate.setOnClickListener(v -> DatePickerFragment.showDialog(this));
        binding().textHour.setOnClickListener(v -> TimePickerFragment.showDialog(this));
    }

    @Override
    public void setDate(String date) {
        binding().textDate.setText(date);
    }

    @Override
    public void setHour(String hour) {
        binding().textHour.setText(hour);
    }
}