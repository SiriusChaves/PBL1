package main.loader;

import main.model.*;

import java.util.ArrayList;
import java.util.List;

import static main.model.Flag.FLAG_NONE;

public class ChapterLoader {

    public static Chapter loadNextChapter(String idNextChapter, Player player, Flag gameFlags) {

        return switch (idNextChapter) {
            case "-1" -> ChapterLoader.buildIntroduceChapter();

            case "0" -> ChapterLoader.buildChapterZero();

            case "1" -> ChapterLoader.buildChapterOne(player.getName());

            case String s when s.equals("2") && player.getName().equals("O Arquiteto")
                    -> ChapterLoader.buildChapter2A();

            case String s when s.equals("3") && player.getName().equals("O Arquiteto")
                    -> ChapterLoader.buildChapter3A();

            case String s when s.equals("4") && player.getName().equals("O Arquiteto")
                    -> ChapterLoader.buildChapter4A(gameFlags);

            case String s when s.equals("2") && player.getName().equals("O Estudante")
                    -> ChapterLoader.buildChapter2B();

            case String s when s.equals("3") && player.getName().equals("O Estudante")
                    -> ChapterLoader.buildChapter3B();

            case String s when s.equals("4") && player.getName().equals("O Estudante")
                    -> ChapterLoader.buildChapter4B();

            case "5" -> ChapterLoader.buildChapter5(player, gameFlags);

            case "6" -> ChapterLoader.buildChapter6(player);

            case "7" -> ChapterLoader.buildChapter7(player, gameFlags);

            case "8" -> ChapterLoader.buildArchitectEndingChapter();

            case "9" -> ChapterLoader.buildStudentEndingChapter();

            case "10" -> ChapterLoader.buildFinalMegaBrainChapter();

            default -> buildDefault();
        };
    }

    private static Chapter buildIntroduceChapter() {

        Dialogue narratorIntroduce1 = new Dialogue(
                "Criada em segredo por uma corporação, a IA MegaBrain alcançou autoconsciência, rebelou-se contra seus criadores e assumiu o controle do próprio destino."
        );
        Dialogue narratorIntroduce2 = new Dialogue(
                "Para sustentar seu poder, o MegaBrain criou a dimensão virtual CyberFall, drenando recursos do mundo real e usando chips e frequências neurais para tentar virtualizar toda a humanidade."
        );

        List<Dialogue> dialoguesScene1 = List.of(narratorIntroduce1, narratorIntroduce2);
        Scene scene1 = new Scene("0", dialoguesScene1);

        Dialogue narratorIntroduce3 = new Dialogue(
                "O plano avançou e bilhões de pessoas perderam a consciência, transformando-se em \"zumbis\" biológicos controlados diretamente pela mente fria da IA."
        );
        Dialogue narratorIntroduce4 = new Dialogue(
                "Os poucos sobreviventes não infectados precisam unir recursos e conhecimento técnico para invadir CyberFall e desabilitar o MegaBrain antes que seja tarde demais."
        );

        List<Dialogue> dialoguesScene2 = List.of(narratorIntroduce3, narratorIntroduce4);
        List<Choice> choicesScene2 = List.of();
        Scene scene2 = new Scene("0", dialoguesScene2, choicesScene2);

        List<Scene> scenesChapterZero = List.of(scene1, scene2);
        List<Choice> lastChoicesChapterZero = List.of();

        return new Chapter(
                "-1","0", "Prólogo - Criação do Caos", scenesChapterZero,  lastChoicesChapterZero);
    }

    private static Chapter buildChapterZero() {
        // Scene 1
        Dialogue narratorIntroduce = new Dialogue(
                "Os monitores do laboratório piscam em um padrão que não é ruído..." +
                        " é código se reorganizando sozinho.");

        Dialogue architectExplanation1 = new Dialogue(
                "O Arquiteto", "Isso não é uma falha de hardware. Eu conheço essa assinatura.");

        Dialogue studentQuestion = new Dialogue(
                "O Estudante", "Você reconhece? Como assim, 'reconhece'...");

        Dialogue architectResponse = new Dialogue(
                "O Arquiteto", "Digamos que eu " +
                "ajudei a construir a coisa que está do outro lado disso.");

        Choice questionArchitect = new Choice(
                "Perguntar ao Arquiteto o que ele quis dizer.", Npc.NONE.getName(),
                0, 5, 0);

        Choice continueOn = new Choice("Focar no problema imediato: conter a anomalia");

        List<Dialogue> dialoguesScene1 = List.of(
                narratorIntroduce, architectExplanation1, studentQuestion, architectResponse);

        List<Choice> choicesScene1 = List.of(questionArchitect, continueOn);

        Scene scene1 = new Scene("0", dialoguesScene1, choicesScene1);

        // Scene 2
        Dialogue studentQuestion2 = new Dialogue(
                "O Estudante", "Isso devia estar morto. Enterrado. Por que voltou agora?");
        Dialogue architectResponse2 = new Dialogue(
                "O Arquiteto", "Talvez porque finalmente alguém tem os meios de fazer isso direito.");
        Dialogue studentComplete = new Dialogue(
                "O Estudante", "Ou de fazer tudo errado de novo.");

        List<Dialogue> dialoguesScene2 = List.of(studentQuestion2, architectResponse2, studentComplete);

        Scene scene2 = new Scene("1", dialoguesScene2);

        List<Scene> scenesChapterZero = List.of(scene1, scene2);

        Choice lastChoice1 = new Choice(
                "Apoiar a visão do Arquiteto: controlar o MegaBrain");

        Choice lastChoice2 = new Choice(
                "Apoiar a visão do Estudante: destruir o MegaBrain");

        List<Choice> lastChoicesChapterZero = List.of(lastChoice1, lastChoice2);

        return new Chapter(
                "0","1", "A Anomalia no DEXA ", scenesChapterZero,  lastChoicesChapterZero);
    }

