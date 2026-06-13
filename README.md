# Mobile Programming — Pemrograman Perangkat Bergerak

Repository ini berisi kumpulan project praktikum dan tugas mata kuliah **Pemrograman Perangkat Bergerak (PPB)**. Seluruh project dibuat menggunakan **Kotlin** dan **Android Studio**, dengan pendekatan pengembangan Android modern seperti **Jetpack Compose**, **Room Database**, **MVVM**, **Retrofit**, dan **REST API**.

Repository ini digunakan sebagai dokumentasi proses belajar sekaligus arsip source code dari setiap pertemuan praktikum.

---

## Identitas

**Nama:** Thalita Aika Rahmani <br>
**NRP:** 5025231058 <br>
**Kelas:** Pemrograman Perangkat Bergerak B <br>
**Program Studi:** Teknik Informatika — Institut Teknologi Sepuluh Nopember

---

## Blog Dokumentasi

Dokumentasi lengkap setiap pertemuan dapat dilihat melalui blog berikut:

**Blog utama PPB:**
https://aikathatappbclass.blogspot.com/

---

## Tech Stack

Beberapa teknologi yang digunakan dalam repository ini:

* Kotlin
* Android Studio
* Jetpack Compose
* Material Design 3
* Room Database
* MVVM Architecture
* Retrofit
* REST API
* Kotlin Coroutines
* StateFlow
* Coil Image Loading
* Navigation Compose
* Git & GitHub

---

## Struktur Repository

```text
Mobile-Programming
├── ets
│   └── MoneyNotesApp
├── week-2
│   └── HelloAndroid
├── week-3
│   └── HappyBirthday
├── week-5
│   └── DiceRoller
├── week-6
│   └── Calculator
├── week-7
│   └── MyLogin
├── week-11
│   └── MarketSiswa
├── week-12
│   └── LoginMVVMApp
├── week-13
│   └── StudentRegistrationApp
└── week-14
    └── NewsPulseApp
```

---

## Daftar Pertemuan dan Project

