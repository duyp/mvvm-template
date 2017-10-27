# Android MVVM template project

This project demonstrates how to use android [architecture components][1] (ViewmModel, DataBinding, LiveData, Lifecycle aware) along with [Realm][2] database and [Dagger 2][3] dependency injection to build a robust application (scalable, testable, less bug, easy to maintain and support offline mode).

## App
I used [official Github Rest API][4] to develop a Github Application on Android with a lot of features like github webpage.

This project focuses on android application architecture, so, to faster my work, I used some resources and helper classes of [Fasthub][5] open source project, but the architecture is definitely different (he used MVP design pattern and SQLite database). Thanks [k0shk0sh][6] so much for his amazing app!

## Testing
JUnit 4, [Mockito][7], [PowerMock][8] and [Robolectric][9] for Unit testing.

[Espresso][12] and [UI Automator][13] for automated UI testing.

## References
* [Fasthub][5] open source project
* [Google Architecture Components sample][10] project
* [MVVM countries][11]


[1]: https://developer.android.com/topic/libraries/architecture/index.html
[2]: https://realm.io
[3]: https://github.com/google/dagger
[4]: https://developer.github.com/v3/
[5]: https://github.com/k0shk0sh/FastHub
[6]: https://github.com/k0shk0sh

[7]: https://github.com/mockito/mockito
[8]: https://github.com/powermock/powermock
[9]: http://robolectric.org

[10]: https://github.com/googlesamples/android-architecture-components
[11]: https://github.com/patloew/countries

[12]: https://developer.android.com/training/testing/espresso/index.html
[13]: https://developer.android.com/training/testing/ui-automator.html
