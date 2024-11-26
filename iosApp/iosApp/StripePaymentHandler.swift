//
//  StripePaymentHandler.swift
//  iosApp
//
//  Created by Ethan Wright on 2024-11-25.
//  Copyright © 2024 orgName. All rights reserved.
//

import Foundation
import StripePaymentSheet
import SwiftUI

class StripePaymentHandler: ObservableObject {
    let backendCheckoutUrl = URL(string: "http://localhost:8080/payment-sheet")!
    @Published var paymentSheet: PaymentSheet?
    @Published var paymentResult: PaymentSheetResult?

    func preparePaymentSheet() {
      var request = URLRequest(url: backendCheckoutUrl)
      request.httpMethod = "POST"
        
      let task = URLSession.shared.dataTask(with: request, completionHandler: { [weak self] (data, response, error) in
        guard let data = data,
              let json = try? JSONSerialization.jsonObject(with: data, options: []) as? [String : Any],
              let customerId = json["customer"] as? String,
              let customerEphemeralKeySecret = json["ephemeralKey"] as? String,
              let paymentIntentClientSecret = json["paymentIntent"] as? String,
              let publishableKey = json["publishableKey"] as? String,
              let self = self else {
            // Handle error
          return
        }

        STPAPIClient.shared.publishableKey = publishableKey
        var configuration = PaymentSheet.Configuration()
        configuration.merchantDisplayName = "Example, Inc."
        configuration.customer = .init(id: customerId, ephemeralKeySecret: customerEphemeralKeySecret)
        configuration.allowsDelayedPaymentMethods = true

        DispatchQueue.main.async {
          self.paymentSheet = PaymentSheet(paymentIntentClientSecret: paymentIntentClientSecret, configuration: configuration)
        }
      })
        
      task.resume()
    }

    func onPaymentCompletion(result: PaymentSheetResult) {
      self.paymentResult = result
    }
}
