# SnapListUtil
Snap List Util allow Recycler Views and List Views to smooth scroll to View Holder edge by adding a custom Smooth Scroll event to your list,
when scrolling down it will snap to the bottom view hodlersholders bottom edge and the to top view holders top edge when scrolling up

# Get it
Add it in your root build.gradle at the end of repositories:
```groovy
allprojects {
	repositories {
		...
		maven { url "https://jitpack.io" }
	}
}
```
Add the dependency
```groovy
dependencies {
        compile 'com.wscheffer.SnapListUtil:v0.0.2:SNAPSHOT'
}
```

#Use it create RecyclerAdapter

```java
public class MainActity extends Activity { 

   RecyclerView recyclerView;
   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        if (recyclerView != null) {
            recyclerView.setHasFixedSize(true);
            recyclerView.setAdapter(getAdapter()); 
            SnapListUtil.addSnapLayoutManager(recyclerView, SnapListUtil.LayoutManagerType.LINEAR, RecyclerView.VERTICAL, false);
        }
    }
}
```