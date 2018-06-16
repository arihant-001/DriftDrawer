# PopOutNavigationDrawer
It is a library for custom Naviagation Drawer

<img src="./images/sample.gif" width="60%">

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
  PopOutNavBuilder(context, toolbar)
    .withMenus(icons)
    .build()
```

### ItemClickListener

- Using lamdas

```kotlin
val navItemListener: (Int, View) -> Unit = { pos: Int, view: View ->
    Toast.makeText(context, "Position: " + pos, Toast.LENGTH_SHORT).show()
  }
```

- This itemClickListener can be easily pass in builder

``` kotlin
  PopOutNavBuilder(context, toolbar)
    .withMenus(icons)
    .withItemClickListener(navItemListener)
    .build()
```

### Builder Extras

``` kotlin
val popOutDrawer = PopOutNavBuilder(this, toolbar)
    .withMenus(icons)
    .withDrawerClosed(false) // set initial state of drawer
    .withColors(Color.parseColor("#E91E63"), Color.parseColor("#9C27B0")) // sets background and item highlight colors
    .withItemClickListener(navItemListener) // sets item click listener
    .build()
```

### PopOutDrawer
`build` method of `PopOutNavBuilder` returns `PopOutDrawer`. This drawer can be used to control its behaviors.

Methods | Definition
------------ | -------------
isClosed | returns true if drawer is closed otherwise false
closeDrawer | close the drawer with/ without animation control by argument `animated`. Default value for `animated` is `true`
openDrawer | open the drawer with/ without animation control by argument `animated`. Default value for `animated` is `true`
setSelectedPosition | sets the position passed in argument as selected position
getLayout | returns the `PopOutNavLayout` for drawer
