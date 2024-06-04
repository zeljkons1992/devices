// iosMain/AppDelegate.swift
import Foundation
import shared

func startKoin() {
    KoinKt.doInitKoin()
}

@main
struct iOSApp: App {
    init() {
        startKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
