# Janji
Saya Muhammad Alvinza dengan NIM 2304879 mengerjakan Tugas Praktikum 5 dalam mata kuliah Desain dan Pemrograman Berorientasi Objek untuk keberkahanNya maka saya tidak melakukan kecurangan seperti yang telah dispesifikasikan. Aamiin.

## Desain Program
![General](https://github.com/user-attachments/assets/a792c9d8-593f-47f2-bc40-8886e52bacda)  
Program ini dapat melakukan Create Update Delete (CRUD) dan menampilkan data-data mahasiswa dari Database db_mahasiswa pada tabel mahasiswa.    
Atribut yang dimiliki adalah NIM, Nama, Jenis Kelamin, dan Umur mahasiswa.  
Pada input NIM, kita perlu memasukkan angka pada TextField yang disediakan, sama seperti Nama.    
Pada Jenis Kelamin, kita memilih salah satu dari dua gender yang valid pada drop down.  
Pada Umur, kita memilih umur yang sesuai dengan menggunakan Slider.  

## Alur Program
Alur dibagi menjadi 3, Create Update dan Delete.  
### Create (Add)
Kita dapat menambahkan data dengan fitur add ini. Kita perlu memasukkan data yang benar ke kolom yang sesuai dan mengklik tombol "Add". Data akan tersimpan pada database dan akan direfresh.  
Before:   
![before insert](https://github.com/user-attachments/assets/dac32451-2a2a-4aa8-b38f-75e26061d60a)    
after:  
![after insert](https://github.com/user-attachments/assets/6af28c63-b794-4784-a207-18aa0973fa10)   

### Update
Ketika kita memilih salah satu baris data, kita dapat memodifikasinya dengan Update. Kita hanya perlu mengklik salah satu baris dan mengubah data pada kolom dan klik tombol "Update" untuk mengubah data pada Database.  
Before:  
![after insert](https://github.com/user-attachments/assets/a5c3b850-70a8-405b-a42a-fd4f8535359f)  
after:  
![after update](https://github.com/user-attachments/assets/e68af928-8f2c-4953-8264-5e7142525d58)  


### Delete
Ketika kita tidak membutuhkan data suatu baris, kita dapat menghapusnya dengan tombol "Delete". Kita hanya perlu mengklik salah satu baris yang akan dihapus, lalu kita klik tombol "Delete" dan data akan dihapus dari Database.  
Before:  
![after update](https://github.com/user-attachments/assets/4dbed5d7-38ed-46fa-b056-ecbf381ccb22)  
After:  
![after delete](https://github.com/user-attachments/assets/4fcbeb98-1d69-4ee5-b12c-b14c29467fdd)

## Error Handling
Terdapat 2 error handling  
### Insert/Update tapi ada kolom kosong  
![error saat insert tapi ada yang kosong](https://github.com/user-attachments/assets/1a6d5fc2-15d4-4172-9802-4b5527f3f107)
![error saat update tapi ada yang kosong](https://github.com/user-attachments/assets/8f7eeca3-f217-4a2f-b88e-c944fa8c9170)
### Insert/Update tapi NIM sudah ada  
![error duplikat nim](https://github.com/user-attachments/assets/5fe2e4b5-cfc3-4f9b-8d7d-a62913d564c8)



