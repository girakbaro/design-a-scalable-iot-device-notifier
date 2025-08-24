/**
 * uk7e_design_a_scalab.kt
 * 
 * Design a Scalable IoT Device Notifier
 * ====================================
 * 
 * This project aims to design and implement a scalable IoT device notifier system
 * that can handle a large number of devices and send personalized notifications
 * to users.
 * 
 * The system will consist of the following components:
 * 
 * 1. IoT Device Module: responsible for collecting data from IoT devices
 * 2. Notification Module: responsible for processing and sending notifications
 * 3. Database Module: responsible for storing device data and notification history
 * 
 * System Requirements:
 * 
 * 1. The system should be able to handle at least 10,000 IoT devices
 * 2. The system should be able to send at least 100 notifications per second
 * 3. The system should be able to store at least 1 million notification records
 * 
 * Design Considerations:
 * 
 * 1. Scalability: The system should be designed to scale horizontally to handle
 *    increasing loads
 * 2. Performance: The system should be optimized for performance to handle high
 *    volumes of data and notifications
 * 3. Reliability: The system should be designed to ensure high availability and
 *    reliability
 * 
 * Technical Requirements:
 * 
 * 1. The system should be built using Kotlin and run on a Linux-based platform
 * 2. The system should use a message broker (e.g. RabbitMQ) for communication
 *    between modules
 * 3. The system should use a NoSQL database (e.g. MongoDB) for storing device data
 *    and notification history
 */

import kotlinx.coroutines.*

class IoTDeviceNotifier {
    private val deviceDataStore: DeviceDataStore
    private val notificationService: NotificationService
    private val messageBroker: MessageBroker

    init {
        deviceDataStore = DeviceDataStore()
        notificationService = NotificationService()
        messageBroker = MessageBroker()
    }

    fun start() {
        launch {
            messageBroker.consumeMessages {
                processMessage(it)
            }
        }
    }

    private fun processMessage(message: Message) {
        when (message.type) {
            MessageType.DEVICE_DATA -> {
                val deviceData = message.data as DeviceData
                deviceDataStore.storeDeviceData(deviceData)
                notificationService.sendNotification(deviceData)
            }
            MessageType.NOTIFICATION_RESULT -> {
                val notificationResult = message.data as NotificationResult
                notificationService.processNotificationResult(notificationResult)
            }
            else -> {
                println("Unknown message type: ${message.type}")
            }
        }
    }
}

class DeviceDataStore {
    private val database: Database

    init {
        database = Database()
    }

    fun storeDeviceData(deviceData: DeviceData) {
        database.storeDeviceData(deviceData)
    }
}

class NotificationService {
    private val notificationSender: NotificationSender

    init {
        notificationSender = NotificationSender()
    }

    fun sendNotification(deviceData: DeviceData) {
        val notification = createNotification(deviceData)
        notificationSender.sendNotification(notification)
    }

    private fun createNotification(deviceData: DeviceData): Notification {
        // Create a notification based on the device data
        return Notification(deviceData)
    }
}

class MessageBroker {
    private val rabbitMQConnection: RabbitMQConnection

    init {
        rabbitMQConnection = RabbitMQConnection()
    }

    fun consumeMessages(onMessageReceived: (Message) -> Unit) {
        rabbitMQConnection.consumeMessages(onMessageReceived)
    }
}

data class Message(val type: MessageType, val data: Any)

enum class MessageType {
    DEVICE_DATA,
    NOTIFICATION_RESULT
}

data class DeviceData(val deviceId: String, val sensorData: String)

data class Notification(val deviceId: String, val message: String)

data class NotificationResult(val notificationId: String, val status: String)

class Database {
    fun storeDeviceData(deviceData: DeviceData) {
        // Store device data in the database
    }
}

class NotificationSender {
    fun sendNotification(notification: Notification) {
        // Send the notification to the user
    }
}

class RabbitMQConnection {
    fun consumeMessages(onMessageReceived: (Message) -> Unit) {
        // Consume messages from RabbitMQ and call onMessageReceived for each message
    }
}