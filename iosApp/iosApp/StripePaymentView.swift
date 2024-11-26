//
//  StripePaymentView.swift
//  iosApp
//
//  Created by Ethan Wright on 2024-11-25.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import SwiftUI
import ComposeApp
import StripePaymentSheet

func stripePaymentViewFactory(
    contentAmount: KotlinLong,
    contentOnComplete: @escaping () -> KotlinUnit
) -> StripePaymentViewFactory {
    return StripePaymentViewContainer(
        contentAmount: contentAmount,
        contentOnComplete: contentOnComplete
    )
}

class StripePaymentViewContainer: StripePaymentViewFactory {
    var viewController: UIViewController
    var stripe: StripePaymentHandler
    
    init(
        contentAmount: KotlinLong,
        contentOnComplete: @escaping () -> KotlinUnit
    ) {
        let stripe = StripePaymentHandler()
        
        self.stripe = stripe
        self.viewController = UIHostingController(
            rootView: StripePaymentView(
                stripe: stripe,
                amount: contentAmount,
                onComplete: contentOnComplete
            )
        )
    }
}

struct StripePaymentView: View {
    @ObservedObject var stripe: StripePaymentHandler
    
    var amount: KotlinLong
    var onComplete: () -> KotlinUnit
    
    var body: some View {
        VStack {
            if let paymentSheet = stripe.paymentSheet {
                    PaymentSheet.PaymentButton(
                        paymentSheet: paymentSheet,
                        onCompletion: { result in
                            switch result {
                            case .completed:
                                print("Payment successful!")
                                onComplete()
                            case .failed(let error):
                                print("Payment failed: \(error.localizedDescription)")
                            case .canceled:
                                print("Payment canceled.")
                            }
                        }
                    ) {
                        payButton
                    }
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                    .ignoresSafeArea()
            } else {
                ProgressView("Loading Payment Sheet...")
                    .frame(maxWidth: .infinity, maxHeight: .infinity)
                    .background(Color.white)
            }
            if let result = stripe.paymentResult {
                switch result {
                case .completed:
                  Text("Payment complete")
                case .failed(let error):
                  Text("Payment failed: \(error.localizedDescription)")
                case .canceled:
                  Text("Payment canceled.")
                }
            }
        }
        .frame(maxWidth: .infinity, maxHeight: .infinity)
        .ignoresSafeArea()
        .background(Color.clear)
        .onAppear{
            stripe.preparePaymentSheet()
        }
    }
    
    private var payButton: some View {
        Text("Pay Now")
            .frame(maxWidth: .infinity, maxHeight: .infinity)
            .background(Color(red: 0xF9 / 255.0, green: 0x78 / 255.0, blue: 0x4B / 255.0))
            .foregroundColor(.white)
            .cornerRadius(12)
            .ignoresSafeArea()
    }
}