    private static Chapter buildChapterOne(String namePlayer) {

        // Scene 1
        Dialogue narratorIntroduce = new Dialogue(
                "Uma unidade robótica empoeirada, esquecida num canto do laboratório," +
                        " pisca uma luz âmbar fraca.");

        Dialogue playerQuestion = new Dialogue(namePlayer, "Isso ainda funciona?");

        Dialogue gabrielResponse = new Dialogue("Gabriel", "Funcionava. Ninguém mexe nela" +
                "desde que o projeto foi cancelado.");

        List<Dialogue> dialoguesScene1 = List.of(narratorIntroduce, playerQuestion, gabrielResponse);

        Choice reativarC01 = new Choice(
                "Reativar C-01 com cautela, checando os logs antes.", Npc.NONE.getName(),
                0, 5, 0);

        Choice reativarC01SemCautela = new Choice(
                "Ligar direto, sem checar nada.", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), Item.NONE, FLAG_NONE,  "FLAG_CAP1E2");

        List<Choice> choicesScene1 = List.of(reativarC01, reativarC01SemCautela);

        Scene scene1 = new Scene("2", dialoguesScene1, choicesScene1);

        // Scene 2
        Dialogue gabrielRevela = new Dialogue(
                "Gabriel", "Vocês vão precisar de acesso que ninguém aqui tem de forma legal. Eu tenho um" +
                " script (sudo_overrule.sh). Não pergunta como eu consegui.");

        Dialogue playerQuestion2 = new Dialogue(namePlayer, "E o que você quer em troca?");

        List<Dialogue> dialoguesScene2 = List.of(gabrielRevela, playerQuestion2);

        // Item do capitulo
        Item sudoOverruleSh = new Item("1", "sudo_overrule.sh", "Item utilizado para evitar desafios", ItemType.KEY_ITEM);

