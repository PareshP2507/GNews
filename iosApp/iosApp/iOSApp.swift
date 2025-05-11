import SwiftUI
import ComposeApp

@main
struct iOSApp: App {
    
    init() {
        AppInitializerKt.doInitKoin(onKoinStart: { _ in})
    }
    
    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}
