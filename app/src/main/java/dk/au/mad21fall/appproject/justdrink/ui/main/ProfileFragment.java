package dk.au.mad21fall.appproject.justdrink.ui.main;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import dk.au.mad21fall.appproject.justdrink.Model.Gender;
import dk.au.mad21fall.appproject.justdrink.Model.ProfileSettings;
import dk.au.mad21fall.appproject.justdrink.R;

import static dk.au.mad21fall.appproject.justdrink.Model.Gender.FEMALE;
import static dk.au.mad21fall.appproject.justdrink.Model.Gender.MALE;
import static dk.au.mad21fall.appproject.justdrink.Model.Gender.OTHER;

public class ProfileFragment extends Fragment {

    private ProfileViewModel mViewModel;

    private EditText firstName;
    private EditText age;
    private RadioGroup gender;
    private Button updateBtn;

    public static ProfileFragment newInstance() {
        return new ProfileFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mViewModel = new ViewModelProvider(this).get(ProfileViewModel.class);
        View v = inflater.inflate(R.layout.profile_fragment, container, false);
        firstName = v.findViewById(R.id.firstName);
        age = v.findViewById(R.id.age);
        gender = v.findViewById(R.id.gender);
        updateBtn = v.findViewById(R.id.updateBtn);

        mViewModel.mProfile.observe(getViewLifecycleOwner(), new Observer<ProfileSettings>() {
            @Override
            public void onChanged(ProfileSettings profileSettings) {
                firstName.setText(profileSettings.firstName);
                age.setText(profileSettings.age+"");
                switch (profileSettings.gender) {
                    case MALE:
                        gender.check(R.id.male);
                        break;
                    case FEMALE:
                        gender.check(R.id.female);
                        break;
                    case OTHER:
                        gender.check(R.id.other);
                        break;
                    default:
                        gender.check(R.id.other);
                        break;

                }
            }
        });

        gender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                Toast.makeText(getActivity(),"Du har trykket på "+i,Toast.LENGTH_SHORT).show();
            }
        });

       updateBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               ProfileSettings settings = new ProfileSettings();
               settings.firstName = firstName.getText().toString();
               String agestr = age.getText().toString();
               settings.age = Integer.parseInt(agestr);
               int resid = gender.getCheckedRadioButtonId();
               View selectedRadioBtn = gender.findViewById(resid);
               switch (gender.indexOfChild(selectedRadioBtn)) {
                   case 0:
                       settings.gender = MALE;
                       break;
                   case 1:
                       settings.gender = FEMALE;
                       break;
                   case 2:
                       settings.gender = OTHER;
                       break;
                   default:
                       settings.gender = OTHER;
                       break;
               }

               mViewModel.updateSettings(settings);
           }
       });
        return v;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // TODO: Use the ViewModel
    }

}
