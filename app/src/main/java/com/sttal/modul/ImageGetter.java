package com.sttal.modul;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.concurrent.CompletableFuture;

public class ImageGetter implements Html.ImageGetter {

    private final Resources res;
    private final TextView htmlTextView;

    public ImageGetter(Resources res, TextView htmlTextView) {
        this.res = res;
        this.htmlTextView = htmlTextView;
    }

    @Override
    public Drawable getDrawable(String url) {
        BitmapDrawablePlaceHolder holder = new BitmapDrawablePlaceHolder(res, null);

        // Asynchronously download the image in the background
        CompletableFuture.runAsync(() -> {
            try {
                // Downloading image in bitmap format using [Picasso] Library
                Bitmap bitmap = Picasso.get().load(url).get();
                Drawable drawable = new BitmapDrawable(res, bitmap);

                // To make sure Images don't go out of screen, Setting width less
                // than screen width, You can change image size if you want
                int width = getScreenWidth() - 150;

                // Images may stretch out if you will only resize width,
                // hence resize height to according to aspect ratio
                float aspectRatio = (float) drawable.getIntrinsicWidth() / drawable.getIntrinsicHeight();
                int height = (int) (width / aspectRatio);
                drawable.setBounds(10, 20, width, height);
                holder.setDrawable(drawable);
                holder.setBounds(10, 20, width, height);

                // Update the text view on the main thread
                htmlTextView.post(() -> htmlTextView.setText(htmlTextView.getText()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        return holder;
    }

    // Actually Putting images
    private static class BitmapDrawablePlaceHolder extends BitmapDrawable {
        private Drawable drawable;

        BitmapDrawablePlaceHolder(Resources res, Bitmap bitmap) {
            super(res, bitmap);
        }

        @Override
        public void draw(Canvas canvas) {
            if (drawable != null) {
                drawable.draw(canvas);
            }
        }

        void setDrawable(Drawable drawable) {
            this.drawable = drawable;
        }
    }

    // Function to get screenWidth used above
    private int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }
}
