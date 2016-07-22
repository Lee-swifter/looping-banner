# looping-banner
An infinite loop banner view for Android. This library can be used for app's banner view and auto scrolled.

---

## Import:

##### Gradle:
    compile 'lic.swifter.banner:banner:0.0.1'

##### Maven:
    <dependency>
      <groupId>lic.swifter.banner</groupId>
      <artifactId>banner</artifactId>
      <version>0.0.1</version>
      <type>pom</type>
    </dependency>

## How to use:

```xml
<lic.swifter.banner.LoopingViewPager
    android:layout_width="match_parent"
    android:layout_height="240dip"
    banner:auto_scroll="true"
    banner:loop_interval="3000" />
```

In your code:

```java
loopingViewPager = (LoopingViewPager) findViewById(R.id.looping_view_pager);
loopingViewPager.setInterVal(2000);

loopingViewPager.setAdapter(new LoopingPagerAdapter(){...});
loopingViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) { }

    @Override
    public void onPageSelected(int position) {
        //Use getCurrentPage() instead of getCurrentItem()
        int currentPosition = loopingViewPager.getCurrentPage();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        //this code is handle scroll strategy when touching
        if(loopingEnable) {
            switch (state) {
                case ViewPager.SCROLL_STATE_DRAGGING:
                    loopingViewPager.stopScroll();
                    break;
                case ViewPager.SCROLL_STATE_IDLE:
                    loopingViewPager.startScroll();
                    break;
            }
        }
    }
});
```

Use `startScroll()` and `stopScroll()` to get the scroll strategy under your control.
Use `setInterVal()` the set the auto scroll interval.

### Attention:

- Use `LoopingPagerAdapter` instead of `PageAdapter`
- Use `getCurrentPage()` instead of `getCurrentItem`

### All Attributes
```xml
<declare-styleable name="looping_banner">
    <attr name="loop_interval" format="integer" />
    <attr name="auto_scroll" format="boolean" />
</declare-styleable>
```

## LICENSE

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.