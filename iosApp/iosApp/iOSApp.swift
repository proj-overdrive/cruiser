import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    init() {
        ViewFactoryKt.setViewFactories(
            mapWithSwiftViewFactory: mapWithSwiftViewFactory
        )
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
