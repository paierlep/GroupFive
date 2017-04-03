package at.sw2017.calculator;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class Calculator extends AppCompatActivity implements View.OnClickListener {

    private Button buttonAdd;
    private Button buttonSub;
    private Button buttonMul;
    private Button buttonDiv;
    private Button buttonEqu;
    private Button buttonDel;
    private TextView numberView;
    private ArrayList<Button> numberButtons = new ArrayList<>();

    enum State {
        ADD, SUB, MUL, DIV, INIT, NUM
    }

    private int firstNumber;
    private State state = State.INIT;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calculator);

        numberView = (TextView) findViewById(R.id.textView);
        buttonAdd = (Button) findViewById(R.id.button_add);
        buttonSub = (Button) findViewById(R.id.button_subtract);
        buttonMul = (Button) findViewById(R.id.button_multiply);
        buttonDiv = (Button) findViewById(R.id.button_divide);
        buttonEqu = (Button) findViewById(R.id.button_equal);
        buttonDel = (Button) findViewById(R.id.button_C);

        setUpNumberButtonListener();

        buttonAdd.setOnClickListener(this);
        buttonSub.setOnClickListener(this);
        buttonMul.setOnClickListener(this);
        buttonDiv.setOnClickListener(this);
        buttonEqu.setOnClickListener(this);
        buttonDel.setOnClickListener(this);
    }

    public void setUpNumberButtonListener()  {
        for (int i=0; i<=9; i++) {
            String buttonName = "button_" + i;
            int id = getResources().getIdentifier(buttonName, "id", R.class.getPackage().getName());

            Button button = (Button) findViewById(id);
            button.setOnClickListener(this);

            numberButtons.add(button);
        }
    }

    @Override
    public void onClick(View v) {
        Button clickedButton = (Button) v;

        switch (clickedButton.getId()) {
            case R.id.button_add:
                clearNumberView();
                state = State.ADD;
                break;
            case R.id.button_subtract:
                clearNumberView();
                state = State.SUB;
                break;
            case R.id.button_multiply:
                clearNumberView();
                state = State.MUL;
                break;
            case R.id.button_divide:
                clearNumberView();
                state = State.DIV;
                break;
            case R.id.button_equal:
                calculateResult();
                state = State.INIT;
                break;
            case R.id.button_C:
                clearTextView();
                break;
            default:
                String recentNumber = numberView.getText().toString();
                if (state == State.INIT) {
                    recentNumber = "";
                    state = State.NUM;
                }

                recentNumber += clickedButton.getText().toString();
                numberView.setText(recentNumber);
        }
    }

    private void clearNumberView() {
        String tempString = numberView.getText().toString();
        if (!tempString.equals("")) {
            firstNumber = Integer.valueOf(tempString);
        }

        numberView.setText("");
    }

    private void clearTextView() {
        numberView.setText("0");
        firstNumber = 0;
        state = State.INIT;
    }

    private void calculateResult() {
        int secondNumber = 0;

        String tempString = numberView.getText().toString();
        if(!tempString.equals("")) {
            secondNumber = Integer.valueOf(tempString);
        }

        int result;
        switch (state) {
            case ADD:
                result = Calculations.doAddition(firstNumber, secondNumber);
                break;
            case SUB:
                result = Calculations.doSubtraction(firstNumber, secondNumber);
                break;
            case MUL:
                result = Calculations.doMultiplication(firstNumber, secondNumber);
                break;
            case DIV:
                result = Calculations.doDivision(firstNumber, secondNumber);
                break;
            default:
                result = secondNumber;
        }

        numberView.setText(Integer.toString(result));
    }
}
