# Film Veritabanı Android Uygulaması

Bu uygulama; kullanıcıların, <a href="https://www.omdbapi.com/"> OMDb API</a> üzerinden film araması yapmasına imkan sağlayan bir Android uygulamasıdır. Kullanıcılar arama çubuğuna yazdıkları filmi arama yapabilir, gelen sonuçlardan ilgili filme tıkladıklarında da tekrar aynı site üzerindena alınan detaylar yüklenir. Gelen film listesi 1 sayfadan fazla ise aşağı kaydırdıkça sonraki sayfalar da sonuçlara eklenir. Listede veya detay sayfasındaki favorilere ekle/çıkar butonlarıyla filmler favori listesine eklenip çıkartılır ve bu liste Room kütüphanesi yardımıyla saklanır.

## Film Arama Ekranı

Uygulamadaki tüm string değerler strings dosyasından alındığı için, uygulamanın çoklu dil desteği vardır. Benzer şekilde görsel stiller ve boyutlar da kolay yönetilmek için ayrı ve tek dosyadan okunmaktadır. Film arama ekranında, arama kutucuğuna yazılan film sitede arama yapılır ve sonuçlar döndürülür. Sitede parçalı arama desteklenmediği için filmin tam adını yazmak gereklidir. Filmlerin yanındaki favori durumu, Room kütüphanesi yardımıyla oluşturulan bir yerel veritabanında saklanır. Favor butonuna tıklandığında favori durumu değişir ve ilgili filmin olduğu kısım güncellenir.

<a href="https://github.com/yemregul94/Android-Movie-App/blob/main/screenshots/list_TR.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Movie-App/blob/main/screenshots/list_TR.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Android-Movie-App/blob/main/screenshots/list_EN.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Movie-App/blob/main/screenshots/list_EN.png" width="200" style="max-width:100%;"></a>

## Sonsuz Listeleme Ekranı

Film listesi API üzerinden sayfalar halinde gelmektedir ve her sayfada 10 film yer almaktadır. Sonraki sayfaya geçmek için tekrar bir API sorgusu yapılması gerekmektedir ve bu sorgu, sayfanın sonuna gelindiğinde otomatik olarak yapılır. Gelen yeni sonuçlar, önceki sonuçlara eklenir ve sayfa geçişi 2 yönlü olarak sağlanır.

<a href="https://github.com/yemregul94/Android-Movie-App/blob/main/screenshots/paging_TR.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Movie-App/blob/main/screenshots/paging_TR.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Android-Movie-App/blob/main/screenshots/paging_EN.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Movie-App/blob/main/screenshots/paging_EN.png" width="200" style="max-width:100%;"></a>

## Detay Ekranı

Listeden seçilen filmin ID'si ile sunucuda detay sayfası isteği oluşturulur ve gelen detaylar bu sayfada gösterilir. Film detayları İngilizce olduğu için uygulamanın diğer dillerinde de içerik kısmı aynı olacaktır. Bu ekranda da tıpkı liste ekranı gibi favorilere ekleme ve çıkartma özelliği mevcuttur.

<a href="https://github.com/yemregul94/Android-Movie-App/blob/main/screenshots/details_TR.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Movie-App/blob/main/screenshots/details_TR.png" width="200" style="max-width:100%;"></a>

<a href="https://github.com/yemregul94/Android-Movie-App/blob/main/screenshots/details_EN.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Movie-App/blob/main/screenshots/details_EN.png" width="200" style="max-width:100%;"></a>

## Hata Ekranı

Sunucudan hata dönmesi durumunda hata mesajı basit bir şekilde ekrana yansıtılır. Örneğin görselde "ba" kelimesi içeren film sayısı, sunucunun yollayabileceğinden fazla olduğu için sunucudan görseldeki hata mesajı döner. 

<a href="https://github.com/yemregul94/Android-Movie-App/blob/main/screenshots/error.png" target="_blank">
<img src="https://github.com/yemregul94/Android-Movie-App/blob/main/screenshots/error.png" width="200" style="max-width:100%;"></a>


## Kullanılar Teknolojiler ve Yapılar

- Kotlin & Android
- MVVM
- Retrofit
- Glide
- Room
- Coroutines
- Dagger & Hilt
- Data Binding
- View Binding
- Fragments
- StateFlow
