package com.example.cacau2.ecovoicetest;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.SparseArray;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.maps.android.clustering.Cluster;
import com.google.maps.android.clustering.ClusterManager;
import com.google.maps.android.clustering.view.DefaultClusterRenderer;
import com.google.maps.android.ui.IconGenerator;

public class CustomClusterRenderer extends DefaultClusterRenderer<Tree_item> {
    private final Context mContext;
    private final IconGenerator mClusterIconGeneratorM1;
    private final IconGenerator mClusterIconGeneratorM2;
    private final IconGenerator mClusterIconGeneratorM3;
    private final IconGenerator mClusterIconGeneratorM4;
    private final IconGenerator mClusterIconGeneratorM5;
    private Drawable cluster1;
    private Drawable cluster2;
    private Drawable cluster3;
    private Drawable cluster4;
    private Drawable cluster5;

    private SparseArray<BitmapDescriptor> mIcons = new SparseArray<BitmapDescriptor>();

    public CustomClusterRenderer(Context context, GoogleMap map,
                                 ClusterManager<Tree_item> clusterManager) {

        super(context, map, clusterManager);
        mContext = context;
        cluster1 = mContext.getResources().getDrawable(R.drawable.m1);
        cluster2 = mContext.getResources().getDrawable(R.drawable.m2);
        cluster3 = mContext.getResources().getDrawable(R.drawable.m3);
        cluster4 = mContext.getResources().getDrawable(R.drawable.m4);
        cluster5 = mContext.getResources().getDrawable(R.drawable.m5);

        mClusterIconGeneratorM1 = new IconGenerator(mContext.getApplicationContext());
        mClusterIconGeneratorM2 = new IconGenerator(mContext.getApplicationContext());
        mClusterIconGeneratorM3 = new IconGenerator(mContext.getApplicationContext());

        mClusterIconGeneratorM4 = new IconGenerator(mContext.getApplicationContext());
        mClusterIconGeneratorM5 = new IconGenerator(mContext.getApplicationContext());
        setupIconGen(mClusterIconGeneratorM1, cluster1, mContext);
        setupIconGen(mClusterIconGeneratorM2, cluster2, mContext);
        setupIconGen(mClusterIconGeneratorM3, cluster3, mContext);
        setupIconGen(mClusterIconGeneratorM4, cluster4, mContext);
        setupIconGen(mClusterIconGeneratorM5, cluster5, mContext);


    }

    public Bitmap GetBitmapMarker(Context mContext, int resourceId, String mText) {
        try {
            Resources resources = mContext.getResources();
            float scale = resources.getDisplayMetrics().density;
            Bitmap bitmap = BitmapFactory.decodeResource(resources, resourceId);

            android.graphics.Bitmap.Config bitmapConfig = bitmap.getConfig();

            // set default bitmap config if none
            if (bitmapConfig == null)
                bitmapConfig = android.graphics.Bitmap.Config.ARGB_8888;

            bitmap = bitmap.copy(bitmapConfig, true);

            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
            paint.setColor(Color.WHITE);
            paint.setTextSize((int) (14 * scale));

            //paint.setShadowLayer(1f, 0f, 1f, Color.DKGRAY);

            // draw text to the Canvas center
            Rect bounds = new Rect();
            paint.getTextBounds(mText, 0, mText.length(), bounds);
            int x = (bitmap.getWidth() - bounds.width()) / 2;
            int y = (bitmap.getHeight() + bounds.height()) / 2;

            canvas.drawText(mText, x * scale, y * scale, paint);

            return bitmap;

        } catch (Exception e) {
            return null;
        }
    }

    private void setupIconGen(IconGenerator generator, Drawable drawable, Context context) {
        TextView textView = new TextView(context);
        textView.setTextAppearance(context, R.style.amu_ClusterIcon_TextAppearance);
        textView.setId(com.google.maps.android.R.id.amu_text);
        textView.setGravity(android.view.Gravity.CENTER);
        textView.setLayoutParams(new FrameLayout.LayoutParams(drawable.getIntrinsicWidth()+5, drawable.getIntrinsicHeight()+5));
        generator.setContentView(textView);
        generator.setContentPadding(2, 2, 2, 2);
        generator.setBackground(drawable);
    }

    @Override
    protected void onBeforeClusterItemRendered(Tree_item tree_item, MarkerOptions markerOptions) {
        /*mClusterIconGenerator.setBackground(
            ContextCompat.getDrawable(mContext, R.drawable.ic_arvore_cadastro));*/
        //mClusterIconGenerator.setTextAppearance(R.style.AppTheme_WhiteTextAppearance);
        //final Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(10));
        markerOptions.icon(BitmapDescriptorFactory.fromResource(R.drawable.tree_icon));

    }

    @Override
    protected void onBeforeClusterRendered(Cluster<Tree_item> cluster, MarkerOptions markerOptions) {
        super.onBeforeClusterRendered(cluster, markerOptions);
        int bucket = getBucket(cluster);

        if (cluster.getSize() > 1000) {
            setupIconGen(mClusterIconGeneratorM5, cluster5, mContext);

            Bitmap icon = mClusterIconGeneratorM5.makeIcon(String.valueOf(getClusterText(bucket)));

            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        } else if (cluster.getSize() > 500) {
            setupIconGen(mClusterIconGeneratorM4, cluster4, mContext);

            Bitmap icon = mClusterIconGeneratorM4.makeIcon(String.valueOf(getClusterText(bucket)));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        } else if (cluster.getSize() > 100) {
            setupIconGen(mClusterIconGeneratorM3, cluster3, mContext);

            Bitmap icon = mClusterIconGeneratorM3.makeIcon(String.valueOf(getClusterText(bucket)));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        } else if (cluster.getSize() > 20) {
            setupIconGen(mClusterIconGeneratorM2, cluster2, mContext);

            Bitmap icon = mClusterIconGeneratorM2.makeIcon(String.valueOf(getClusterText(bucket)));
            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        } else {
            setupIconGen(mClusterIconGeneratorM1, cluster1, mContext);
            Bitmap icon = mClusterIconGeneratorM1.makeIcon(String.valueOf(getClusterText(bucket)));

            markerOptions.icon(BitmapDescriptorFactory.fromBitmap(icon));
        }

    }

    /*@Override
    protected void onClusterItemRendered(Tree_item clusterItem, Marker marker) {

        super.onClusterItemRendered(clusterItem, marker);
        mClusterIconGenerator.setBackground(
            ContextCompat.getDrawable(mContext, R.drawable.m1));
        mClusterIconGenerator.setTextAppearance(R.style.amu_Bubble_TextAppearance_Dark);
        final Bitmap icon = mClusterIconGenerator.makeIcon(String.valueOf(10));
    }*/
}