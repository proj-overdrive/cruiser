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
    contentPaddingState: SkieSwiftStateFlow<Foundation_layoutPaddingValues>,
    contentSpotsState: SkieSwiftStateFlow<[Spot]>
) -> MapWithSwiftViewFactory {
    return MapSwiftViewContainer(
        contentPaddingState: contentPaddingState,
        contentSpotsState: contentSpotsState
    )
}

class MapSwiftViewContainer: MapWithSwiftViewFactory {
    
    var viewController: UIViewController
    
    init(
        contentPaddingState: SkieSwiftStateFlow<Foundation_layoutPaddingValues>,
        contentSpotsState: SkieSwiftStateFlow<[Spot]>) {
            self.viewController = UIHostingController(
                rootView: MapSwiftView(
                    contentPaddingState: contentPaddingState,
                    contentSpotsState: contentSpotsState
                )
            )
    }
}

struct MapSwiftView: View {
    var contentPaddingState: SkieSwiftStateFlow<Foundation_layoutPaddingValues>
    var contentSpotsState: SkieSwiftStateFlow<[Spot]>
    
    @Environment(\.colorScheme) var colorScheme
    @State var contentPadding: Foundation_layoutPaddingValues?
    @State var viewport = Viewport.camera(center: .init(latitude: 48.2082, longitude: 16.3719), zoom: 14, bearing: 0, pitch: 0)

    var body: some View {
        Map(viewport: $viewport) {
            let spots = contentSpotsState.value
            CircleAnnotationGroup(spots){ spot in
                CircleAnnotation(centerCoordinate: CLLocationCoordinate2D(latitude: spot.latitude, longitude: spot.longitude))
                        .circleColor(colorScheme == .light ? "#FF0000" : "#00FF00")
                        .circleRadius(10.0)
                }
            .slot("top")
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
        .onChange(of: contentSpotsState.value) { newSpots in
            if let firstSpot = newSpots.first {
                viewport = Viewport.camera(center: .init(latitude: firstSpot.latitude, longitude: firstSpot.longitude), zoom: 14, bearing: 0, pitch: 0)
            }
        }
    }
}

extension Spot: Identifiable {}
