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
        center: .init(latitude: 48.2082, longitude: 16.3719),
        zoom: 14,
        bearing: 0,
        pitch: 0
    )

    var body: some View {
        Map(viewport: $viewport) {
            if let location = location {
                CircleAnnotation(
                    centerCoordinate: CLLocationCoordinate2D(
                        latitude: location.longitude,
                        longitude: location.latitude
                    )
                )
                .circleColor("#007BFF")
                .circleOpacity(0.7)
                .circleRadius(15.0)
            }
            
            ForEvery(spots.filter { spot in
                isValidCoordinate(latitude: spot.latitude, longitude: spot.longitude)}
            ){ spot in
                MapViewAnnotation(coordinate: .init(latitude: spot.latitude, longitude: spot.longitude)) {
                    let image = spot.isBooked ? "not-parking-icon" : "parking-icon"
                    Image(image)
                        .resizable()
                        .frame(width: 40, height: 40)
                        .onTapGesture {
                            if !spot.isBooked {
                                _ = onSpotSelected(spot)
                            }
                        }
                }
            }
        }
        .ornamentOptions(
            OrnamentOptions(
                logo: LogoViewOptions(margins: CGPoint(x: CGFloat(8.0), y: CGFloat($contentPadding.wrappedValue?.calculateBottomPadding() ?? 0.0))),
                attributionButton: AttributionButtonOptions(margins: CGPoint(x: CGFloat(8.0), y: CGFloat($contentPadding.wrappedValue?.calculateBottomPadding() ?? 0.0)))
            )
        )
        .mapStyle(MapStyle(uri: StyleURI(rawValue: "mapbox://styles/proj-overdrive/cm2w2fyoo003i01q28u9f1ooa") ?? .light))
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
    
    func isValidCoordinate(latitude: Double, longitude: Double) -> Bool {
        return latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180
    }
}

extension Spot: Identifiable {}
