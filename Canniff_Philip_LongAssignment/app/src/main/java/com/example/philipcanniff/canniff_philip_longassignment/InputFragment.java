package com.example.philipcanniff.canniff_philip_longassignment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by philipcanniff on 9/10/15.
 */
public class InputFragment extends Fragment {
    public PersonClass person;
    private int placement;
    public static boolean isEditing;

    public static final String TAG = "InputFragment.tag";

    InputInterfacing personListView;


    public static InputFragment newInstance(boolean editing, PersonClass _person, int _placement){

        InputFragment frag = new InputFragment();

        Bundle args = new Bundle();
        args.putSerializable("Person", _person);
        args.putInt("Placement", _placement);
        frag.setArguments(args);
        isEditing = editing;

        return frag;


    }
    public interface InputInterfacing{

        public void recallListFromInput();

        public void recallDetail(PersonClass _person, int _placement);



    }
    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof InputInterfacing){

            personListView = (InputInterfacing) activity;
        }
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null){

            person = (PersonClass) getArguments().getSerializable("Person");
            placement = getArguments().getInt("Placement");


        }

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.input_layout, container, false);

        //Input Buttons
        Button back = (Button)view.findViewById(R.id.backButton);
        Button saveUser = (Button) view.findViewById(R.id.saveUser);
        Button update = (Button) view.findViewById(R.id.updateUser);

        //Input Fields
        final EditText name = (EditText) view.findViewById(R.id.inputName);
        final EditText field = (EditText) view.findViewById(R.id.inputJob);
        final EditText years = (EditText) view.findViewById(R.id.inputYears);
        final EditText skillOne = (EditText) view.findViewById(R.id.inputSkill);
        final EditText skillTwo = (EditText) view.findViewById(R.id.inputSkill2);

        if (person != null){

            name.setText(person.getName());
            field.setText(person.getLast());
            years.setText(String.valueOf(person.getYears()));
            skillOne.setText(person.getSkill1());
            skillTwo.setText(person.getSkill2());

        }


        if (isEditing == true){

            saveUser.setVisibility(View.GONE);

        } else {


            update.setVisibility(View.GONE);

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (isEditing == false) {

                    personListView.recallListFromInput();

                    getActivity().findViewById(R.id.inputUser).setVisibility(View.VISIBLE);
                    getActivity().findViewById(R.id.image_layout).setVisibility(View.VISIBLE);

                } else {


                    personListView.recallDetail(person, placement);


                }

            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String skillOneDefault = "";
                String skillTwoDefault = "";


                if (name.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Please add a name.", Toast.LENGTH_SHORT).show();

                } else if (field.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Please add a field of expertise.", Toast.LENGTH_SHORT).show();

                } else if (years.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Please add your years of experience.", Toast.LENGTH_SHORT).show();


                } else {

                    if (skillOne.getText().toString().equals("")) {

                        skillOneDefault = "N/A";

                    } else {

                        skillOneDefault = skillOne.getText().toString();

                    }
                    if (skillTwo.getText().toString() == "") {

                        skillOneDefault = "N/A";

                    } else {

                        skillTwoDefault = skillTwo.getText().toString();

                    }

                    PersonClass person = new PersonClass(name.getText().toString(), field.getText().toString(), Integer.parseInt(years.getText().toString()), skillOne.getText().toString(), skillTwo.getText().toString());
                    DataManager.getInstance(getActivity()).updatePerson(person, placement);

                    personListView.recallListFromInput();
                    getActivity().findViewById(R.id.inputUser).setVisibility(View.VISIBLE);
                    getActivity().findViewById(R.id.image_layout).setVisibility(View.VISIBLE);

                }

            }
        });
        saveUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String skillOneDefault = "";
                String skillTwoDefault = "";


                if (name.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Please add a name.", Toast.LENGTH_SHORT).show();

                } else if (field.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Please add a field of expertise.", Toast.LENGTH_SHORT).show();

                } else if (years.getText().toString().equals("")) {

                    Toast.makeText(getActivity(), "Please add your years of experience.", Toast.LENGTH_SHORT).show();


                } else {

                    if (skillOne.getText().toString().equals("")) {

                        skillOneDefault = "N/A";

                    } else {

                        skillOneDefault = skillOne.getText().toString();

                    }
                    if (skillTwo.getText().toString() == "") {

                        skillOneDefault = "N/A";

                    } else {

                        skillTwoDefault = skillTwo.getText().toString();

                    }

                    PersonClass person = new PersonClass(name.getText().toString(), field.getText().toString(), Integer.parseInt(years.getText().toString()), skillOne.getText().toString(), skillTwo.getText().toString());
                    DataManager.getInstance(getActivity()).appendPerson(person);

                    personListView.recallListFromInput();
                    getActivity().findViewById(R.id.inputUser).setVisibility(View.VISIBLE);
                    getActivity().findViewById(R.id.image_layout).setVisibility(View.VISIBLE);

                }


            }
        });



        return view;

    }
}
