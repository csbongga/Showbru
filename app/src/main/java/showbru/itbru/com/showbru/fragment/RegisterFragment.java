package showbru.itbru.com.showbru.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import showbru.itbru.com.showbru.MainActivity;
import showbru.itbru.com.showbru.R;
import showbru.itbru.com.showbru.utility.AddNewUserToServer;
import showbru.itbru.com.showbru.utility.MyAlert;
import showbru.itbru.com.showbru.utility.MyConstant;

public class RegisterFragment extends Fragment{
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

//        Create Toolbar
        createToolbar();


    } //main Method

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId()==R.id.itemUpload){

            uploadValueToServer();
            return true;
        }





        return super.onOptionsItemSelected(item);
    }

    private void uploadValueToServer() {
//        get value from Edittext
        EditText nameEditText=getView().findViewById(R.id.editName);
        EditText userEditText=getView().findViewById(R.id.editUser);
        EditText passwordEditText=getView().findViewById(R.id.editPassword);

//        Change Data Type From EditText to string
        String nameString = nameEditText.getText().toString().trim();
        String userString = userEditText.getText().toString().trim();
        String passwordString = passwordEditText.getText().toString().trim();


//        Check Space
        if (nameString.isEmpty() || userString.isEmpty() || passwordString.isEmpty()) {
//            Have Space
            MyAlert myAlert = new MyAlert(getActivity());
            myAlert.normalDialog("Hav Space คุณำม่ไได้กรแด","please fill all bank กรอกด้วย");

        } else {
//            No Space
            try {

                MyConstant myConstant = new MyConstant();
                AddNewUserToServer addNewUserToServer = new AddNewUserToServer(getActivity());
                addNewUserToServer.execute(nameString,userString,passwordString,
                        myConstant.getUrlAddUser());

                String result = addNewUserToServer.get();
                Log.d("26ApriV1", "result ==> " + result);

                if (Boolean.parseBoolean(result)) {
                    getActivity().getSupportFragmentManager().popBackStack();
                } else {
                    Toast.makeText(getActivity(),"Error Cannot Upload",
                            Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


        }

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.menu_register,menu); // ดึงเมนูมาใช้งาน

    }

    private void createToolbar() {
        Toolbar toolbar = getActivity().findViewById(R.id.toobarRegister);
        ((MainActivity)getActivity()).setSupportActionBar(toolbar);

//        Setup title
        ((MainActivity) getActivity()).getSupportActionBar().setTitle("Register");
        ((MainActivity) getActivity()).getSupportActionBar().setSubtitle("Plese Fill All Blank");
//        Setup Navigation Icon
        ((MainActivity) getActivity()).getSupportActionBar().setHomeButtonEnabled(true);
        ((MainActivity) getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });


        setHasOptionsMenu(true);






    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_register, container, false);
        return view;
    }
}
