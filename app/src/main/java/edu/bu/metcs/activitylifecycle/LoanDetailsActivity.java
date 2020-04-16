package edu.bu.metcs.activitylifecycle;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import android.view.MotionEvent;
import android.widget.ProgressBar;
import android.widget.TextView;
import java.util.Locale;

public class LoanDetailsActivity extends AppCompatActivity {

    float x1, x2, y1, y2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loan_details);

        // get the intent in the target activity
        Bundle chartData = getIntent().getExtras();

        // Extracting the stored data from the bundle
        assert chartData != null;
        double interest = chartData.getDouble("interest");
        double total = chartData.getDouble("total");

        // update the chart
        updateChart(interest, total);
    }


    public void updateChart(double interest, double total) {

        TextView totalCost = findViewById(R.id.textTotal);

        // USD currency - format payments to 2-place decimal
        String cost = String.format(Locale.US,"%,.2f", total);
        String txtCost = "$" + cost + "\nTotal";
        totalCost.setText(txtCost);

        ProgressBar pieChart = findViewById(R.id.stats_interest);
        double i = interest / total * 100;
        int progress = (int) i;
        pieChart.setProgress(progress);

        double principle = total - interest;
        TextView totalPrinciple = findViewById(R.id.textPrinciple);
        // USD currency - format payments to 2-place decimal
        String principleString = String.format(Locale.US,"%,.2f", principle);
        String txtP = "$" + principleString + "\nPrinciple";
        totalPrinciple.setText(txtP);

        TextView totalInterest = findViewById(R.id.textInterest);
        // USD currency - format payments to 2-place decimal
        String interestString = String.format(Locale.US,"%,.2f", interest);
        String txtI = "$" + interestString + "\nInterest";
        totalInterest.setText(txtI);
    }


    public boolean onTouchEvent(MotionEvent touchEvent) {
        switch (touchEvent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                x1 = touchEvent.getX();
                y1 = touchEvent.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = touchEvent.getX();
                y2 = touchEvent.getY();

                if (x1 > x2) { // Swipe ( right <-- left )
                    Intent i = new Intent(LoanDetailsActivity.this,
                            MainActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }

}
