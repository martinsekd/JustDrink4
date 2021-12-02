package dk.au.mad21fall.appproject.justdrink.ViewModel;

import android.content.Context;

import com.android.volley.Response;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import dk.au.mad21fall.appproject.justdrink.Model.Repository;

public class ChatFragmentViewModel extends ViewModel {
    private MutableLiveData<String> UUID;
    private Context mContext;

    public void setContext(Context context) {
        mContext = context;
    }
    public LiveData<String> getUUID() {
        return Repository.getInstance(mContext).getUUID(mContext);
    }

}
