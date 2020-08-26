package tech.ankainn.edanapplication.ui.host;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import tech.ankainn.edanapplication.model.formOne.FormOneData;
import tech.ankainn.edanapplication.model.formOne.FormOneSubset;
import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.model.formTwo.FormTwoSubset;
import tech.ankainn.edanapplication.repositories.FormOneRepository;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.util.Tuple2;

public class FilesViewModel extends ViewModel {

    private MediatorLiveData<List<Tuple2<Boolean, FormOneSubset>>> listFormOne = new MediatorLiveData<>();
    private MediatorLiveData<List<Tuple2<Boolean, FormTwoSubset>>> listFormTwo = new MediatorLiveData<>();

    private long tempFormOneId = 0L;
    private long tempFormTwoId = 0L;

    public FilesViewModel(FormOneRepository formOneRepository, FormTwoRepository formTwoRepository) {

        LiveData<List<FormOneSubset>> sourceFormOne = formOneRepository.loadAllFormOneSubset();
        listFormOne.addSource(sourceFormOne, list -> {
            if (!list.isEmpty()) {
                List<Tuple2<Boolean, FormOneSubset>> result = new ArrayList<>();
                for (FormOneSubset formOneSubset : list) result.add(new Tuple2<>(false, formOneSubset));
                listFormOne.setValue(result);
            }
        });

        LiveData<List<FormTwoSubset>> source = formTwoRepository.loadAllFormTwoSubset();
        listFormTwo.addSource(source, listData -> {
            if (!listData.isEmpty()) {
                List<Tuple2<Boolean, FormTwoSubset>> result = new ArrayList<>();
                for (FormTwoSubset data : listData) result.add(new Tuple2<>(false, data));
                listFormTwo.setValue(result);
            }
        });
    }

    public LiveData<List<Tuple2<Boolean, FormOneSubset>>> getListFormOne() {
        return listFormOne;
    }

    public LiveData<List<Tuple2<Boolean, FormTwoSubset>>> getListFormTwo() {
        return listFormTwo;
    }

    public void setActiveFormTwoItem(long formId) {
        tempFormTwoId = formId;
    }

    public long getActiveFormTwoItem() {
        long copy = tempFormTwoId;
        tempFormTwoId = 0L;
        return copy;
    }
}
