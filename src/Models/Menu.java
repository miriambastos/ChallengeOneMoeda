package Models;

// ═ ║ ╒ ╓ ╔ ╕ ╖ ╗ ╘ ╙ ╚ ╛ ╜ ╝ ╞ ╟
// ╠ ╡ ╢ ╣ ╤ ╥ ╦ ╧ ╨ ╩ ╪ ╫ ╬

import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Menu {
    Scanner sc = new Scanner(System.in);
    private boolean seguir = true;
    private String moedaBase;
    private String moedaAlvo;
    private SimpleDateFormat formatDate = new SimpleDateFormat("HH:mm:ss - dd/MM/yyyy");

    ArrayList<ConversionData> conversoes = new ArrayList<>();

    private String principal = """
            ╔═══════════════════════════════════════════════════════╗
            ║                 $ $ $ $ $ $ $ $ $ $                   ║
            ║=-=-=-=-=-=-=-=- Conversor de Moedas -=-=-=-=-=-=-=-=-=║
            ║                 $ $ $ $ $ $ $ $ $ $                   ║
            ╠═══════════════════════════════════════════════════════╣
            ║ 1) Dólar =>> Peso argentino                           ║
            ║ 2) Peso Argentino =>> Dólar                           ║
            ║ 3) Dólar =>> Real brasileiro                          ║
            ║ 4) Real brasileiro =>> Dólar                          ║
            ║ 5) Dólar =>> Peso Colombiano                          ║
            ║ 6) Peso Colombiano =>> Dólar                          ║
            ║ 7) Conversão customizada                              ║
            ║ 8) Visualizar e salvar histórico                      ║
            ║ 9) Sair                                               ║
            ╚═══════════════════════════════════════════════════════╝
            """;

    private String custom = """
            ╔═══════════════════════════════════════════════════════╗
            ║                 $ $ $ $ $ $ $ $ $ $                   ║
            ║=-=-=-=-=-=-=-=- Conversor de Moedas -=-=-=-=-=-=-=-=-=║
            ║                 $ $ $ $ $ $ $ $ $ $                   ║
            ╠═══════════════════════════════════════════════════════╣
            ║ 1) Real                                               ║
            ║ 2) Peso Argentino                                     ║
            ║ 3) Peso Uruguaio                                      ║
            ║ 4) Peso Colombiano                                    ║
            ║ 5) Peso Mexicano                                      ║
            ║ 6) Sol Peruano                                        ║
            ║ 7) Dólar                                              ║
            ║ 8) Euro                                               ║
            ║ 9) Voltar                                             ║
            ║ 10) Sair                                              ║
            ╚═══════════════════════════════════════════════════════╝
            """;

//    private String askChoice = """
//            ╔═══════════════════════════════════════════════════════╗
//            ║ Digite a opção desejada:                              ║
//            ╚═══════════════════════════════════════════════════════╝
//            """;


    public void firstMenu() throws IOException, InterruptedException {
        Conversor conversor = new Conversor();
        this.printMenuPrincipal();
        System.out.println("Digite a opção desejada");
        String userChoice = sc.nextLine();
        this.opcaoMenuPrincipal(userChoice);
        if (userChoice.equals("9")) {
            System.out.println("Saindo do Conversor de Moedas. Até logo!");
            this.setSeguir(false);
        } else if (userChoice.equals("7")) {
            this.printMenuCustom();
            System.out.println("Digite a opção da moeda que deseja converter");
            String userCustom = sc.nextLine();
            String moedaBase = this.opcaoMenuCustom(userCustom);
            firstCustomMenu(userCustom, moedaBase);
        } else if (userChoice.equals("8")) {
            SimpleDateFormat diaHora = new SimpleDateFormat("dd-MM-yyyy-HH-mm");
            Timestamp data = new Timestamp(System.currentTimeMillis());
            String momento = diaHora.format(data);
            try (FileWriter writer = new FileWriter("hist_" + momento + ".txt")) {
                for (ConversionData conversionData : conversoes) {
                    String hist =
                            (" | Moeda original: " + conversionData.getMoedaBase() +
                            " | Moeda a ser convertida: " + conversionData.getMoedaAlvo() + "\n" +
                            " | Valor original: " + conversionData.getValor() + "\n" +
                            " | Valor convertido: " + (conversionData.getValor() * conversionData.getTaxa()) +  "\n" +
                            " | Taxa de conversão: " + conversionData.getTaxa() + "\n" +
                            " | Conversão realizada em: " + conversionData.getHora() + "\n" + "\n");
                    System.out.println(hist);
                    writer.write(hist);
                }
                System.out.println("Histórico salvo no arquivo: " + "hist_" + momento + ".txt\n");
            } catch (IOException e) {
                System.out.println("Erro ao escrever o arquivo.");
            }
        } else if (List.of("1", "2", "3", "4", "5", "6").contains(userChoice)) {
            System.out.println("Digite o valor a ser convertido:");
            String valorString = sc.nextLine();
            try {
                Timestamp data = new Timestamp(System.currentTimeMillis());
                double valor = Double.parseDouble(valorString);
                double taxa = conversor.pegaTaxaConversao(this.getMoedaBase(), this.getMoedaAlvo());
                String hora = formatDate.format(data);
                conversoes.add(new ConversionData(this.getMoedaBase(), this.getMoedaAlvo(), taxa, valor, hora));
                System.out.println("O valor de " + this.getMoedaBase() + " " + valor + " em " + this.getMoedaAlvo() +
                        " é de: " + conversor.converteValor(valor, taxa) + ". Horário de conversão: " + hora);
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Valor inválido, tente novamente");
            }
        } else {
            System.out.println("Opção inválida, tente novamente.");
        }
    }

    private void firstCustomMenu(String userCustom, String moedaBase) throws IOException, InterruptedException {
        if (userCustom.equals("10")) {
            System.out.println("Saindo do Conversor de Moedas. Até logo!");
            this.setSeguir(false);
        } else if (userCustom.equals("9")) {
            System.out.println("Retornando ao menu principal");
        } else if (List.of("1", "2", "3", "4", "5", "6", "7", "8").contains(userCustom)) {
            System.out.println("Digite a opção da moeda para qual converter");
            userCustom = sc.nextLine();
            String moedaAlvo = this.opcaoMenuCustom(userCustom);
            secondCustomMenu(userCustom, moedaBase, moedaAlvo);
        } else {
            System.out.println("Opção inválida, tente novamente.");
        }
    }

    private void secondCustomMenu(String userCustom, String moedaBase, String moedaAlvo) throws IOException, InterruptedException {
        Conversor conversor = new Conversor();
        if (userCustom.equals("10")) {
            System.out.println("Saindo do Conversor de Moedas. Até logo!");
            this.setSeguir(false);
        } else if (userCustom.equals("9")) {
            System.out.println("Retornando ao menu principal \n");
        } else if (List.of("1", "2", "3", "4", "5", "6", "7", "8").contains(userCustom)) {
            System.out.println("Digite o valor a ser convertido:");
            String valorString = sc.nextLine();
            try {
                Timestamp data = new Timestamp(System.currentTimeMillis());
                double valor = Double.parseDouble(valorString);
                double taxa = conversor.pegaTaxaConversao(moedaBase, moedaAlvo);
                String hora = formatDate.format(data);
                conversoes.add(new ConversionData(moedaBase, moedaAlvo, taxa, valor, hora));
                System.out.println("O valor de " + moedaBase + " " + valor + " em " + moedaAlvo +
                        " é de: " + conversor.converteValor(valor, taxa) + ". Horário de conversão: " + formatDate.format(data));
            } catch (NumberFormatException | InputMismatchException e) {
                System.out.println("Valor inválido, tente novamente");
            }
        } else {
            System.out.println("Opção inválida, tente novamente.");
        }
    }

    public void opcaoMenuPrincipal(String opcao) {
        String base = "";
        String alvo = "";
        switch (opcao) {
            case "1":
                base = "USD";
                alvo = "ARS";
                break;
            case "2":
                base = "ARS";
                alvo = "USD";
                break;
            case "3":
                base = "USD";
                alvo = "BRL";
                break;
            case "4":
                base = "BRL";
                alvo = "USD";
                break;
            case "5":
                base = "USD";
                alvo = "COP";
                break;
            case "6":
                base = "COP";
                alvo = "USD";
                break;
            case "7":
                base = "custom";
                break;
            case "8":
                base = "sair";
                break;
        }
        this.setMoedaAlvo(alvo);
        this.setMoedaBase(base);
    }

    public String opcaoMenuCustom(String opcao) {
        String choice = "";
        switch (opcao) {
            case "1":
                choice = "BRL";
                break;
            case "2":
                choice = "ARS";
                break;
            case "3":
                choice = "UYU";
                break;
            case "4":
                choice = "COP";
                break;
            case "5":
                choice = "MXN";
                break;
            case "6":
                choice = "PEN";
                break;
            case "7":
                choice = "USD";
                break;
            case "8":
                choice = "EUR";
                break;
            case "9":
                choice = "back";
                break;
            case "10":
                choice = "exit";
                this.seguir = false;
        }
        return choice;
    }

    public void printMenuPrincipal() {
        System.out.println(this.getPrincipal());
    }

    public void printMenuCustom() {
        System.out.println(this.getCustom());
    }

    public String getPrincipal() {
        return principal;
    }

    public String getCustom() {
        return custom;
    }

    public boolean isSeguir() {
        return seguir;
    }

    public void setSeguir(boolean seguir) {
        this.seguir = seguir;
    }

    public String getMoedaBase() {
        return moedaBase;
    }

    public void setMoedaBase(String moedaBase) {
        this.moedaBase = moedaBase;
    }

    public String getMoedaAlvo() {
        return moedaAlvo;
    }

    public void setMoedaAlvo(String moedaAlvo) {
        this.moedaAlvo = moedaAlvo;
    }

}