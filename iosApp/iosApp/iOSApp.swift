import SwiftUI
import ComposeApp
import GoogleSignIn

class AppDelegate: NSObject, UIApplicationDelegate {

    func application(
    _ app: UIApplication,
    open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]
    ) -> Bool {
        var handled: Bool
        
        // Add any more custom url handles below
        handled = GIDSignIn.sharedInstance.handle(url)

        return handled
    }
}

@main
struct iOSApp: App {
    @UIApplicationDelegateAdaptor(AppDelegate.self) var delegate
    
    init() {
        ViewFactoryKt.setViewFactories(
            mapWithSwiftViewFactory: mapWithSwiftViewFactory
        )
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView().onOpenURL(perform: { url in
                GIDSignIn.sharedInstance.handle(url)
            })
        }
    }
}
