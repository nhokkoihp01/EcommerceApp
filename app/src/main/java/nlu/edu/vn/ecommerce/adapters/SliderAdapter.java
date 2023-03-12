package nlu.edu.vn.ecommerce.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewpager.widget.PagerAdapter;

import nlu.edu.vn.ecommerce.R;

public class SliderAdapter extends PagerAdapter {
    private Context context;
    private LayoutInflater layoutInflater;

    public SliderAdapter(Context context) {
        this.context = context;
    }

    public int imageArray[] = {
            R.drawable.board,
            R.drawable.board2,
            R.drawable.board3

    };
    public int headingArray[] = {
            R.string.first_slide,
            R.string.second_slide,
            R.string.first_slide
    };
    public int description[] = {
            R.string.description_slide1,
            R.string.description_slide1,
            R.string.description_slide1
    };


    @Override
    public int getCount() {
        return headingArray.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (ConstraintLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.sliding_layout,container,false);
        ImageView imageView  = view.findViewById(R.id.imgBoard);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txtHeading = view.findViewById(R.id.txtHeading);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView txtDescription = view.findViewById(R.id.txtDescription);
        imageView.setImageResource(imageArray[position]);
        txtHeading.setText(headingArray[position]);
        txtDescription.setText(description[position]);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
