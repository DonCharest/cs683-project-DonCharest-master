package edu.bu.metcs.activitylifecycle;

import androidx.appcompat.app.AppCompatActivity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Locale;

public class AmortizationScheduleActivity extends AppCompatActivity {

    float x1, x2, y1, y2;
    String fileName = "AmortizationSchedule.pdf";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_amortization_schedule);

        // get the intent in the target activity
        Bundle loanData = getIntent().getExtras();

        // Extracting the stored data from the bundle
        assert loanData != null;
        double amount = loanData.getDouble("amount");
        double interestRate = loanData.getDouble("interestRate");
        double payment = loanData.getDouble("payment");
        int timePeriod = loanData.getInt("timePeriod");
        double extraPayment = loanData.getDouble("extraPayment");

        init(amount, interestRate, payment, timePeriod, extraPayment);
    }


    public void openPDF(){

        // Open the new PDF file
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + "/" + fileName);
        Intent target = new Intent(Intent.ACTION_VIEW);
        target.setDataAndType(Uri.fromFile(file),"application/pdf");
        target.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        Intent intent = Intent.createChooser(target, "Open File");

        try {
            startActivity(intent);

        } catch (ActivityNotFoundException e) {
            // Need to install a PDF reader
            Toast.makeText(getApplicationContext(), "Please install a PDF reader" ,
                    Toast.LENGTH_LONG).show();
        }

    }



    public void printPDF(View view) {

        RelativeLayout relativeLayout = findViewById(R.id.RelativeLayout1);

        int width = relativeLayout.getWidth();
        int height = relativeLayout.getHeight();

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        relativeLayout.draw(canvas);

        PdfDocument pdfDocument = new PdfDocument();

        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(width, height,
                1).create();

        PdfDocument.Page page = pdfDocument.startPage(pageInfo);
        Canvas canvas1 = page.getCanvas();
        canvas1.drawBitmap(bitmap, 0, 0, new Paint());
        pdfDocument.finishPage(page);

        try {

            File file = new File(
                    Environment.getExternalStorageDirectory().getAbsolutePath() +
                            File.separator + fileName);

            pdfDocument.writeTo(new FileOutputStream(file));

            Log.d("printPDF Success", "New file created @: " +
                    Environment.getExternalStorageDirectory() + File.separator +
                        fileName);


        } catch (FileNotFoundException fnfe) {
            fnfe.printStackTrace();
            Log.d("printPDF Failure", "file not found exception");

        } catch (IOException ioe) {
            ioe.printStackTrace();
            Log.d("printPDF Failure", "IO exception");
        }

        pdfDocument.close();

        openPDF();

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

                if (x1 < x2) { // Swipe from right --> left
                    Intent i = new Intent(AmortizationScheduleActivity.this,
                            MainActivity.class);
                    startActivity(i);
                }
                break;
        }
        return false;
    }


    public void init(double amount, double interestRate, double payment, int timePeriod,
                     double extraPayment) {

        TableLayout tableLayout = findViewById(R.id.table_main);
        TableRow head = new TableRow(this);

        TextView tv1 = new TextView(this);
        String tv1h = " No. ";
        tv1.setText(tv1h);
        tv1.setTextColor(Color.BLACK);
        head.addView(tv1);

        TextView tv2 = new TextView(this);
        String tv2h = " Balance (start)";
        tv2.setText(tv2h);
        tv2.setTextColor(Color.BLACK);
        head.addView(tv2);

        TextView tv3 = new TextView(this);
        String tv3h = " Payment ";
        tv3.setText(tv3h);
        tv3.setTextColor(Color.BLACK);
        head.addView(tv3);

        TextView tv4 = new TextView(this);
        String tv4h = " Principle ";
        tv4.setText(tv4h);
        tv4.setTextColor(Color.BLACK);
        head.addView(tv4);

        TextView tv5 = new TextView(this);
        String tv5h = " Interest ";
        tv5.setText(tv5h);
        tv5.setTextColor(Color.BLACK);
        head.addView(tv5);

        TextView tv6 = new TextView(this);
        String tv6h = " Balance (end)";
        tv6.setText(tv6h);
        tv6.setTextColor(Color.BLACK);
        head.addView(tv6);

        tableLayout.addView(head);


        double startingBalance, endingBalance, amountPaid, interestPaid, principle;
        amountPaid = payment + extraPayment;
        startingBalance = amount;
        endingBalance = startingBalance;


        int count = 0;

        while ( (endingBalance > 0) && (count < timePeriod) && (amountPaid < startingBalance) ) {

            TableRow tableRow = new TableRow(this);

            if ( count % 2 == 0 ) {
                tableRow.setBackgroundColor(Color.parseColor("#00c3ff"));
            }

            if (extraPayment > 0) {

                if (amountPaid >= endingBalance) {
                    amountPaid = endingBalance ;
                }
            }

            // payment No.
            TextView t1v = new TextView(this);
            String number = String.valueOf(count + 1);
            t1v.setText(number);
            t1v.setTextColor(Color.BLACK);
            t1v.setGravity(Gravity.CENTER);
            tableRow.addView(t1v);

            // Starting Balance
            TextView t2v = new TextView(this);
            startingBalance = endingBalance;
            String startBal = String.format(Locale.US,"%,.2f", startingBalance);
            String startBal$ = "$" + startBal;
            t2v.setText(startBal$);
            t2v.setTextColor(Color.BLACK);
            t2v.setGravity(Gravity.CENTER);
            tableRow.addView(t2v);

            // Payment (monthly payment + extra payment)
            TextView t3v = new TextView(this);
            String pay = String.format(Locale.US,"%,.2f", amountPaid);
            String pay$ = "$" + pay;
            t3v.setText(pay$);
            t3v.setTextColor(Color.BLACK);
            t3v.setGravity(Gravity.CENTER);
            tableRow.addView(t3v);

            // Principle (paid monthly)
            TextView t4v = new TextView(this);
            interestPaid  = startingBalance * interestRate;
            principle = amountPaid - interestPaid;
            String principlePaid = String.format(Locale.US,"%,.2f", principle);
            String principlePaid$ = "$" + principlePaid;
            t4v.setText(principlePaid$);
            t4v.setTextColor(Color.BLACK);
            t4v.setGravity(Gravity.CENTER);
            tableRow.addView(t4v);

            // interest (paid monthly)
            TextView t5v = new TextView(this);
            String interestPaidStr = String.format(Locale.US,"%,.2f", interestPaid);
            String interestPaid$ = "$" + interestPaidStr;
            t5v.setText(interestPaid$);
            t5v.setTextColor(Color.BLACK);
            t5v.setGravity(Gravity.CENTER);
            tableRow.addView(t5v);

            // Ending Balance
            if ( (amountPaid < payment) && (amountPaid == (principle + interestPaid)) ) {
                endingBalance = 0;
            } else { endingBalance = startingBalance - principle; }

            TextView t6v = new TextView(this);
            String endBal = String.format(Locale.US,"%,.2f", endingBalance);
            String endBal$ = "$" + endBal;
            t6v.setText(endBal$);
            t6v.setTextColor(Color.BLACK);
            t6v.setGravity(Gravity.CENTER);
            tableRow.addView(t6v);

            tableLayout.addView(tableRow);
            count++;
        }

    }


}
