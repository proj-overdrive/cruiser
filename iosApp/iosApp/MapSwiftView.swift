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
    contentLocationState: SkieSwiftStateFlow<Coordinate>,
    contentSpotsState: SkieSwiftStateFlow<[Spot]>
) -> MapWithSwiftViewFactory {
    return MapSwiftViewContainer(
        contentPaddingState: contentPaddingState,
        contentLocationState: contentLocationState,
        contentSpotsState: contentSpotsState
    )
}

class MapSwiftViewContainer: MapWithSwiftViewFactory {
    
    var viewController: UIViewController
    
    init(
        contentPaddingState: SkieSwiftStateFlow<Foundation_layoutPaddingValues>,
        contentLocationState: SkieSwiftStateFlow<Coordinate>,
        contentSpotsState: SkieSwiftStateFlow<[Spot]>
    ) {
            self.viewController = UIHostingController(
                rootView: MapSwiftView(
                    contentPaddingState: contentPaddingState,
                    contentLocationState: contentLocationState,
                    contentSpotsState: contentSpotsState
                )
            )
    }
}

struct MapSwiftView: View {
    var contentPaddingState: SkieSwiftStateFlow<Foundation_layoutPaddingValues>
    var contentLocationState: SkieSwiftStateFlow<Coordinate>
    var contentSpotsState: SkieSwiftStateFlow<[Spot]>
    
    @Environment(\.colorScheme) var colorScheme
    @State var contentPadding: Foundation_layoutPaddingValues?
    @State var spots: [Spot] = []
    @State private var viewport: Viewport = Viewport.camera(
        center: .init(latitude: 48.2082, longitude: 16.3719), // Default center coordinates
        zoom: 14, // Default zoom level
        bearing: 0,
        pitch: 0
    )

    var body: some View {
        Map(viewport: $viewport) {
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
        .task {
            for await newLocation in contentLocationState {
                withViewportAnimation(.easeOut(duration: 0.5)) {
                    viewport = Viewport.camera(
                        center: .init(latitude: newLocation.longitude, longitude: newLocation.latitude),
                        zoom: 10,
                        bearing: 0,
                        pitch: 0
                    )
                }
            }
        }
        .task {
            for await newSpots in contentSpotsState {
                self.spots = newSpots
            }
        }
    }
}

extension Spot: Identifiable {}
