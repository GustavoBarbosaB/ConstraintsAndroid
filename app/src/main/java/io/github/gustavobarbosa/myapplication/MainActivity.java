package io.github.gustavobarbosa.myapplication;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.constraint.Guideline;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.transition.AutoTransition;
import android.transition.TransitionManager;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView1) TextView mTextOne;
    @BindView(R.id.textView2) TextView mTextTwo;
    @BindView(R.id.textView3) TextView mTextThree;
    @BindView(R.id.my_constraint) ConstraintLayout mContraintLayout;
    @BindView(R.id.my_card) CardView mCardView;
    @BindView(R.id.guideline) Guideline mGuideline;

    private Boolean isShow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        this.isShow = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @OnClick(R.id.my_card)
    public void onClick(){
        if(isShow){
            hideView();
        }else
            showView();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void showView(){
        isShow = true;

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mContraintLayout);

        int idOne, idTwo, idThree, idGuideline;

        idOne = mTextOne.getId();
        idTwo = mTextTwo.getId();
        idThree = mTextThree.getId();
        idGuideline = mGuideline.getId();

        AutoTransition transition = new AutoTransition();
        transition.setDuration(2000);


        TransitionManager.beginDelayedTransition(mContraintLayout,transition);

        clearConstraints(constraintSet,idOne);
        clearConstraints(constraintSet,idTwo);
        clearConstraints(constraintSet,idThree);

        //Connect View One
        constraintSet.connect(idOne,ConstraintSet.START,idTwo,ConstraintSet.START,0);
        constraintSet.connect(idOne,ConstraintSet.TOP,ConstraintSet.PARENT_ID,ConstraintSet.TOP,0);

        //Connect View Two
        constraintSet.connect(idTwo,ConstraintSet.START,ConstraintSet.PARENT_ID,ConstraintSet.START,0);
        constraintSet.connect(idTwo,ConstraintSet.TOP,idGuideline,ConstraintSet.BOTTOM,getPixelValue(8));
        constraintSet.connect(idTwo,ConstraintSet.END,idThree,ConstraintSet.START,0);
        constraintSet.setHorizontalBias(idTwo,0.5f);

        //Connect View Three
        constraintSet.connect(idThree,ConstraintSet.END,ConstraintSet.PARENT_ID,ConstraintSet.END,0);
        constraintSet.connect(idThree,ConstraintSet.TOP,idGuideline,ConstraintSet.BOTTOM,0);
        constraintSet.connect(idThree,ConstraintSet.START,idTwo,ConstraintSet.END,0);
        constraintSet.setHorizontalBias(idThree,0.5f);

        constraintSet.applyTo(mContraintLayout);

    }

    public int getPixelValue(int dimenId) {
        Resources resources = getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                dimenId,
                resources.getDisplayMetrics()
        );
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void hideView(){
        isShow = false;
        int idOne, idTwo, idThree;

        idOne = mTextOne.getId();
        idTwo = mTextTwo.getId();
        idThree = mTextThree.getId();

        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(mContraintLayout);

        TransitionManager.beginDelayedTransition(mContraintLayout);

        clearConstraints(constraintSet,idOne);
        clearConstraints(constraintSet,idTwo);
        clearConstraints(constraintSet,idThree);

        constraintSet.applyTo(mContraintLayout);

    }

    private void clearConstraints(ConstraintSet constraintSet, int id){
        constraintSet.clear(id,ConstraintSet.START);
        constraintSet.clear(id,ConstraintSet.TOP);
        constraintSet.clear(id,ConstraintSet.BOTTOM);
        constraintSet.clear(id,ConstraintSet.END);
    }

}
