MediaNotification是一个暂时有些不稳定的尝试来创建Android O风格的媒体通知的应用程序。请注意，这个应用程序不会取代来自其他应用程序的媒体通知，它会自行创建新的通知。最常用的使用方法如下：

**方法一**
保持原始的音乐播放器通知不变。 该应用程序将读取其数据，并创建一个新的通知，来全面操作专辑封面和媒体控件。只要“使用广播接收器”开关被禁用，使用这个应用程序不应该有任何问题。如果您在Android Nougat或更高版本上使用此选项，则建议使用强力通知管理（可在SystemUI Tuner中启用）将音乐播放器设置在列表底部显示通知，并从状态栏中隐藏图标（ 将设置更改为“1”，如果将其设置为“0”，则效果与方法2相同）。这已经过测试，并在以下音乐播放器上正常工作：
  - Bandcamp
  - BlackPlayer
  - Google Chrome (yes, the web browser)
  - Google Play Music
  - Jair Player
  - Phonograph
  - PlayerPro
  - Pulsar
  - Poweramp
  - Retro Player
  - Shuttle
  - Soundcloud
  - Spotify

**方法二**
阻止安装在手机上的所有音乐播放器的所有通知，并启用设置菜单底部的“使用广播接收器”开关。 然后，应用程序将从BroadcastReceiver获取所有信息，并从外部存储（如果歌曲正在本地播放）或从last.fm api获取专辑封面。与此选项一起使用时，通知功能完全正常的唯一音乐播放器是Spotify。 大多数其他应用程序在播放器控件（播放器控件不支持的应用程序默认被禁用，检查“媒体控件方法”设置）和内容意图（当单击通知时）时会有问题。 但是，有几个应用程序的内容意图仍然正常工作：
  - BlackPlayer
  - Phonograph
  - Timber
  - Jockey
  - Jair Player
  - Pulsar
  - NewPipe

作为一种解决方法，如果通知不能以任何其他方式获得内容意图，则可以设置用于打开通知的“默认音乐播放器”。

**重要:** 以下音乐播放器需要修改其设置才能正常工作：
  - Spotify: 在设置菜单中启用“设备广播”。
  - Shuttle: 打开last.fm Scrobbler（你不需要下载安装Scrobbler）。
  - AIMP: 必须启用“集成锁定屏幕”设置
  - Jair Player: 在设置菜单底部启用Scrobbler。 为了正常工作您，您将需要安装Scrobbler（卸载它将禁用该设置）。
  - NewPipe: 对这个播放器的支持确实存在，但是非常有限。 建议在New Pope的设置菜单中打开“使用外部音频播放器”。
  - BlackPlayer: 打开“音乐作品音乐”设置（位于“元数据”部分的底部）。 你不需要安装Scrobbler。
  - 网易云音乐: 在设置中的通知栏样式中选择“系统样式”。

**方法三**
以某种方式在您的设备的系统分区上安装应用程序，并授予它“android.permission.UPDATE_APP_OPS_STATS”权限。 对于普通用户来说这是不可能的，因为`UPDATE_APP_OPS_STATS`权限受应用程序签名的保护。 换句话说，您将需要首先编译一个与其他系统应用程序具有相同签名的新apk。 我正在努力绕过这个权限，并使第三个选项功能只有root访问权限，但它将是一段时间才能完成。

---

不幸的是，这个应用程序与以下音乐播放器一起使用时根本不起作用。这是因为他们不使用MediaPlayer API，或者他们也使用它，但阻止其他应用程序拦截来自它的任何信息。
  - iHeartRadio
  - Rocket Player
