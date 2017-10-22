package com.example.ccl.myapplication_1;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;


public class CreateNewRecipe extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout mNameTextInputLayout, mDescriptionTextInputLayout;
    private EditText mNameEditText, mDescriptionEditText;
    private Spinner mSpinner1;
    private Button mCreateButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_new_recipe_form);




        mNameEditText = (EditText) findViewById(R.id.name_edit_text);
        mDescriptionEditText = (EditText) findViewById(R.id.description_edit_text);
        mNameTextInputLayout = (TextInputLayout) findViewById(R.id.name_til);
        mDescriptionTextInputLayout = (TextInputLayout) findViewById(R.id.description_til);
        mSpinner1 = (Spinner) findViewById(R.id.spinner1);

        // set the color for the spinner label
        ColorStateList hintTextColor =  mNameEditText.getHintTextColors();
        //TextView spinner1Label = (TextView) findViewById(R.id.spinner1_label);
        //mSpinner1.setBackgroundColor(hintTextColor);

        mCreateButton = (Button) findViewById(R.id.create_button);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.measurement,
                android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        mSpinner1.setAdapter(adapter);

        mCreateButton.setOnClickListener(this);
        mNameEditText.setOnFocusChangeListener(new MyOnFocusChangeListener(mNameEditText));
        mDescriptionEditText.setOnFocusChangeListener(new MyOnFocusChangeListener(mDescriptionEditText));
    }

    @Override
    public void onClick(View view) {
        if (isNameValid() && isDescValid()) {
            Intent i = new Intent(CreateNewRecipe.this, MainActivity.class);
            startActivity(i);
        }
        else{
            Snackbar.make(findViewById(R.id.create_new_recipe_form), "Check the error messages!", Snackbar.LENGTH_SHORT).show();
        }
    }

    /**
     * check the name if it's valid (not null)
     * @return
     */
    public boolean isNameValid() {
        String name = mNameEditText.getText().toString();
        if (name.isEmpty()) {
            mNameTextInputLayout.setErrorEnabled(true);
            mNameTextInputLayout.setError("This field is required.");
            return false;
        } else {
            mNameTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    /**
     * check the description if it is valid (less than 50 words)
     * @return
     */
    public boolean isDescValid() {
        String desc = mDescriptionEditText.getText().toString();
        if (desc.length() > 50) {
            mDescriptionTextInputLayout.setErrorEnabled(true);
            mDescriptionTextInputLayout.setError("The description must be less than 50 words.");
            return false;
        } else {
            mDescriptionTextInputLayout.setErrorEnabled(false);
            return true;
        }
    }

    private class MyTextWatcher implements TextWatcher {

        private View view;

        private MyTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
        }

        @Override
        public void afterTextChanged(Editable s) {
            switch (view.getId()) {
                case R.id.name_edit_text:
                    isNameValid();
                    break;
                case R.id.description_edit_text:
                    isDescValid();
                    break;
//                case R.id.et_email:
//                    isEmailValid();
//                    break;
            }
        }
    }

    private class MyOnFocusChangeListener implements View.OnFocusChangeListener {
        private EditText EditTextView;

        private MyOnFocusChangeListener(View view) {
            this.EditTextView = (EditText) view;
        }

        @Override
        public void onFocusChange(View view, boolean hasFocus) {
            if (!hasFocus) {
                isNameValid(); // only the name is required
            }
            else {
                EditTextView.addTextChangedListener(new MyTextWatcher(EditTextView));
            }
        }
    }
}
