package edu.bu.metcs.activitylifecycle;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import java.util.StringTokenizer;

/**
 * Adapted from code by: Shreekrishna
 * from: https://stackoverflow.com/questions/34607560/add-thousand-separators-automatically-while-text-changes-in-android-edittext/34607831
 */
public class NumberTextWatcherForThousand implements TextWatcher {

    private EditText editText;


    NumberTextWatcherForThousand(EditText editText) {
        this.editText = editText;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }
    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }
    @Override
    public void afterTextChanged(Editable s) {

        try
        {
            editText.removeTextChangedListener(this);
            String value = editText.getText().toString();

            if (!value.equals(""))
            {
                if(value.startsWith(".")){
                    editText.setText("0.");
                }
                if(value.startsWith("0") && !value.startsWith("0.")){
                    editText.setText("");
                }

                String str = editText.getText().toString().replaceAll(",", "");
                editText.setText(getDecimalFormattedString(str));
                editText.setSelection(editText.getText().toString().length());
            }
            editText.addTextChangedListener(this);
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
            editText.addTextChangedListener(this);
        }

    }

    private static String getDecimalFormattedString(String value)
    {
        StringTokenizer lst = new StringTokenizer(value, ".");
        String str1 = value;
        String str2 = "";

        if (lst.countTokens() > 1)
        {
            str1 = lst.nextToken();
            str2 = lst.nextToken();
        }

        StringBuilder str3 = new StringBuilder();
        int i = 0;
        int j = -1 + str1.length();

        if (str1.charAt( -1 + str1.length()) == '.')
        {
            j--;
            str3 = new StringBuilder(".");
        }

        for (int k = j;; k--)
        {
            if (k < 0)
            {
                if (str2.length() > 0)
                    str3.append(".").append(str2);
                return str3.toString();
            }

            if (i == 3)
            {
                str3.insert(0, ",");
                i = 0;
            }

            str3.insert(0, str1.charAt(k));
            i++;
        }

    }

    static String trimCommaOfString(String string) {

        if(string.contains(",")){
            return string.replace(",","");

        } else {
            return string;
        }

    }
}