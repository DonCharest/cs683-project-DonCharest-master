package edu.bu.metcs.activitylifecycle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.jetbrains.annotations.NotNull;
import java.util.Locale;
import static java.lang.String.valueOf;

public class MainActivity extends AppCompatActivity {

    Bundle loanData = new Bundle();
    Bundle chartData = new Bundle();

    double modifiedInterestTotal, modifiedCostTotal;

    float x1, x2, y1, y2;

    private EditText principle;
    private EditText interestRate;
    private EditText timePeriod;
    private EditText extraPayment;

    private TextView monthlyPayment;
    private TextView totalCost;
    private TextView totalInterest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // variables for requesting permissions, API 25+
        int requestCode = 0;
        int[] grantResults = new int[0];

        if(ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED ) {

            // Ask for required permissions
            ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, requestCode);

            onRequestPermissionsResult(requestCode,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, grantResults);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        principle = findViewById(R.id.editTextId_principle);
        principle.addTextChangedListener(new NumberTextWatcherForThousand(principle));

        interestRate = findViewById(R.id.editTextId_interestRate);
        timePeriod = findViewById(R.id.editTextId_timePeriod);

        extraPayment = findViewById(R.id.editTextId_extraPayment);
        extraPayment.addTextChangedListener(new NumberTextWatcherForThousand(extraPayment));

        monthlyPayment = findViewById(R.id.textViewId_monthlyPayment);

        totalCost = findViewById(R.id.textViewId_totalCost);
        totalInterest =  findViewById(R.id.textViewId_totalInterest);

