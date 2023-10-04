package me.toddydev.core.services.mercadopago;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.toddydev.core.model.order.Order;
import me.toddydev.core.model.order.status.OrderStatus;
import okhttp3.*;
import org.bukkit.Bukkit;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import static me.toddydev.core.model.order.status.OrderStatus.WAITING;

public class MPService {

    public Order create(Order order) {
        JsonObject object = new JsonObject();

        object.addProperty("description", "Projetado por BRPayments.");

        JsonObject payer = new JsonObject();

        payer.addProperty("entity_type", "individual");
        payer.addProperty("type", "customer");
        payer.addProperty("email", order.getReferenceId() + "@toddydev.me");

        object.add("payer", payer);

        DecimalFormat format = new DecimalFormat("#.##");

        object.addProperty("external_reference", order.getReferenceId());
        object.addProperty("payment_method_id", "pix");
        object.addProperty("transaction_amount", new BigDecimal(format.format(order.getCost()).replace(",", ".")).doubleValue());

        try {
            OkHttpClient client = new OkHttpClient();
            MediaType type = MediaType.parse("application/json; charset=utf-8");

            RequestBody requestBody = RequestBody.create(type, object.toString());
            Request request = new Request.Builder()
                    .url("https://api.mercadopago.com/v1/payments")
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Accept", "application/json")
                    .addHeader("Authorization", "Bearer " + order.getGateway().getToken())
                    .post(requestBody)
                    .build();

            Response rs = client.newCall(request).execute();

            JsonObject o = (new JsonParser()).parse(rs.body().string()).getAsJsonObject();
            order.setCode(o.getAsJsonObject("point_of_interaction").getAsJsonObject("transaction_data").get("qr_code").getAsString());
            order.setTicketLink(o.getAsJsonObject("point_of_interaction").getAsJsonObject("transaction_data").get("ticket_url").getAsString());
            order.setPaymentId(o.get("id").getAsString());
            return order;
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage("[BRPayments] Ocorreu um erro ao tentar criar um pagamento com o Mercado Pago: " + e.getLocalizedMessage());
            return null;
        }
    }

    public OrderStatus check(Order order) {
        try {
            OkHttpClient okHttpClient = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("https://api.mercadopago.com/v1/payments/" + order.getPaymentId())
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + order.getGateway().getToken())
                    .get()
                    .build();

            Response rs = okHttpClient.newCall(request).execute();
            JsonObject object = new JsonParser().parse(rs.body().string()).getAsJsonObject();
            JsonElement element = object.get("status");

            String status = element.getAsString();
            OrderStatus orderStatus = (status.equalsIgnoreCase("approved") ? OrderStatus.PAID : status.equalsIgnoreCase("cancelled") ? OrderStatus.CANCELLED : status.equalsIgnoreCase("refunded") ? OrderStatus.REFUNDED : status.equalsIgnoreCase("rejected") ? OrderStatus.CANCELLED : WAITING);
            return orderStatus;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return WAITING;
    }
}
