# 🚀 SnapBin – “Take Snap, Clean Bin”

🌍 **Smart Waste Reporting System | Android App | Human-Centred Design**

---

## 📌 Overview
SnapBin is a **mobile application built for Android** that enables users to report unattended waste directly to local authorities like **Dublin City Council**.  

The application simplifies the process of identifying, reporting, and tracking waste using **real-time location, images, and user input**, helping create **cleaner and smarter cities**.

---

## 💡 Problem Statement
Urban areas generate massive waste, but:
- ❌ Many issues go unreported  
- ❌ Reporting systems are slow and inefficient  
- ❌ Lack of community involvement  
- ❌ No real-time tracking of waste  

👉 SnapBin solves this by making reporting **fast, simple, and community-driven**.

---

## 🎯 Key Features

### 📸 Smart Waste Reporting
- Capture waste using camera  
- Retake option for clarity  
- Add:
  - 📍 Location (auto-detected)
  - 📝 Description
  - ⚠️ Urgency level
  - 🗑️ Waste type & size  
- Submit report instantly  

---

### 🗺️ Live Map Integration
- Real-time location tracking  
- View nearby reports  
- Interactive and clean UI  

---

### 🔐 User Authentication
- Secure signup/login  
- Firebase Authentication (Email & Password)  
- Persistent user sessions  

---

### 🏛️ Admin Dashboard (Council)
- View all reports  
- Mark waste as ✅ **Cleaned**  
- Archive completed reports  
- Enables efficient decision-making  

---

### 📊 Reports Management
- View:
  - 📂 Submitted reports  
  - 🗑️ Deleted reports  
  - 📍 Nearby reports  
- Real-time data sync using Firebase  

---

### 🌐 Globalization
- Multi-language support:
  - 🇬🇧 English  
  - 🇫🇷 French  
  - 🇩🇪 German  
  - 🇮🇹 Italian  

---

### 🤝 Community Events
- Create clean-up events  
- Add date, time, and description  
- Publicly visible to all users  
- Encourages social impact  

---

### 👤 Profile Management
- Edit user details  
- Add:
  - 📞 Phone number  
  - 🎂 Date of birth  
  - 🖼️ Profile picture  

---

### 🔔 Notifications
- Event alerts  
- Updates and engagement  

---

### 🔥 Heatmaps & Analytics
- Visualize waste density  
- Track cleaned areas  
- Helps authorities identify hotspots  

---

## 🏗️ System Architecture

### 📱 Frontend
- Kotlin  
- Jetpack Compose  
- MVVM Architecture  

### ☁️ Backend
- Firebase Authentication  
- Firestore Database  
- Firebase Storage  
- Cloud Messaging  

---

## 🧠 Architecture Pattern
- MVVM (Model-View-ViewModel)  
- Clean separation of logic, UI, and data  

---

## 🗄️ Database Design

### Collections:
- 👤 Users  
- 📅 Events  
- 🗑️ Trash Reports  

Each report includes:
- Location (GeoPoint)  
- Timestamp  
- Description  
- Urgency  
- Waste type  
- Image URLs  
- User reference  

---

## ⚙️ Technologies Used
- Kotlin  
- Android Studio  
- Firebase (Auth, Firestore, Storage)  
- Google Maps API  
- Jetpack Compose  
- MVVM  

---

## 🧪 Testing
- ✔️ Black Box Testing  
- ✔️ White Box Testing  
- ✔️ Regression Testing  
- ✔️ Manual Testing  

---

## 📈 Impact
- 🌍 Improves urban cleanliness  
- 🤝 Encourages community participation  
- 📊 Enables data-driven decisions  
- 🏙️ Supports smart city development  

---

## 🚧 Future Improvements
- 🤖 AI-based waste detection  
- 📱 iOS version  
- 🎮 Gamification (badges/rewards)  
- 📡 Real-time council tracking  
- 🌐 Expansion to other cities  

---

## 📸 Screenshots
> *(Add your app screenshots here: Home, Camera, Map, Reports, Events, Profile)*

---

## 📦 Installation
```bash
git clone https://github.com/your-username/snapbin.git