        Button submitBtn = findViewById(R.id.buttonId_submit);
        submitBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                calculateMonthlyPayment();
            }
        });
    }


    @Override // android recommended class to handle permissions
    public void onRequestPermissionsResult(int requestCode, @NotNull String[] permissions,
                                           @NotNull int[] grantResults) {

        if (requestCode == 1) { // If request is cancelled, the result arrays are empty.

            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                Log.d("permission", "granted");

            } else {

                // permission denied
                Toast.makeText(MainActivity.this,
                        "Permission to read your External storage denied",
                        Toast.LENGTH_SHORT).show();

                //app cannot function without this permission for now so close it...
                onDestroy();
            }

        }
    }


    public boolean onTouchEvent(MotionEvent touchEvent) {

        // If a loan has been calculated allow other views
        String mP = monthlyPayment.getText().toString();
        if (!mP.matches("")) {

            switch (touchEvent.getAction()) {

                case MotionEvent.ACTION_DOWN:
                    x1 = touchEvent.getX();
                    y1 = touchEvent.getY();

                    if (x1 < x2) { // Swipe ( right <-- left )
                        Intent i = new Intent(getApplicationContext(), LoanDetailsActivity.class);
                        i.putExtras(chartData);
                        startActivity(i);
                    }
                    break;

                case MotionEvent.ACTION_UP:
                    x2 = touchEvent.getX();
                    y2 = touchEvent.getY();

                    if (x1 > x2) { // Swipe ( left --> right )
                        Intent i = new Intent(getApplicationContext(),
                                AmortizationScheduleActivity.class);
                        i.putExtras(loanData);
                        startActivity(i);
                    }
                    break;
            }
        }
        return false;

    }

    private void calculateMonthlyPayment() {
        // Form validation - check for empty field, or if amount of zero entered -
        // prompt user if either condition true
        String p = principle.getText().toString();

        if (p.matches("")) {
            Toast.makeText(this, "You did not enter the principle",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (p.matches("0")) {
            Toast.makeText(this, "The principle must be greater than zero",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // Form validation - check for empty field - prompt user if either condition true
        String iR = interestRate.getText().toString();
        if (iR.matches("")) {
            Toast.makeText(this, "You did not enter the interest rate",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // Form validation - check for empty field, or if amount of zero entered -
        // prompt user if either condition true
        String tP = timePeriod.getText().toString();
        if (tP.matches("")) {
            Toast.makeText(this, "You did not enter the time period",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (tP.matches("0")) {
            Toast.makeText(this, "The time period must be greater than zero",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // Form validation - check for empty field - prompt user if either condition true
        String exP = extraPayment.getText().toString();
        if (exP.matches("")) {
            Toast.makeText(this, "extra payment cannot be void",
                    Toast.LENGTH_SHORT).show();
            return;
        }


       /*
       Formulas for Amortizing Loan Payment: Loan Payment = Amount / Discount Factor or P = A / D
        where:
            Number of Periodic Payments (n) = Payments per year times number of years
            Periodic Interest Rate (i) = Annual rate divided by number of payment periods
            Discount Factor (D) = {[(1 + i) ^n] - 1} / [i(1 + i)^n]
            Loan amount (A)
        */

        // number of payments (in months)
        int n = Integer.valueOf(timePeriod.getText().toString());

        // interest rate (converted to decimal)
        double i = Double.valueOf(interestRate.getText().toString()) / 12 / 100;

        // Discount rate
        double D =  ( Math.pow( ( 1 + i ), n ) -1 )  /  ( i *  Math.pow ( ( 1 + i ), n ) );
        // If if interest rate == 0, set discount factor == number of payments
        if (iR.equals("0")) {
            D = Double.valueOf(timePeriod.getText().toString());
        }

        // principle (Amount borrowed)
        double A = Double.valueOf(
                NumberTextWatcherForThousand.trimCommaOfString(principle.getText().toString()));

        // Payment (monthly)
        double P = ( A / D );
        // USD currency - format payments to 2-place decimal
        String payment = String.format(Locale.US,"%,.2f", P);
        String txtPayment = "Monthly Payment\n" + "$" + payment;
        monthlyPayment.setText( txtPayment );

        // Total cost of loan = ( r * p * n )  /  ( 1 - (  ( 1 + r ) ^ (-n) )
        double tC = ( i * A * n ) / ( 1 - ( Math.pow(  (1 + i ), -n ) ) ) ;
        // if interest == 0; total cost = principle
        if (iR.equals("0")) {
            tC = A;
        }


        // Extra PaymentAmount
        double eP = Double.valueOf(NumberTextWatcherForThousand.trimCommaOfString(
                extraPayment.getText().toString()));

        if (eP > 0) {

            modifiedInterestTotal = extraPaymentFormula(A, i, P, n, eP);
            Log.i("modified int total: ",  valueOf(modifiedInterestTotal));

            modifiedCostTotal = A + modifiedInterestTotal;
            Log.i("modified cost total: ",  valueOf(modifiedCostTotal));

            // USD currency - format payments to 2-place decimal
            String interest = String.format(Locale.US,"%,.2f", modifiedInterestTotal);
            String txtInterest = "Total Interest\n" + "$" + interest;
            totalInterest.setText( txtInterest );

            // USD currency - format payments to 2-place decimal
            String cost = String.format(Locale.US,"%,.2f", modifiedCostTotal);
            String txtCost = "Total Cost\n" + "$" + cost;
            totalCost.setText( txtCost );

            // Update Bundle chartData
            chartData.putDouble("interest", modifiedInterestTotal);
            chartData.putDouble("total", modifiedCostTotal);

        } else {

            // Total interest on loan = ( Total Cost - Principle Amount )
            double tI = ( tC - A ) ;
            // USD currency - format payments to 2-place decimal
            String interest = String.format(Locale.US,"%,.2f", tI);
            String txtInterest = "Total Interest\n" + "$" + interest;
            totalInterest.setText( txtInterest );

            // USD currency - format payments to 2-place decimal
            String cost = String.format(Locale.US,"%,.2f", tC);
            String txtCost = "Total Cost\n" + "$" + cost;
            totalCost.setText( txtCost );

            // Update Bundle chartData
            chartData.putDouble("interest", tI);
            chartData.putDouble("total", tC);
        }

        // update Bundle to include current loan detail parameters
        loanData.putDouble("amount",  A);
        loanData.putDouble("interestRate", i);
        loanData.putDouble("payment", P);
        loanData.putInt("timePeriod", n);
        loanData.putDouble("extraPayment", eP);

    }

    // formula to generate modified total interest,
    // when an extra monthly principle payment is applied
    private double extraPaymentFormula( double A, double i, double P, int n, double eP) {

        double startingBalance, endingBalance, amountPaid, interestPaid, principle,
                modifiedTotalInterest;
        int count = 0;

        amountPaid = P + eP;
        startingBalance = A;
        endingBalance = startingBalance;
        modifiedTotalInterest = 0;

        while ( ( endingBalance > 0 ) && ( count < n ) && ( amountPaid < startingBalance ) ) {

            if ( eP > 0 ) {

                if (amountPaid >= endingBalance) {
                    amountPaid = endingBalance ;
                }
            }
            // Starting Balance
            startingBalance = endingBalance;

            // interest (paid monthly)
            interestPaid = startingBalance * i;

            // Principle (paid monthly)
            principle = amountPaid - interestPaid;

            // Ending Balance
            if ( ( amountPaid < P ) && ( amountPaid == (principle + interestPaid )) ) {
                endingBalance = 0;

            } else { endingBalance = startingBalance - principle; }

            modifiedTotalInterest += interestPaid;
            count++;
        }

        return modifiedTotalInterest;
    }


}