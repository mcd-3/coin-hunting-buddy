# ViewModels
This directory contains ViewModel classes and the ViewModelFactory class.

Use the ViewModelFactory class to create ViewModels like so (you will need to import ViewModelProvider):
```kt
val mainViewModel = ViewModelProvider(
    applicationContext,
    ViewModelFactory(this.application)
).get(ActivityViewModel::class.java)
```

This project will use ViewModels on an Activity basis (1 ViewModel per Activity)