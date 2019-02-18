import java.util.Scanner;
import java.lang.String;
import java.lang.Math;

/* # Basic Payslip Challenge

        At MYOB we rock at payroll, the most important part of payroll is getting your payslip!

        ## Overview

        We have the following payslip rules:

        * Pay period = per calendar month
        * Gross income = annual salary / 12 months
        * Income tax = based on the tax table provided below
        * Net income = gross income - income tax
        * Super = gross income x super rate
        * All calculation results should be rounded to the whole dollar. If >= 50 cents round up to the next dollar increment, otherwise round down.

        When supplied employee details: first name, last name, annual salary (positive integer) and super rate (0% - 50% inclusive), payment start date, generate pay slip information with name, pay period,
        gross income, income tax, net income and super.

        The following rates for 2017-18 apply from 1 July 2017:

        | Taxable Income     | Tax on this Income                         |
        |--------------------|--------------------------------------------|
        | $0 - $18,200       | Nil                                        |
        | $18,201 - $37,000  | 19c for each $1 over $18,200               |
        | $37,001 - $87,000  | $3,572 plus 32.5c for each $1 over $37,000 |
        | $87,001 - $180,000 | $19,822 plus 37c for each $1 over $87,000  |
        | $180,001 and over  | $54,232 plus 45c for each $1 over $180,000 |

        For example, the payment in March for an employee with an annual salary of $60,050 and a super rate of 9% is:

        * pay period = Month of March (01 March to 31 March)
        * gross income = 60,050 / 12 = 5,004.16666667 (round down) = 5,004
        * income tax = (3,572 + (60,050 - 37,000) x 0.325) / 12 = 921.9375 (round up) = 922
        * net income = 5,004 - 922 = 4,082
        * super = 5,004 x 9% = 450.36 (round down) = 450

        ### Your Task

        Generate a basic payslip application. You should be able to enter a single employee details, the application will generate a basic payslip.

        Everything will be done via the console.

        An example run through of of how this console would be...

        ~~~
        Welcome to the payslip generator!

        Please input your name: John
        Please input your surname: Doe
        Please enter your annual salary: 60050
        Please enter your super rate: 9
        Please enter your payment start date: 1 March
        Please enter your payment end date: 31 March

        Your payslip has been generated:

        Name: John Doe
        Pay Period: 01 March – 31 March
        Gross Income: 5004
        Income Tax: 922
        Net Income:4082
        Super: 450

        Thank you for using MYOB!
        ~~~ */

public class Main {

    public static void main(String[] args) throws InterruptedException {

        // welcome message
        System.out.print("Welcome to the payslip generator!\n");
        Thread.sleep(1000);

        Scanner userInput = new Scanner(System.in);

        // ask user to enter first name
        System.out.print("\nPlease enter your first name: ");
        String firstName = userInput.nextLine();

        // ask user to enter last name
        System.out.print("Please enter your last name: ");
        String lastName = userInput.nextLine();

        // ask user to enter annual salary
        // check annual salary is a positive integer
        int annualSalary = 0;

        try {
            System.out.print("Please enter your annual salary: ");
            annualSalary = Integer.parseInt(userInput.nextLine());
            if (annualSalary <= 0) {
                throw new Exception("Annual salary must be positive, received: " + annualSalary);
            }
        } catch(NumberFormatException e) {
            System.out.print("Annual Salary must be an integer, received: " + annualSalary);
            System.exit(2);
        } catch(Exception e) {
            System.out.print(e.getMessage());
            System.exit(3);
        }

        // ask user to enter super rate
        // check super rate is an integer and within 0 - 50
        int superRate = 0;

        try {
            System.out.print("Please enter your superannuation rate (out of 100): ");
            superRate = Integer.parseInt(userInput.nextLine());
            if (superRate > 100 || superRate < 0) {
                throw new Exception("Super must be between 0 and 100, received: " + superRate);
            }
        } catch(Exception e) {
            System.out.print(e.getMessage());
            System.exit(4);
        }

        // ask user to enter pay start date
        System.out.print("Please enter your payment start date(dd/mm/yyyy): ");
        String startDate = userInput.nextLine();

        // ask user to enter pay end date
        System.out.print("Please enter your payment end date(dd/mm/yyyy): ");
        String endDate = userInput.nextLine();

        // generate payslip
        System.out.print(createPayslip(firstName, lastName, startDate, endDate, annualSalary, superRate));

    }

    // CALCULATION METHODS

        // method to generate gross income (annual salary / 12)
    private static int grossIncomeMethod(int annualSalary){
        return annualSalary / 12;
    }

        // method to generate income tax (round up)
    private static double incomeTaxMethod(int annualSalary){

        double tax;

        if(annualSalary <= 18200 ){
            tax = 0;
        } else if(annualSalary <= 37000){
            tax = (annualSalary - 18000) * 0.19;
        } else if(annualSalary <= 87000){
            tax = 3572 + ((annualSalary - 37000) * 0.325);
        } else if(annualSalary <= 180000){
            tax = 19822 + ((annualSalary - 87000) * 0.37);
        } else {
            tax = 54232 + ((annualSalary - 180000) * 0.45);
        }

        return tax / 12;
    }

        // method to generate net income (gross income - income tax)
    private static double netIncomeMethod(int grossIncome, double incomeTax){
        return grossIncome - incomeTax;
    }

        // method to generate super (gross income x super rate -> round down)
    private static double superContributionMethod(int grossIncome, int superRate){
        return grossIncome * (superRate / 100.0f);
    }

    // PAYSLIP GENERATOR
    private static String createPayslip(String firstName, String lastName, String startDate, String endDate, int annualSalary, int superRate){

        // print gross income
        int grossIncome = grossIncomeMethod(annualSalary);

        // print income tax
        double incomeTax = Math.round(incomeTaxMethod(annualSalary));

        // print net income
        double netIncome = netIncomeMethod(grossIncome, incomeTax);

        // print super contribution
        double superContribution = Math.round(superContributionMethod(grossIncome, superRate));

        String generatePayslip = "\nYour payslip is being generated...\n"
                                + "\nName: " + firstName + " " + lastName + "\n"
                                + "Pay Period: " + startDate + " - " + endDate + "\n"
                                + "Gross Income: " + grossIncome + "\n"
                                + "Income Tax: " + incomeTax + "\n"
                                + "Net Income: " + netIncome + "\n"
                                + "Super: " + superContribution + "\n"
                                + "\nThank you for using MYOB!";

        return generatePayslip;
    }

}
