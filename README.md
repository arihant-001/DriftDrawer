# PopOutNavigationDrawer
It is a library for a custom Naviagation Drawer

<img src="./images/sample.gif">

## Usage

### Builder

1. Create a list of icons for navigation items

``` kotlin
  val icons = ArrayList<Int>()
  icons.add(R.drawable.ic_archive_black_24dp)
  icons.add(R.drawable.ic_apps_black_24dp)
  icons.add(R.drawable.ic_border_color_black_24dp)
  icons.add(R.drawable.ic_build_black_24dp)

```
2. Build the drawer programmatically using `PopOutNavBuilder`.

``` kotlin
  val popOutNavLayout = PopOutNavBuilder(context, toolbar)
          .withMenus(icons)
          .build()
```

### Builder Extras
``` kotlin
val popOutNavLayout = PopOutNavBuilder(this, toolbar)
                .withMenus(icons)
                .withDrawerClosed(false) // set initial state of drawer
                .withColors(Color.parseColor("#E91E63"), Color.parseColor("#9C27B0")) // sets background and item highlight colors
                .withItemClickListener(navItemListener) // sets item click listener
                .build()
```