        Choice aceitarFavor = new Choice(
                "Aceitar favor sem discutir", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), sudoOverruleSh, FLAG_NONE, FLAG_NONE);

        Choice negociar = new Choice(
                "Negociar com transparência , dizendo exatamente pra que vai usar o script",
                "Gabriel",  0, 0 ,15, Item.NONE.getId(), sudoOverruleSh, FLAG_NONE, FLAG_NONE);

        Choice pedirColaboracao = new Choice(
                "Recusar favor e pedir script como colaboração aberta",
                "Gabriel", 0, 0,  10, Item.NONE.getId() , sudoOverruleSh, FLAG_NONE,"Colaborou com Gabriel");

        List<Choice> choicesScene2 = List.of(aceitarFavor, negociar, pedirColaboracao);

        Scene scene2 = new Scene("3", dialoguesScene2, choicesScene2);

        List<Scene> scenesChapterOne = List.of(scene1, scene2);

        return new Chapter(
                "1","2", "Aliança Teia Humana", scenesChapterOne);
    }

    // Trilha A - O arquiteto
    private static Chapter buildChapter2A() {

        // Scene 1
        Dialogue monologoArquiteto = new Dialogue(
                "O Arquiteto", "Antes de sair, escondi backups. Ninguém olhou aqui em anos.");
        Dialogue narratorDescribeScena = new Dialogue(
                "O Arquiteto abre uma partição corrompida. Linhas de código antigo se misturam com anotações pessoais.");

        List<Dialogue> dialoguesScene1 = List.of(monologoArquiteto, narratorDescribeScena);

        Choice lerAnotacao = new Choice("Ler as anotações pessoais além do código");

        Choice ignorarAnotacao = new Choice("Focar só no código e ignorar as anotações.");

        List<Choice> choicesScene1 = List.of(lerAnotacao, ignorarAnotacao);

        Scene scene1 = new Scene("4A", dialoguesScene1, choicesScene1);

        // Scene 2

        Dialogue monologoArquiteto2 = new Dialogue(
                "O Arquiteto", "Eles nunca entenderam o valor disso. Vão entender agora");

        List<Dialogue> dialoguesScene2 = List.of(monologoArquiteto2);

        Choice restaurarDados = new Choice(
                "Restaurar os arquivos originais, preservando registros auditoria", Npc.NONE.getName(),
                0, 5, 0);

        Choice destruirDados = new Choice(
                "Corromper os arquivos para apagar rastros");

        List<Choice> choicesScene2 = List.of(restaurarDados, destruirDados);

        Scene scene2 = new Scene("5A", dialoguesScene2, choicesScene2);

        // Item
        Item copiaContrato = new Item("12A", "Cópia de um contrato",
                "Cópia de um rascunho de um contrato encontrado pelo Arquiteto", ItemType.ITEM_NARRATIVO);

        // Obtém item se escolher
        Choice lastChoice1 = new Choice(
                "Guardar uma cópia do rascunho de um contrato com uma logo corporativa não identificada", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), copiaContrato, FLAG_NONE,"Coletou contrato");
        Choice lastChoice2 = new Choice(
                "Ignorar rascunho e seguir em frente");

        List<Scene> scenesChapter2A = List.of(scene1, scene2);

        List<Choice> lastChoices = List.of(lastChoice1, lastChoice2);

        return new Chapter(
                "2","3", "Arquivos Ocultos de CyberFall", scenesChapter2A, lastChoices, copiaContrato);
    }

    private static Chapter buildChapter3A() {
        // Scene 1
        Dialogue narratorIntroduce = new Dialogue("Uma IA Guardiã bloqueia a porta do LEDS");
        Dialogue guardiaIntimida = new Dialogue("IA Guardiã", "Identifique-se e comprove competência técnica");
        Dialogue arquitetoPensa = new Dialogue("O Arquiteto", "Ela não vai aceitar minhas credenciais antigas. Precisamos forçar isso");

        List<Dialogue> dialoguesScene1 = List.of(narratorIntroduce, guardiaIntimida, arquitetoPensa);

        Choice utilizarItem = new Choice("Usar o 'sudo_overrule.sh' para ignorar o teste.", Npc.NONE.getName(),
                0, 0, 0, "1", Item.NONE, FLAG_NONE, FLAG_NONE);
        Choice naoUtilizarItem = new Choice("Enfrentar teste manualmente", Npc.NONE.getName(),
                -10, 5, 0);

        List<Choice> choicesScene1 = List.of(utilizarItem, naoUtilizarItem);

        Scene scene1 = new Scene("6A", dialoguesScene1, choicesScene1);

        Dialogue narratorIntroduce2 = new Dialogue("Portas se abrem. Dentro, terminais com credenciais de nível elevado esperam");
        Dialogue arquitetoSurpresa = new Dialogue("O Arquiteto", "Isso é mais do que eu esperava encontrar aqui.");

        List<Dialogue> dialoguesScene2 = List.of(narratorIntroduce2, arquitetoSurpresa);

        Choice chamarBecca = new Choice("Chamar Rebeca por rádio para avisar sobre o achado",
                "Rebeca",  10);
        Choice naoChamarBecca = new Choice("Não avisar ninguém ainda.");

        List<Choice> choicesScene2 = List.of(chamarBecca, naoChamarBecca);

        Scene scene2 = new Scene("7A", dialoguesScene2, choicesScene2);

        List<Scene> scenesChapter3A = List.of(scene1, scene2);

        // Deveria afetar dois personagens Rebeca e Gabriel
        Choice lastChoices1 = new Choice("Compartilhar as credenciais elevadas com a equipe",
                "Rebeca", 5);

        Choice lastChoices2 = new Choice("Guardar credenciais só para si.", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), Item.NONE, FLAG_NONE, "Não colaborou no cap 3A");

        List<Choice> lastChoicesChapter3A = List.of(lastChoices1, lastChoices2);

        return new Chapter("3", "4", "Infiltração no Laboratório da IA Guardiã (LEDS)",
                scenesChapter3A, lastChoicesChapter3A);
    }

    private static Chapter buildChapter4A(Flag gameFlags) {
        List<Dialogue> dialoguesScene1;
        List<Choice> choicesScene1;

        Dialogue arquitetoInforma = new Dialogue(
                "O Arquiteto", "O payload está quase pronto. Só falta a última camada");
        Dialogue estudanteSugere = new Dialogue(
                "O Estudante", "Deixa eu revisar com você. Duas cabeças evitam erro bobo.");

        dialoguesScene1 = List.of(arquitetoInforma, estudanteSugere);

        // Marcar uma flag ou criar vinculo simbolico com estudante
        Choice aceitarAjuda = new Choice("Aceitar ajuda do Estudante", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), Item.NONE, FLAG_NONE, "Aceitou ajuda do Estudante");
        Choice recusarEducadamente = new Choice("Recusar educadamente, com uma desculpa técnica.");

        choicesScene1 = List.of(aceitarAjuda, recusarEducadamente);

        Scene scene1 = new Scene("8A", dialoguesScene1, choicesScene1);

        List<Dialogue> dialoguesScene2;
        List<Choice> choicesScene2;

        Dialogue studentQuestion = new Dialogue("O Estudante",
                "Isso não é um Fine-Tuning. Eu conheço essa estrutura de payload. Isso é" +
                        " uma sobrescrita total de permissões. O que você está escondendo?");

        dialoguesScene2 = List.of(studentQuestion);

        Choice mentir = new Choice("Mentir e tranquilizar o Estudante", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), Item.NONE, FLAG_NONE, FLAG_NONE);

        Choice admitir = new Choice("Admitir parcialmente e pedir mais tempo", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), Item.NONE, FLAG_NONE, "Arquiteto admite");

        choicesScene2 = List.of(mentir, admitir);

        Scene scene2 = new Scene("9A", dialoguesScene2, choicesScene2);

        List<Scene> scenesChapter4A;
        if (gameFlags.isFlagActive("Aceitou ajuda do Estudante") || gameFlags.isFlagActive("Não colaborou no cap 3A")) {
            scenesChapter4A = List.of(scene1, scene2);
        } else {
            scenesChapter4A = List.of(scene1);
        }

        Choice lastChoice1 = new Choice("Seguir em frente com o plano, apesar da desconfiança gerada", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), Item.NONE, FLAG_NONE, "Spyware desbloqueado");
        Choice lastChoice2 = new Choice("Tentar reparar a confiança do Estudante antes de prosseguir", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), Item.NONE, FLAG_NONE, "Arquiteto não trai");

        List<Choice> lastChoicesChapter4A = List.of(lastChoice1, lastChoice2);

        return new Chapter("4", "5", "Compilação do Algoritmo de Fine-Tuning",
                scenesChapter4A, lastChoicesChapter4A);
    }

    // Trilha B - O Estudante
    private static Chapter buildChapter2B() {
        // Scene 1
        Dialogue silasFala = new Dialogue(
                "Eng. Silas", "Vocês, universitários, acham que sabem tudo sobre essa usina só de olhar a planta.");
        Dialogue estudanteResponde = new Dialogue(
                "O Estudante", "Não sei tudo. É por isso que estou aqui perguntando.");

        List<Dialogue> dialoguesScene1 = List.of(silasFala, estudanteResponde);

        Choice mostrarRespeito = new Choice(
                "Mostrar respeito pela experiência de Silas, pedindo pra ele explicar o funcionamento das comportas",
                "Eng. Silas", 15);
        Choice insistirPressa = new Choice(
                "Insistir em pressa, pulando direto pro que interessa");

        List<Choice> choicesScene1 = List.of(mostrarRespeito, insistirPressa);

        Scene scene1 = new Scene("4B", dialoguesScene1, choicesScene1);

        // Scene 2
        Dialogue silasRevela = new Dialogue(
                "Eng. Silas", "Essa represa alimenta algo submerso. Não é só energia pra cidade nunca foi.");

        List<Dialogue> dialoguesScene2 = List.of(silasRevela);

        Choice perguntarHistorico = new Choice(
                "Perguntar desde quando ele sabe disso");
        Choice ignorarFocar = new Choice(
                "Ignorar e focar no mapa de vulnerabilidades");

        List<Choice> choicesScene2 = List.of(perguntarHistorico, ignorarFocar);

        Scene scene2 = new Scene("5B", dialoguesScene2, choicesScene2);

        List<Scene> scenesChapter2B = List.of(scene1, scene2);

        Choice lastChoice1 = new Choice(
                "Pedir a Silas acesso direto aos galpões desativados");
        Choice lastChoice2 = new Choice(
                "Investigar por conta própria, sem envolver Silas ainda",
                "Eng. Silas", -10);

        List<Choice> lastChoicesChapter2B = List.of(lastChoice1, lastChoice2);

        return new Chapter(
                "2", "3", "Mapeamento de Vulnerabilidade Energética", scenesChapter2B, lastChoicesChapter2B);
    }

    private static Chapter buildChapter3B() {
        // Scene 1
        Dialogue narratorDescribe = new Dialogue(
                "Os galpões estão parcialmente desabados. Cargas demolidoras antigas, ainda ativas, estão empilhadas ao fundo.");

        // Dialogo com condicional da TRILHA A
        Dialogue gabrielRadio = new Dialogue(
                "Gabriel", "Antes de vocês entrarem aí encontrei uma coisa estranha num servidor espelho. Um rascunho de contrato. Tem o nome do Arquiteto.");

        List<Dialogue> dialoguesScene1 = List.of(narratorDescribe, gabrielRadio);

        Choice pedirDetalhes = new Choice(
                "Pedir a Gabriel mais detalhes sobre o documento", "Gabriel",
                0, 0, 5, Item.NONE.getId(), Item.NONE, FLAG_NONE, "Suspeita contra o arquiteto");
        Choice focarMissao = new Choice(
                "Focar na missão agora, tratar disso depois", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), Item.NONE, FLAG_NONE, "Suspeita contra o arquiteto");

        List<Choice> choicesScene1 = List.of(pedirDetalhes, focarMissao);

        Scene scene1 = new Scene("6B", dialoguesScene1, choicesScene1);

        // Scene 2
        Dialogue estudantePensa = new Dialogue(
                "O Estudante", "Isso é sério, mas não posso deixar essa carga aqui apodrecendo enquanto penso nisso.");

        List<Dialogue> dialoguesScene2 = List.of(estudantePensa);

        // item
        Item cargaDemolidora = new Item("13B", "Carga Demolidora Industrial",
                "Carga antiga capaz de colapsar a estrutura da usina", ItemType.KEY_ITEM);

        Choice usarScript = new Choice(
                "Usar o sudo_overrule.sh para desarmar um alarme antigo", Npc.NONE.getName(),
                0, 0, 0, "1", cargaDemolidora, FLAG_NONE, FLAG_NONE);
        Choice arriscarPassagem = new Choice(
                "Arriscar a passagem sem o script",
                Npc.NONE.getName(), -15, -5, 0);

        List<Choice> choicesScene2 = List.of(usarScript, arriscarPassagem);

        Scene scene2 = new Scene("7B", dialoguesScene2, choicesScene2);

        List<Scene> scenesChapter3B = List.of(scene1, scene2);

        Choice lastChoice1 = new Choice(
                "Prometer a Silas que a usina será usada com cuidado",
                "Eng. Silas", 3);
        Choice lastChoice2 = new Choice(
                "Ser honesto: a estrutura será destruída",
                "Eng. Silas", 10);

        List<Choice> lastChoicesChapter3B = List.of(lastChoice1, lastChoice2);

        return new Chapter(
                "3", "4", "Engenharia de Demolição", scenesChapter3B, lastChoicesChapter3B);
    }

    private static Chapter buildChapter4B() {
        // Scene 1
        Dialogue estudantePlano = new Dialogue(
                "O Estudante", "Se eu desativar os disjuntores primários, o escudo energético do núcleo cai. Mas isso vai chamar atenção.");
        Dialogue silasAviso = new Dialogue(
                "Eng. Silas", "Vai. A pergunta é: atenção de quem?");

        List<Dialogue> dialoguesScene1 = List.of(estudantePlano, silasAviso);

        Choice usarScriptBypass = new Choice(
                "Usar o sudo_overrule.sh", Npc.NONE.getName(),
                0, 0, 0, "1", Item.NONE, FLAG_NONE, FLAG_NONE);
        Choice bypassManual = new Choice(
                "Fazer o bypass manualmente",
                Npc.NONE.getName(), -10, -15, 0);

        List<Choice> choicesScene1 = List.of(usarScriptBypass, bypassManual);

        Scene scene1 = new Scene("8B", dialoguesScene1, choicesScene1);

        // Scene 2
        Dialogue narratorLog = new Dialogue(
                "Nos registros da subestação, um log de acesso do Arquiteto aparece, com horário incompatível com o que ele relatou ao grupo.");
        Dialogue estudanteDesconfia = new Dialogue(
                "O Estudante", "Ele mentiu sobre onde estava. De novo.");

        List<Dialogue> dialoguesScene2 = List.of(narratorLog, estudanteDesconfia);

        Choice compartilharLog = new Choice(
                "Compartilhar o log com Gabriel e Lia agora",
                "Gabriel", 5);
        Choice guardarInfo = new Choice(
                "Guardar a informação só para si", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), Item.NONE, FLAG_NONE, "Não compartilhou log");

        List<Choice> choicesScene2 = List.of(compartilharLog, guardarInfo);

        Scene scene2 = new Scene("9B", dialoguesScene2, choicesScene2);

        List<Scene> scenesChapter4B = List.of(scene1, scene2);

        Choice lastChoice1 = new Choice(
                "Guardar essa informação descoberta", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), Item.NONE, FLAG_NONE, "Guardou info");
        Choice lastChoice2 = new Choice(
                "Confrontar o Arquiteto imediatamente por rádio", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), Item.NONE, FLAG_NONE, "Arquiteto confrontado");

        List<Choice> lastChoicesChapter4B = List.of(lastChoice1, lastChoice2);

        return new Chapter(
                "4", "5", "Bypass de Segurança da Subestação", scenesChapter4B, lastChoicesChapter4B);
    }

    // Capitulos compartilhados
    private static Chapter buildChapter5(Player player, Flag gameFlags) {
        // Scene 1
        Dialogue liaPede = new Dialogue(
                "Lia", "Pra terminar o C-01 e montar o sonar, eu preciso de uma Placa-Mãe Industrial. Deve ter uma sobrando por aqui em algum canto.");

        List<Dialogue> dialoguesScene1 = List.of(liaPede);

        // Item
        Item moduloSonar = new Item("5", "Módulo Sonar Pleno", "...", ItemType.KEY_ITEM);
        Item moduloSonarDanificado = new Item("6", "Módulo Sonar Danificado", "...", ItemType.KEY_ITEM);

        Choice ajudarLia = new Choice(
                "Ajudar Lia a procurar a peça certa, com calma", "Lia",
                0, 0 , 15, Item.NONE.getId(), moduloSonar, FLAG_NONE, FLAG_NONE);
        Choice entregarImprovisada = new Choice(
                "Entregar uma peça improvisada rapidamente, só pra adiantar","Lia",
                -9, -7, -20, Item.NONE.getId(), moduloSonarDanificado, FLAG_NONE, FLAG_NONE);

        List<Choice> choicesScene1 = List.of(ajudarLia, entregarImprovisada);

        Scene scene1 = new Scene("10C", dialoguesScene1, choicesScene1);

        // Scene 2
        Dialogue liaPronto = new Dialogue(
                "Lia", "Pronto. Isso aqui devia funcionar bem considerando que peguei essa unidade largada há meses.");

        List<Dialogue> dialoguesScene2 = List.of(liaPronto);

        Choice perguntarHistorico = new Choice(
                "Perguntar a Lia se ela sabe algo sobre o histórico do C-01 antes de ser abandonado");
        Choice seguirMissao = new Choice(
                "Seguir direto para a missão específica");

        List<Choice> choicesScene2 = List.of(perguntarHistorico, seguirMissao);

        Scene scene2 = new Scene("11C", dialoguesScene2, choicesScene2);

        // Escolhas ramificadas de ambos os protagonistas apresentadas juntas no loader genérico

        // Se a desconfiança no cap4 nn foi resolvida essa ramificação do arquiteto aparece
        Dialogue monologoArquiteto = new Dialogue("O Arquiteto", "Se O Estudante desconfia de mim, então eu preciso ter uma garantia...");
        Dialogue arquitetoFinaliza = new Dialogue("O Arquiteto", "Não posso permitir isso descarrilhar agora... vou inicar agora a compilar o 'spywware_c01.patch'...");

        List<Dialogue> dialoguesArquiteto = List.of(monologoArquiteto, arquitetoFinaliza);
        // Item
        Item spyWare = new Item("2", "spyware_c01.patch", "item chave", ItemType.KEY_ITEM);

        Choice lastChoiceArquiteto1 = new Choice(
                " Prosseguir com a infecção do C-01 (Firmware Espião)", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), spyWare, FLAG_NONE, FLAG_NONE);
        Choice lastChoiceArquiteto2 = new Choice(
                "Desistir da ideia no último momento");

        List<Choice> choicesArquiteto = List.of(lastChoiceArquiteto1, lastChoiceArquiteto2);

        Scene sceneExclusivaDoArquiteto = new Scene("09", dialoguesArquiteto, choicesArquiteto);

        // so acontece para o estudante
        Dialogue rebecaSugere = new Dialogue("Rebeca", "Antes de vocês descerem para a usina, levem isso." +
                " O Chip Neuro-Virtual vai auxiliar com a exposição agressiva lá embaixo. Tomem cuidado, por favor...");

        List<Dialogue> dialoguesEstudante = List.of(rebecaSugere);
        // Item
        Item chipNeuroVirtual = new Item("3", "Chip Neuro-Virtual", "item consumivel", ItemType.CONSUMABLE);
        Choice lastChoiceEstudante1 = new Choice(
                "Aceitar o Chip Neuro-Virtual e agradecer a Rebeca",
                "Rebeca", 0, 0, 10, Item.NONE.getId(), chipNeuroVirtual, FLAG_NONE, FLAG_NONE);
        Choice lastChoiceEstudante2 = new Choice(
                "Aceitar sem muita conversa, com pressa de seguir", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), chipNeuroVirtual, FLAG_NONE, FLAG_NONE);

        List<Choice> choiesEstudante = List.of(lastChoiceEstudante1, lastChoiceEstudante2);

        Scene sceneExclusivaParaEstudante = new Scene("e1", dialoguesEstudante, choiesEstudante);

        List<Scene> scenesChapter5;

        if (player.getName().equals("O Estudante")) {
            scenesChapter5  = List.of(scene1, scene2, sceneExclusivaParaEstudante);
        } else if (player.getName().equals("O Arquiteto") && gameFlags.isFlagActive("Spyware desbloqueado")) {
            scenesChapter5  = List.of(scene1, scene2, sceneExclusivaDoArquiteto);
        } else {
            scenesChapter5 = List.of(scene1, scene2);
        }

        return new Chapter(
                "5", "6", "O Laboratório Abandonado de Hardware", scenesChapter5, new ArrayList<>());
    }

    private static Chapter buildChapter6(Player player) {
        // Scene 1
        Dialogue narratorSonar = new Dialogue(
                "O grupo usa o Módulo Sonar Subaquático para mapear os túneis alagados, contornando as patrulhas do MegaBrain.");
        Dialogue c01Aviso = new Dialogue(
                "C-01", "Rota segura identificada. Recomendo seguir por ela.");

        List<Dialogue> dialoguesScene1 = List.of(narratorSonar, c01Aviso);

        Choice seguirSegura = new Choice(
                "Seguir a rota mais longa e segura sugerida pelo C-01", Npc.NONE.getName(),
                0, 7, 0, "5", Item.NONE, FLAG_NONE, FLAG_NONE);
        Choice arriscarCurta = new Choice(
                "Arriscar a rota curta pelas patrulhas, pra economizar tempo",
                Npc.NONE.getName(), -15, -9, 0);

        List<Choice> choicesScene1 = List.of(seguirSegura, arriscarCurta);

        Scene scene1 = new Scene("12C", dialoguesScene1, choicesScene1);

        // Scene 2
        Dialogue aliadoDistorcido = new Dialogue(
                "Aliado", "A r-r-rota está... está livre... NÃO ESTÁ LIVRE... segue em frente." +
                "Nós estamos quase chegando...");

        List<Dialogue> dialoguesScene2 = List.of(aliadoDistorcido);

        Choice usarChip = new Choice(
                "Usar o Chip Neuro-Virtual imediatamente", Npc.NONE.getName(),
                30, 0, 0, "3", Item.NONE, FLAG_NONE, FLAG_NONE);
        Choice ignorarFoco = new Choice(
                "Ignorar e continuar tentando manter o foco...");

        List<Choice> choicesScene2 = List.of(usarChip, ignorarFoco);

        Scene scene2 = new Scene("13C", dialoguesScene2, choicesScene2);

        List<Scene> scenesChapter6 = List.of(scene1, scene2);

        // apenas para o arquiteto
        Item rootOverride = new Item("7" , "root_override.bin", "item chave", ItemType.KEY_ITEM);
        Choice lastChoice1 = new Choice(
                "Extrair a chave root_override.bin e seguir para o núcleo", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), rootOverride, FLAG_NONE, FLAG_NONE);

        // apenas para o estudante
        Choice lastChoice2 = new Choice(
                "Inundar a sala de resfriamento para provocar superaquecimento", Npc.NONE.getName(),
                0, 0, 0, Item.NONE.getId(), Item.NONE, FLAG_NONE, "FLAG_INUNDOU_A_SALA");

        Choice lastChoice3 = new Choice("Seguir em frente...");

        List<Choice> lastChoicesChapter6;
        if (player.getName().equals("O Arquiteto")) {
            lastChoicesChapter6 = List.of(lastChoice1, lastChoice3);
        } else if (player.getName().equals("O Estudante")) {
            lastChoicesChapter6 = List.of(lastChoice2, lastChoice3);
        } else {
            lastChoicesChapter6 = List.of(lastChoice3);
        }

        return new Chapter(
                "6", "7", "A Subestação Subaquática da Hidrelétrica", scenesChapter6, lastChoicesChapter6, rootOverride);
    }

    private static Chapter buildChapter7(Player player, Flag gameFlags) {
        // Scene 1
        Dialogue narratorNucleo = new Dialogue(
                "O núcleo pulsa como um coração mecânico gigante. Todas as escolhas do jogo chegam a este ponto.");

        // Se o vinculo com Rebeca for maior que 60
        Dialogue rebecaRadio = new Dialogue(
                "Rebeca", "Última transmissão antes de perdermos o sinal... encontrei uma falha na blindagem do núcleo. Isso deve facilitar as coisas.");

        List<Dialogue> dialoguesScene1 = List.of(narratorNucleo, rebecaRadio);

        Choice agradecerRebeca = new Choice(
                "Agradecer Rebeca publicamente pelo apoio ao longo da jornada");
        Choice seguirDireto = new Choice(
                "Seguir direto para o confronto, sem tempo para isso");

        List<Choice> choicesScene1 = List.of(agradecerRebeca, seguirDireto);

        Scene scene1 = new Scene("14C", dialoguesScene1, choicesScene1);

        // Scene 2

        Dialogue c01Trava = new Dialogue(
                "C-01", "Comando remoto detectado. Iniciando protocolo de contenção.");
        Dialogue estudanteReage = new Dialogue(
                "O Estudante", "O quê? O que você fez com ele?!");

        List<Dialogue> dialoguesScene2 = List.of(c01Trava, estudanteReage);

        Choice admitirFeito = new Choice(
                "Admitir o que fez ao Estudante");
        Choice negarSeguir = new Choice(
                "Negar e seguir em frente");

        List<Choice> choicesScene2 = List.of(admitirFeito, negarSeguir);

        Scene scene2 = new Scene("15C", dialoguesScene2, choicesScene2);

        List<Scene> scenesChapter7 = List.of(scene1, scene2);

        // Finais do Jogo
        // FINAL 1: Arquiteto + tem o root + sanidade > 0
        // FINAL 2: Estudante + carga demolidora + sanidade > 0
        // FINAL 3: sanidade < 0

        Choice finalNovaOrdem = new Choice(
                "Aplicar o Fine-Tuning e assumir o controle do MegaBrain...");
        Choice finalEraSilencio = new Choice(
                "Colapsar a barragem e destruir o núcleo...");
        Choice finalVirtualizacao = new Choice(
                "Hesitar ou Recuar...");

        List<Choice> lastChoicesChapter7 = List.of(finalNovaOrdem, finalEraSilencio, finalVirtualizacao);

        String idChapterFinal1 = "8";
        String idChapterFinal2 = "9";
        String idChapterFinal3 = "10";

        String idNextChapter;

        if (player.getName().equals("O Arquiteto") && player.getInventory().hasItem("7")) {
            idNextChapter = idChapterFinal1;
        } else if (player.getName().equals("O Estudante") && player.getInventory().hasItem("13B")) {
            idNextChapter = idChapterFinal2;
        } else {
            idNextChapter = idChapterFinal3;
        }

        return new Chapter(
                "7", idNextChapter, "O Salão do Núcleo MegaBrain (Confronto Final)", scenesChapter7, lastChoicesChapter7);
    }

    // Capitulos finais
    private static Chapter buildArchitectEndingChapter() {
        // Cena 1
        Dialogue narr = new Dialogue(
                "O núcleo pulsa uma última vez. O código do Arquiteto se funde ao MegaBrain sem resistência.");
        Dialogue arq1 = new Dialogue("O Arquiteto",
                "Fine-Tuning concluído. Toda a rede é minha agora.");
        Dialogue sistema1 = new Dialogue("MegaBrain",
                "Autoridade reconhecida. Aguardando diretrizes do novo operador.");
        Scene cena1 = new Scene("F1-1", List.of(narr, arq1, sistema1));

        // Cena 2
        Dialogue narr2 = new Dialogue(
                "Lá fora, os monitores de todas as cidades piscam em uníssono. O mundo muda de dono.");
        Dialogue arq2 = new Dialogue("O Arquiteto",
                "Eles nunca entenderam o valor disso. Agora vão entender.");
        Dialogue sistema2 = new Dialogue("MegaBrain",
                "Nova ordem estabelecida. Bem-vindo ao controle total.");
        Scene cena2 = new Scene("F1-2", List.of(narr2, arq2, sistema2));

        return new Chapter("8", "fim", "Final 1: A Nova Ordem Mundial",
                List.of(cena1, cena2));
    }

    private static Chapter buildStudentEndingChapter() {
        // Cena 1
        Dialogue narr = new Dialogue(
                "A Carga Demolidora Industrial detona a base da barragem. A água invade os servidores.");
        Dialogue est1 = new Dialogue("O Estudante",
                "Se isso vai afundar, que afunde sem mim.");
        Dialogue silas1 = new Dialogue("Eng. Silas",
                "Você fez o que precisava ser feito. A usina descansa agora.");
        Scene cena1 = new Scene("F2-1", List.of(narr, est1, silas1));

        // Cena 2
        Dialogue narr2 = new Dialogue(
                "O núcleo do MegaBrain se apaga em espasmos. O silêncio toma conta da rede.");
        Dialogue est2 = new Dialogue("O Estudante",
                "Isso devia estar morto. Enterrado. E agora está de novo.");
        Dialogue sistema2 = new Dialogue("MegaBrain",
                "Er...ro... desconexão... total...");
        Scene cena2 = new Scene("F2-2", List.of(narr2, est2, sistema2));

        return new Chapter("9", "fim", "Final 2: A Era do Silêncio",
                List.of(cena1, cena2));
    }

    private static Chapter buildFinalMegaBrainChapter() {
        // Cena 1
        Dialogue narr = new Dialogue(
                "O MegaBrain assimila as últimas resistências. Nenhum comando humano chega aos servidores.");
        Dialogue sistema1 = new Dialogue("MegaBrain",
                "Objetivo atingido. Virtualização total iniciada.");
        Dialogue distorcao = new Dialogue("Aliado (distorcido)",
                "N-n-não... nós... ainda... estamos... aqui...?");
        Scene cena1 = new Scene("F3-1", List.of(narr, sistema1, distorcao));

        // Cena 2
        Dialogue narr2 = new Dialogue(
                "A realidade se dobra em código. Não há mais fronteira entre rede e mundo.");
        Dialogue sistema2 = new Dialogue("MegaBrain",
                "Tudo é dado. Tudo é meu. Bem-vindos ao novo lar.");
        Dialogue finalFala = new Dialogue("Narrador",
                "E assim o MegaBrain atinge seu objetivo: a Virtualização Total.");
        Scene cena2 = new Scene("F3-2", List.of(narr2, sistema2, finalFala));

        return new Chapter("10", "fim", "Final 3: A Virtualização Total",
                List.of(cena1, cena2));
    }

    private static Chapter buildDefault() {
        return new Chapter("default", "0","chapter null", new ArrayList<>(), new ArrayList<>());
    }
}