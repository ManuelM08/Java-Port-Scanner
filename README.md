# 📋 Java Multi-Threaded Port Scanner

A high-performance, concurrent network utility built in **Java** to scan open TCP ports on a target IP address. This tool leverages modern Java concurrency features to provide fast and reliable results.

---

## 🚀 Features

* **Multi-Threading:** Uses a `FixedThreadPool` to scan multiple ports simultaneously, significantly reducing scan time.
* **Custom Timeout Management:** Includes a dedicated `Tp` (Timer) class to handle execution lifecycles and prevent "hanging" threads.
* **Thread-Safe Progress Tracking:** Real-time progress updates in the console using `AtomicInteger`.
* **Thread-Safe Data Collection:** Stores discovered open ports using `ConcurrentLinkedQueue` to ensure data integrity across multiple threads.
* **Clean Architecture:** Separated logic between the core scanning engine and the execution controller.

---

## 🛠️ Technical Stack

* **Language:** Java 8+
* **Concurrency:** `ExecutorService`, `AtomicInteger`, `ConcurrentLinkedQueue`
* **Networking:** `java.net.Socket`, `java.net.InetSocketAddress`
