# MyWorks
Made by Ivan Myrotiuk

public class SimpleOrderService {
 
        public Order placeNewOrder(Customer customer, Integer ... pizzasID) {
                List<Pizza> pizzas = new ArrayList<>();
 
                for(Integer id : pizzasID){
                        pizzas.add(getPizzaByID(id));  // get Pizza from predifined in-memory list
                }
                Order newOrder = new Order(customer, pizzas);
 
                saveOrder(newOrder);  // set Order Id and save Order to in-memory list
                return newOrder;
        }
 
}
public class PizzaApp {
 
        public static void main(String[] args) {
                Customer customer = null;
                Order order;
 
                SimpleOrderService orderService = new SimpleOrderService();
                order = orderService.placeNewOrder(customer, 1, 2, 3);
 
                System.out.println(order);
        }
 
}

–асширить доменную модель:
- включить возможность добавл€ть в заказ определЄнное число пицц: от 1 до 10 пицц (в сумме)
- подсчет стоимости заказа
- скидка в 30% на самую дорогую пиццу в заказе, если количество пицц больше 4-х
- у заказа должен быть статус (NEW, IN_PROGRSS, CANCELED, DONE, Е - с ограничени€ми на возможные переходы между статусами )
- у заказа должен быть Customer, у которого в свою очередь должен быть Address доставки
- стоимость заказа должна заноситьс€ на накопительную карту пользовател€ если она есть
- из стоимости заказа вычитаетс€ 10% от общей суммы на накопительной карте, но не больше чем 30% стоимости заказа
 
 од должен быть покрыт модульными тестами.