import SwiftUI
import ComposeApp
import GoogleSignIn
import StripePaymentsUI

class AppDelegate: NSObject, UIApplicationDelegate {

    func application(
    _ app: UIApplication,
    open url: URL, options: [UIApplication.OpenURLOptionsKey : Any] = [:]
    ) -> Bool {
        StripeAPI.defaultPublishableKey = "pk_test_51QLd7JHSPwdfxfQKhJ3Xy8f91p7Y89ApSN2I7ZTgPM71TGQzogmNmbiQExBW9RSl5MtDf49Cr4aN6tWeQvm20cWD00ZxsJFaBD"
        
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
            mapWithSwiftViewFactory: mapWithSwiftViewFactory,
            stripePaymentViewFactory: stripePaymentViewFactory
        )
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView().onOpenURL(perform: { url in
                GIDSignIn.sharedInstance.handle(url)
            })
            .environment(\.colorScheme, .light)
        }
    }
}
