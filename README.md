Android MVVM template project
======

This project demonstrates how to use [Android Architecture Components][1] (ViewmModel, DataBinding, LiveData, Lifecycle aware) along with [Realm][2] database and [Dagger 2][3] dependency injection to build a robust application (scalable, testable, less bug, easy to maintain and support offline mode).

## App

I used [official Github Rest API][4] to develop a Github Application on Android with a lot of features like github webpage.

This project focuses on android application architecture, so, to faster my work, I used some resources and helper classes of [Fasthub][5] open source project, but the architecture is definitely different (he used MVP design pattern and SQLite database). Thanks [k0shk0sh][6] so much for his amazing app!

Sample app:

[<img src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png"
      alt="Download from Google Play"
      height="80">](https://play.google.com/store/apps/details?id=com.duyp.architecture.mvvm)

### Architecture overview
Comming soon...

### Persistence - why Realm, not Room?
[Room Persistence Library][14] is a part of google's android acrhitecture components announced Google IO 2017. It provides an abstraction layer over SQLite to allow fluent database access while harnessing the full power of SQLite, works well with [LiveData][14].

But, SQLite is not simple, and relational database is not easy for a lot of android developers. In almost android application, local database is offen used for data caching only, and isn't center of bussiness processes.

[Realm][2] is a mobile database, a replacement of SQLite and ORMs. It is much more simple than SQLite, but really powerfull, easy to learn in minutes, not hours, for every mobile deveopers, both Android and iOS. Even "Realm Database is much faster than an ORM, and often faster than raw SQLite". With lazy data loading, your app can easy to archive better performance and user experience, it's really difficult if you are using Room.

`Room works well with LiveData and lifecycle aware to prevent memory leaks, how about Realm ?`

It dosen't matter, Realm can easy to adapt with LiveData, read more: [Android Architecture Components and Realm][16].

### Testing
JUnit 4, [Mockito][7], [PowerMock][8] and [Robolectric][9] for Unit testing.

[Espresso][12] and [UI Automator][13] for automated UI testing.

## References
* [Fasthub][5] open source project
* [Google Architecture Components sample][10] project
* [MVVM countries][11]

## Screenshots
| Feeds | Drawer |
|:-:|:-:|
| ![First](/screenshots/1.png?raw=true) | ![Sec](/screenshots/2.png?raw=true) |

| Repo | Profile |
|:-:|:-:|
| ![Third](/screenshots/7.png?raw=true) | ![Fourth](/screenshots/5.png?raw=true) |



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

[14]: https://developer.android.com/topic/libraries/architecture/room.html
[15]: https://developer.android.com/topic/libraries/architecture/livedata.html
[16]: https://academy.realm.io/posts/android-architecture-components-and-realm/


License
=======

    Copyright 2017 Duy Pham.

Licensed under the the [GPL-3.0](https://www.gnu.org/licenses/gpl.html) license.
    
See the [LICENSE](https://github.com/duyp/mvvm-template/blob/master/LICENSE) file for the whole license text.
