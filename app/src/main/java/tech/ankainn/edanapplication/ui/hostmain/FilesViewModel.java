package tech.ankainn.edanapplication.ui.hostmain;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.Transformations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import tech.ankainn.edanapplication.model.formTwo.FormTwoData;
import tech.ankainn.edanapplication.repositories.FormTwoRepository;
import tech.ankainn.edanapplication.ui.common.BaseViewModel;
import tech.ankainn.edanapplication.util.LoadingState;
import tech.ankainn.edanapplication.util.Messenger;
import tech.ankainn.edanapplication.util.Metadata;
import tech.ankainn.edanapplication.util.Tuple2;
import tech.ankainn.edanapplication.util.Tuple3;
import timber.log.Timber;

public class FilesViewModel extends BaseViewModel {

    private FormTwoRepository formTwoRepository;

    private LiveData<Messenger> messenger;
    private MediatorLiveData<List<Metadata<FormTwoData>>> list = new MediatorLiveData<>();

    private MutableLiveData<Metadata<FormTwoData>> loading = new MutableLiveData<>();
    private MutableLiveData<FormTwoData> upload = new MutableLiveData<>();
    private Metadata<FormTwoData> activeItem;

    public FilesViewModel(@NonNull Application application) {
        super(application);
        formTwoRepository = getApplication().getFormTwoRepository();

        LiveData<List<FormTwoData>> source = formTwoRepository.getListFormTwo();
        list.addSource(source, listData -> {
            List<Metadata<FormTwoData>> result = new ArrayList<>();
            for (FormTwoData listDatum : listData) {
                result.add(new Metadata<>(listDatum, false));
            }
            list.setValue(result);
        });
        list.addSource(loading, loadingData -> {
            Timber.v("FilesViewModel: %s %s", loadingData.data, loadingData.loading);
            if(list.getValue() == null) return;
            List<Metadata<FormTwoData>> temp = new ArrayList<>(list.getValue());

            for (int i = 0; i < temp.size(); i++) {
                if (temp.get(i).data.id == loadingData.data.id) {
                    temp.set(i, loadingData);
                    Timber.i("FilesViewModel: %s %s", loadingData.data, loadingData.loading);
                    list.setValue(temp);
                    return;
                }
            }
        });

        LiveData<Integer> result = Transformations.switchMap(upload, input -> formTwoRepository.postFormTwo(input));
        messenger = Transformations.map(result, input -> {
            loading.setValue(new Metadata<>(upload.getValue(), false));
            String msg = input == -1 ? "Hubo un problema, intente mas tarde" : "Archivo subido satisfactoriamente";
            return new Messenger(msg);
        });
    }

    public LiveData<List<Metadata<FormTwoData>>> getList() {
        return list;
    }

    public LiveData<Metadata<FormTwoData>> getLoading() {
        return loading;
    }

    public LiveData<Messenger> getMessenger() {
        return messenger;
    }

    public void setActiveItem(Metadata<FormTwoData> activeItem) {
        this.activeItem = activeItem;
    }

    public void uploadFile() {
        loading.setValue(new Metadata<>(activeItem.data, true));
        upload.setValue(activeItem.data);
        activeItem = null;
    }

    public void clearActiveItem() {
        activeItem = null;
    }
}