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
    contentSpotsState: SkieSwiftStateFlow<[Spot]>,
    onSpotSelected: @escaping (Spot) -> KotlinUnit
) -> MapWithSwiftViewFactory {
    return MapSwiftViewContainer(
        contentPaddingState: contentPaddingState,
        contentLocationState: contentLocationState,
        contentSpotsState: contentSpotsState,
        onSpotSelected: onSpotSelected
    )
}

class MapSwiftViewContainer: MapWithSwiftViewFactory {
    
    var viewController: UIViewController
    
    init(
        contentPaddingState: SkieSwiftStateFlow<Foundation_layoutPaddingValues>,
        contentLocationState: SkieSwiftStateFlow<Coordinate>,
        contentSpotsState: SkieSwiftStateFlow<[Spot]>,
        onSpotSelected: @escaping (Spot) -> KotlinUnit
        
    ) {
            self.viewController = UIHostingController(
                rootView: MapSwiftView(
                    contentPaddingState: contentPaddingState,
                    contentLocationState: contentLocationState,
                    contentSpotsState: contentSpotsState,
                    onSpotSelected: onSpotSelected
                )
            )
    }
}

struct MapSwiftView: View {
    var contentPaddingState: SkieSwiftStateFlow<Foundation_layoutPaddingValues>
    var contentLocationState: SkieSwiftStateFlow<Coordinate>
    var contentSpotsState: SkieSwiftStateFlow<[Spot]>
    var onSpotSelected: (Spot) -> KotlinUnit
    
    @Environment(\.colorScheme) var colorScheme
    @State var contentPadding: Foundation_layoutPaddingValues?
    @State var spots: [Spot] = []
    @State var location: Coordinate?
    @State private var viewport: Viewport = Viewport.camera(
        center: .init(latitude: 48.2082, longitude: 16.3719), // Default center coordinates
        zoom: 14, // Default zoom level
        bearing: 0,
        pitch: 0
    )

    var body: some View {
        Map(viewport: $viewport) {
            if let location = location {
                CircleAnnotation(
                    centerCoordinate: CLLocationCoordinate2D(
                        latitude: location.longitude, // Correct order
                        longitude: location.latitude
                    )
                )
                .circleColor("#007BFF")
                .circleOpacity(0.7)
                .circleRadius(15.0)
            }
            CircleAnnotationGroup(spots){ spot in
                CircleAnnotation(centerCoordinate: CLLocationCoordinate2D(latitude: spot.latitude, longitude: spot.longitude))
                    .circleColor(spot.isBooked ? "#FF5733" : "#28A745")
                    .circleOpacity(0.7)
                    .circleRadius(10.0)
                    .onTapGesture {
                        _ = onSpotSelected(spot)
                    }
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
                self.location = newLocation
                withViewportAnimation(.easeOut(duration: 0.5)) {
                    viewport = Viewport.camera(
                        center: .init(latitude: newLocation.longitude, longitude: newLocation.latitude),
                        zoom: 13,
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
