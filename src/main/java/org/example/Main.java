package org.example;
import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public enum OperatorType{ //enum과 람다식 결합
        ADD('+', (x,y) -> x + y),
        SUB('-', (x,y) -> x - y),
        MUL('*', (x,y) -> x * y),
        DIV('/', (x,y) -> x / y),
        MOD('%', (x,y) -> x % y);

        private final char symbol;
        private final Cal cal;

        OperatorType(char symbol, Cal cal) { //생성자
            this.symbol = symbol;
            this.cal = cal;
        }

        public char getSymbol(){ //연산자 받아오기
            return symbol;
        }
        public double cal(double x, double y){ //계산식(연산자는 람다로)
            return cal.cal(x,y);
        }

        public static OperatorType findSymbol(char symbol){
            for(OperatorType op : values()){
                if (op.getSymbol() == symbol)
                    return op;
            }
            return null;
        }

    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        double result_cal = 0;
        double firstNum;
        while(true) {  //원할때까지 계산 반복

            if(result_cal != 0){
                firstNum = result_cal;
                result_cal = 0;
            }
            else{
                System.out.print("Type first Number ");
                firstNum = sc.nextDouble();
            }


            System.out.print("Type calculator symbols ");
            char symbols = sc.next().charAt(0);

            System.out.print("Type second Number ");
            double secondNum = sc.nextDouble();

            OperatorType op = OperatorType.findSymbol(symbols); //연산자 타입 판단하기

            if (op == null) { // 연산자가 아닐경우 오류 처리
                System.out.println("plz type symbols (+, -, %, *, /)");
                continue;
            }

            if ((op == OperatorType.DIV || op == OperatorType.MOD) && secondNum == 0) { // 0으로 나눌경우 오류처리
                System.out.println("Can't div with 0");
                continue;
            }

            CalResult<Double> result = new CalResult<Double>(); //결과값 제네릭으로 선언

            result.setResult(op.cal(firstNum, secondNum));

//            double value = result.getResult();

            System.out.println(" ");
            System.out.println(" ");

            System.out.println("The result is " + result.getResult());
            ResultCollection.list.add(result.getResult());




            System.out.println("Type exit to exit");
            System.out.println("Type 1 to display result");
            System.out.println("Type 2 to remove first result");
            System.out.println("Type 3 to calculate using previous result");

            String additional_cal = sc.next();
            switch (additional_cal){
                case("1"):{

                    System.out.println("Only results greater than the typed number will be displayed.");
                    double d = sc.nextDouble();
                    ResultCollection.list.stream()
                            .filter(ResultCollection -> ResultCollection > d)
                            .forEach(System.out::println);
                    System.out.println(" ");
                    break;
                }
                case("2"):{
                    System.out.println("removed result: " + ResultCollection.list.get(0));
                    ResultCollection.list.remove(0);
                    System.out.println("current result list: " + ResultCollection.list);
                    break;
                }
                case("3"):{
                    result_cal = ResultCollection.list.getLast();
                    System.out.println("previous result: " + ResultCollection.list.getLast());
                    break;
                }
            }
            if(additional_cal.equals("exit")) break;
        }
    }
}