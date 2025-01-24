package org.example.proyecto_competicion.Controllers;

import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payment") // Define la ruta base para pagos
public class PaymentController {

    @Value("${stripe.keys.secret}")
    private String API_SECRET_KEY;

    // Endpoint para crear el PaymentIntent
    @PostMapping("/create-payment-intent")
    public Map<String, Object> createPaymentIntent(@RequestBody Map<String, Object> request) {
        Stripe.apiKey = API_SECRET_KEY;

        Map<String, Object> response = new HashMap<>();
        try {
            int amount = (int) request.get("amount");

            Map<String, Object> params = new HashMap<>();
            params.put("amount", amount); // Monto en centavos
            params.put("currency", "usd"); // Moneda
            params.put("payment_method_types", List.of("card")); // Solo tarjetas

            PaymentIntent intent = PaymentIntent.create(params);
            response.put("clientSecret", intent.getClientSecret()); // Devuelve el client_secret
        } catch (Exception e) {
            e.printStackTrace();
            response.put("error", "No se pudo crear el PaymentIntent.");
        }

        return response; // Retorna el client_secret o el error
    }
}