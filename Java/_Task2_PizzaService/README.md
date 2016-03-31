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

��������� �������� ������:
- �������� ����������� ��������� � ����� ����������� ����� ����: �� 1 �� 10 ���� (� �����)
- ������� ��������� ������
- ������ � 30% �� ����� ������� ����� � ������, ���� ���������� ���� ������ 4-�
- � ������ ������ ���� ������ (NEW, IN_PROGRSS, CANCELED, DONE, � - � ������������� �� ��������� �������� ����� ��������� )
- � ������ ������ ���� Customer, � �������� � ���� ������� ������ ���� Address ��������
- ��������� ������ ������ ���������� �� ������������� ����� ������������ ���� ��� ����
- �� ��������� ������ ���������� 10% �� ����� ����� �� ������������� �����, �� �� ������ ��� 30% ��������� ������
 
��� ������ ���� ������ ���������� �������.