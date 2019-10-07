import br.com.caelum.stella.ValidationMessage;
import br.com.caelum.stella.validation.CPFValidator;

import java.util.List;
import java.util.Scanner;

public class Principal {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        String cpf;
        System.out.println("Digite um CPF para validação: ");
        cpf = sc.nextLine();
        boolean valido = valida(cpf);
        if (!valido){
            System.out.println("O CPF não é válido");
        }else {
            System.out.println("CPF válido.");
        }
    }
    public static boolean valida(String cpf) {
        CPFValidator cpfValidator = new CPFValidator();
        List<ValidationMessage> erros = cpfValidator.invalidMessagesFor(cpf);
        if(erros.size() > 0) {
            return false;
        }else{
            return true;
        }
    }



}