| Pertemuan | Topik                                            | Project / Materi                 | Blog                                                                                                     |
| --------- | ------------------------------------------------ | -------------------------------- | -------------------------------------------------------------------------------------------------------- |
| 1         | Review Perkembangan Teknologi Perangkat Bergerak | Artikel Review                   | [Buka Blog](https://aikathatappbclass.blogspot.com/2026/06/pertemuan-1-ppb-review-perkembangan.html)     |
| 2         | Membuat Aplikasi Hello Android                   | `week-2/HelloAndroid`            | [Buka Blog](https://aikathatappbclass.blogspot.com/2026/06/pertemuan-2-ppb-membuat-aplikasi-hello.html)  |
| 3         | Membuat Aplikasi Ulang Tahun                     | `week-3/HappyBirthday`           | [Buka Blog](https://aikathatappbclass.blogspot.com/2026/06/pertemaun-3-ppb-membuat-aplikasi-ulang.html)  |
| 5         | Komponen Button — Dice Roller                    | `week-5/DiceRoller`              | [Buka Blog](https://aikathatappbclass.blogspot.com/2026/06/pertemuan-5-ppb-komponen-button-dice.html)    |
| 6         | Membuat Aplikasi Kalkulator Sederhana            | `week-6/Calculator`              | [Buka Blog](https://aikathatappbclass.blogspot.com/2026/06/pertemuan-6-ppb-membuat-aplikasi.html)        |
| 7         | Membuat Halaman Simple Login                     | `week-7/MyLogin`                 | [Buka Blog](https://aikathatappbclass.blogspot.com/2026/06/pertemuan-7-ppb-membuat-halaman-simple.html)  |
| 11        | Membuat Aplikasi Marketplace Siswa               | `week-11/MarketSiswa`            | [Buka Blog](https://aikathatappbclass.blogspot.com/2026/06/pertemuan-8-ppb-membuat-aplikasi.html)        |
| 12        | Membuat Aplikasi Login Menggunakan MVVM          | `week-12/LoginMVVMApp`           | [Buka Blog](https://aikathatappbclass.blogspot.com/2026/06/pertemuan-12-ppb-membuat-aplikasi-login.html) |
| 13        | Membuat Aplikasi Registrasi Siswa                | `week-13/StudentRegistrationApp` | [Buka Blog](https://aikathatappbclass.blogspot.com/2026/06/pertemuan-13-ppb-membuat-aplikasi.html)       |
| 14        | Membuat Aplikasi Berita Menggunakan REST API     | `week-14/NewsPulseApp`           | [Buka Blog](https://aikathatappbclass.blogspot.com/2026/06/pertemuan-14-ppb-membuat-aplikasi.html)       |

---

## Ringkasan Project

### Week 2 — HelloAndroid

Project sederhana untuk menampilkan teks **Hello Android!** menggunakan Kotlin dan Jetpack Compose. Project ini menjadi pengenalan awal terhadap struktur project Android.

### Week 3 — HappyBirthday

Aplikasi kartu ucapan ulang tahun sederhana. Project ini melatih penggunaan layout, teks, styling, dan komponen dasar Jetpack Compose.

### Week 5 — DiceRoller

Aplikasi lempar dadu interaktif. Pengguna dapat menekan tombol untuk menghasilkan angka dadu secara acak, kemudian gambar dadu akan berubah sesuai hasil.

### Week 6 — Calculator

Aplikasi kalkulator sederhana yang dapat melakukan operasi penjumlahan, pengurangan, perkalian, dan pembagian. Project ini melatih penggunaan input, state, dan event handling.

### Week 7 — MyLogin

Aplikasi halaman login sederhana dengan input email dan password. Tampilan dibuat lebih menarik dengan gambar karakter dan ikon login sosial.

### Week 11 — MarketSiswa

Aplikasi marketplace siswa sederhana. Project ini memiliki halaman daftar produk, tambah produk, kategori produk, dan profil pengguna.

### Week 12 — LoginMVVMApp

Aplikasi login menggunakan arsitektur **MVVM** dan **Room Database**. Project ini memisahkan layer data, repository, viewmodel, dan UI screen.

### Week 13 — StudentRegistrationApp

Aplikasi registrasi siswa dengan fitur **CRUD** menggunakan **Room Database**. Pengguna dapat menambahkan, menampilkan, mengedit, dan menghapus data siswa.

### Week 14 — NewsPulseApp

Aplikasi berita menggunakan **REST API** dengan Retrofit. Aplikasi ini memiliki fitur home news, detail berita, search news, saved news, loading state, error state, dan image loading menggunakan Coil.

---

## Cara Menjalankan Project

1. Clone repository ini.

```bash
git clone https://github.com/aikathalita/Mobile-Programming.git
```

2. Buka Android Studio.

3. Pilih salah satu folder project yang ingin dijalankan, misalnya:

```text
week-14/NewsPulseApp
```

4. Tunggu proses Gradle Sync selesai.

5. Jalankan aplikasi menggunakan emulator atau perangkat Android.

---

## Catatan untuk Project REST API

Project `week-14/NewsPulseApp` menggunakan API key dari NewsAPI. API key tidak disimpan langsung di source code agar aman untuk repository publik.

Tambahkan API key pada file `local.properties` di dalam project:

```properties
NEWS_API_KEY=isi_api_key_kamu
```

File `local.properties` tidak perlu dipush ke GitHub karena berisi data sensitif.

---

## Status

Repository ini digunakan untuk dokumentasi pembelajaran dan pengumpulan tugas mata kuliah **Pemrograman Perangkat Bergerak**.

---

## Author

**Thalita Aika Rahmani** <br>
Teknik Informatika — Institut Teknologi Sepuluh Nopember
