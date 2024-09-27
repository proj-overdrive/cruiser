//
//  MapSwiftUI.swift
//  iosApp
//
//  Created by Ethan Wright on 2024-09-24.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import ComposeApp
import MapboxMaps

func mapWithSwiftViewFactory(
    contentPaddingState: SkieSwiftStateFlow<Foundation_layoutPaddingValues>
) -> MapWithSwiftViewFactory {
    return MapSwiftViewContainer(
        contentPaddingState: contentPaddingState
    )
}

class MapSwiftViewContainer: MapWithSwiftViewFactory {
    
    var viewController: UIViewController
    
    init(contentPaddingState: SkieSwiftStateFlow<Foundation_layoutPaddingValues>) {
        self.viewController = UIHostingController(rootView: MapSwiftView(contentPaddingState: contentPaddingState))
    }
}

struct MapSwiftView: View {
    var contentPaddingState: SkieSwiftStateFlow<Foundation_layoutPaddingValues>
    
    @Environment(\.colorScheme) var colorScheme
    @State var contentPadding: Foundation_layoutPaddingValues?
    @State var viewport = Viewport.camera(center: .init(latitude: 48.2082, longitude: 16.3719), zoom: 14, bearing: 0, pitch: 0)

    var body: some View {
        Map(viewport: $viewport) {
        }
        .ornamentOptions(
            OrnamentOptions(
                logo: LogoViewOptions(margins: CGPoint(x: CGFloat(8.0), y: CGFloat($contentPadding.wrappedValue?.calculateBottomPadding() ?? 0.0))),
                attributionButton: AttributionButtonOptions(margins: CGPoint(x: CGFloat(8.0), y: CGFloat($contentPadding.wrappedValue?.calculateBottomPadding() ?? 0.0)))
            )
        )
        .mapStyle(.standard(lightPreset: colorScheme == .light ? .day : .dusk))
        .ignoresSafeArea()
        .task {
            for await contentPadding in contentPaddingState {
                self.contentPadding = contentPadding
            }
        }
    }
}
