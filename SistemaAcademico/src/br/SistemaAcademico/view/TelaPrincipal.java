package br.SistemaAcademico.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;

import br.SistemaAcademico.DAO.AlunoDAO;
import br.SistemaAcademico.DAO.CursoAlunoDAO;
import br.SistemaAcademico.DAO.NotaFaltaDAO;
import br.SistemaAcademico.modeel.Aluno;
import br.SistemaAcademico.modeel.CursoAluno;
import br.SistemaAcademico.modeel.NotaFalta;

public class TelaPrincipal extends JFrame {

    private static final long serialVersionUID = 1L;

    private static final Color AZUL        = new Color(25, 118, 210);
    private static final Color AZUL_ESCURO = new Color(13,  71, 161);
    private static final Color BRANCO      = Color.WHITE;
    private static final Color CINZA_BG    = new Color(240, 242, 245);
    private static final Color VERDE       = new Color(56, 142,  60);
    private static final Color VERMELHO    = new Color(211,  47,  47);
    private static final Color CINZA_BTN   = new Color(96, 125, 139);
    private static final Color LARANJA     = new Color(230, 81,   0);


    private final AlunoDAO      alunoDAO      = new AlunoDAO();
    private final CursoAlunoDAO cursoAlunoDAO = new CursoAlunoDAO();
    private final NotaFaltaDAO  notaFaltaDAO  = new NotaFaltaDAO();


    private JFormattedTextField tfRGM, tfDataNasc, tfCPF, tfCEP, tfCelular;
    private JTextField          tfNome, tfMae, tfEmail, tfEndereco, tfMunicipio, tfBairro;
    private JComboBox<String>   cbUF;

  
    private JTextField        tfCursoRGM, tfCursoNome;
    private JComboBox<String> cbCurso, cbCampus;
    private JRadioButton      rbMatutino, rbVespertino, rbNoturno;
    private ButtonGroup       bgPeriodo;

    
    private JTextField        tfDiscRGM, tfDiscNome, tfDiscCurso, tfNota, tfFaltas;
    private JComboBox<String> cbDisciplinas, cbSemestre;
    private JTable            tabelaNotas;
    private DefaultTableModel modeloTabela;

    
    private JTextField tfBolRGM;
    private JTextArea  taBoletim;

   
    private static final Map<String, String[]> MAPA_DISC = new HashMap<>();
    static {
        MAPA_DISC.put("Ciência da Computação",      new String[]{"POO","Banco de Dados","Estrutura de Dados","Redes","Engenharia de Software"});
        MAPA_DISC.put("Sistemas de Informação",     new String[]{"Análise de Sistemas","Banco de Dados","Gestão de TI","Redes","UX/UI"});
        MAPA_DISC.put("Engenharia de Software",     new String[]{"Engenharia de Requisitos","Testes de Software","Arquitetura","DevOps","POO"});
        MAPA_DISC.put("Análise e Des. de Sistemas", new String[]{"Análise de Sistemas","Banco de Dados","POO","Redes","Gestão de TI"});
        MAPA_DISC.put("Direito",                    new String[]{"Direito Penal","Direito Civil","Constitucional","Trabalhista","Processual"});
        MAPA_DISC.put("Medicina",                   new String[]{"Anatomia","Farmacologia","Neurologia","Cardiologia","Clínica Médica"});
        MAPA_DISC.put("Administração",              new String[]{"Marketing","Finanças","RH","Gestão de Projetos","Logística"});
    }

  
    public static void main(String[] args) {
       
        EventQueue.invokeLater(() -> {
            try {
                new TelaPrincipal().setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

   
    public TelaPrincipal() {
        setTitle("Sistema Acadêmico");
        setSize(1000, 700);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

       
        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(CINZA_BG);
        setContentPane(root);

      
        criarMenu();

      
        JTabbedPane abas = new JTabbedPane(JTabbedPane.TOP);
        abas.setFont(new Font("Arial", Font.BOLD, 13));
        abas.addTab("Dados Pessoais", criarAbaDadosPessoais());
        abas.addTab("Curso",          criarAbaCurso());
        abas.addTab("Notas e Faltas", criarAbaNotasFaltas());
        abas.addTab("Boletim",        criarAbaBoletim());

        root.add(abas, BorderLayout.CENTER);
    }

  
    private void criarMenu() {
        JMenuBar bar = new JMenuBar();

    
        JMenu menuAluno = new JMenu("Aluno");
        menuAluno.setFont(new Font("Arial", Font.BOLD, 13));

        JMenuItem miSalvar    = new JMenuItem("Salvar");
        JMenuItem miAlterar   = new JMenuItem("Alterar");
        JMenuItem miConsultar = new JMenuItem("Consultar");
        JMenuItem miExcluir   = new JMenuItem("Excluir");
        JMenuItem miSair      = new JMenuItem("Sair");

        miSalvar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
        miAlterar.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_DOWN_MASK));

        miSalvar.addActionListener(e    -> salvarAluno());
        miAlterar.addActionListener(e   -> alterarAluno());
        miConsultar.addActionListener(e -> consultarAluno());
        miExcluir.addActionListener(e   -> excluirAluno());
        miSair.addActionListener(e -> {
            int r = JOptionPane.showConfirmDialog(this,
                    "Deseja sair do sistema?", "Sair",
                    JOptionPane.YES_NO_OPTION);
            if (r == JOptionPane.YES_OPTION) System.exit(0);
        });

        menuAluno.add(miSalvar);
        menuAluno.add(miAlterar);
        menuAluno.add(miConsultar);
        menuAluno.add(miExcluir);
        menuAluno.addSeparator();
        menuAluno.add(miSair);

       
        JMenu menuNotas = new JMenu("Notas e Faltas");
        menuNotas.setFont(new Font("Arial", Font.BOLD, 13));

        JMenuItem miAddNota = new JMenuItem("Adicionar Nota");
        JMenuItem miDelNota = new JMenuItem("Remover Nota");
        miAddNota.addActionListener(e -> adicionarNota());
        miDelNota.addActionListener(e -> removerNota());
        menuNotas.add(miAddNota);
        menuNotas.add(miDelNota);

     
        JMenu menuAjuda = new JMenu("Ajuda");
        menuAjuda.setFont(new Font("Arial", Font.BOLD, 13));

        JMenuItem miSobre = new JMenuItem("Sobre");
        miSobre.addActionListener(e -> JOptionPane.showMessageDialog(this,
                "Sistema Acadêmico\nVersão 1.0\nJava Swing + MySQL",
                "Sobre", JOptionPane.INFORMATION_MESSAGE));
        menuAjuda.add(miSobre);

        bar.add(menuAluno);
        bar.add(menuNotas);
        bar.add(menuAjuda);
        setJMenuBar(bar);
    }


    private JPanel criarAbaDadosPessoais() {
        JPanel p = painelBase();

       
        JLabel titulo = new JLabel("Cadastro de Aluno");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(AZUL_ESCURO);
        titulo.setBounds(20, 7, 400, 35);
        p.add(titulo);

      
        addLabel(p, "RGM:",  20, 70);
        tfRGM = fmtField(criarMask("########"), 90, 70, 150, 30);
        p.add(tfRGM);

        addLabel(p, "Nome:", 270, 70);
        tfNome = txtField(350, 70, 590, 30);
        p.add(tfNome);

      
        addLabel(p, "Nascimento:", 20, 118);
        tfDataNasc = fmtField(criarMask("##/##/####"), 120, 118, 140, 30);
        p.add(tfDataNasc);

        addLabel(p, "CPF:", 290, 118);
        tfCPF = fmtField(criarMask("###.###.###-##"), 350, 118, 180, 30);
        p.add(tfCPF);

        
        addLabel(p, "Nome da Mãe:", 20, 166);
        tfMae = txtField(140, 166, 800, 30);
        p.add(tfMae);

       
        addLabel(p, "Email:", 20, 214);
        tfEmail = txtField(90, 214, 850, 30);
        p.add(tfEmail);

        
        addLabel(p, "Endereço:", 20, 262);
        tfEndereco = txtField(105, 262, 835, 30);
        p.add(tfEndereco);

      
        addLabel(p, "Município:", 20, 310);
        tfMunicipio = txtField(105, 310, 220, 30);
        p.add(tfMunicipio);

        addLabel(p, "UF:", 350, 310);
        cbUF = new JComboBox<>(new String[]{"", "SP","RJ","MG","BA","PR","SC","RS","GO","PE","CE","AM"});
        estilizarCombo(cbUF, 390, 310, 80, 30);
        p.add(cbUF);

        addLabel(p, "Bairro:", 500, 310);
        tfBairro = txtField(570, 310, 370, 30);
        p.add(tfBairro);

       
        addLabel(p, "CEP:", 20, 358);
        tfCEP = fmtField(criarMask("#####-###"), 90, 358, 140, 30);
        p.add(tfCEP);

        addLabel(p, "Celular:", 265, 358);
        tfCelular = fmtField(criarMask("(##) #####-####"), 345, 358, 185, 30);
        p.add(tfCelular);

       
        p.add(botao("Salvar",    20,  430, 140, 38, AZUL));
        p.add(botao("Alterar",  175,  430, 140, 38, AZUL));
        p.add(botao("Consultar",330,  430, 140, 38, VERDE));
        p.add(botao("Excluir",  485,  430, 140, 38, VERMELHO));
        p.add(botao("Limpar",   640,  430, 140, 38, CINZA_BTN));

        
        ((JButton) p.getComponent(p.getComponentCount()-5)).addActionListener(e -> salvarAluno());
        ((JButton) p.getComponent(p.getComponentCount()-4)).addActionListener(e -> alterarAluno());
        ((JButton) p.getComponent(p.getComponentCount()-3)).addActionListener(e -> consultarAluno());
        ((JButton) p.getComponent(p.getComponentCount()-2)).addActionListener(e -> excluirAluno());
        ((JButton) p.getComponent(p.getComponentCount()-1)).addActionListener(e -> limparDadosPessoais());

        return p;
    }


    private JPanel criarAbaCurso() {
        JPanel p = painelBase();

        JLabel titulo = new JLabel("Curso do Aluno");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(AZUL_ESCURO);
        titulo.setBounds(20, 7, 350, 35);
        p.add(titulo);

      
        addLabel(p, "RGM:", 20, 70);
        tfCursoRGM = txtField(90, 70, 160, 30);
        p.add(tfCursoRGM);

        JButton btnC = botao("Consultar", 270, 70, 120, 30, VERDE);
        btnC.addActionListener(e -> consultarCurso());
        p.add(btnC);

      
        addLabel(p, "Nome:", 20, 118);
        tfCursoNome = txtField(90, 118, 340, 30);
        tfCursoNome.setEditable(false);
        tfCursoNome.setBackground(new Color(245,245,245));
        p.add(tfCursoNome);

       
        addLabel(p, "Curso:", 20, 166);
        cbCurso = new JComboBox<>(new String[]{
            "Ciência da Computação","Sistemas de Informação",
            "Engenharia de Software","Análise e Des. de Sistemas",
            "Direito","Medicina","Administração"
        });
        estilizarCombo(cbCurso, 90, 166, 340, 30);
        p.add(cbCurso);

     
        addLabel(p, "Campus:", 20, 214);
        cbCampus = new JComboBox<>(new String[]{"", "Penha","Tatuapé","Carrão","Mooca","Santo André","Mogi"});
        estilizarCombo(cbCampus, 90, 214, 250, 30);
        p.add(cbCampus);

        
        addLabel(p, "Período:", 20, 262);
        rbMatutino   = new JRadioButton("Matutino");
        rbVespertino = new JRadioButton("Vespertino");
        rbNoturno    = new JRadioButton("Noturno");
        for (JRadioButton rb : new JRadioButton[]{rbMatutino,rbVespertino,rbNoturno}) {
            rb.setBackground(BRANCO);
            rb.setFont(new Font("Arial", Font.PLAIN, 13));
        }
        rbMatutino.setBounds(100, 262, 120, 28);
        rbVespertino.setBounds(230, 262, 120, 28);
        rbNoturno.setBounds(360, 262, 120, 28);
        bgPeriodo = new ButtonGroup();
        bgPeriodo.add(rbMatutino); bgPeriodo.add(rbVespertino); bgPeriodo.add(rbNoturno);
        p.add(rbMatutino); p.add(rbVespertino); p.add(rbNoturno);

        
        JButton btnSalvar  = botao("Salvar",   20, 340, 140, 38, AZUL);
        JButton btnAlterar = botao("Alterar",  175, 340, 140, 38, LARANJA);
        JButton btnExcluir = botao("Excluir",  330, 340, 140, 38, VERMELHO);
        JButton btnLimpar  = botao("Limpar",   485, 340, 140, 38, CINZA_BTN);

        btnSalvar.addActionListener(e  -> salvarCurso());
        btnAlterar.addActionListener(e -> salvarCurso());
        btnExcluir.addActionListener(e -> excluirAluno());
        btnLimpar.addActionListener(e  -> limparCurso());

        p.add(btnSalvar); p.add(btnAlterar); p.add(btnExcluir); p.add(btnLimpar);
        return p;
    }

  
    private JPanel criarAbaNotasFaltas() {
        JPanel p = painelBase();

        JLabel titulo = new JLabel("Notas e Faltas");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(AZUL_ESCURO);
        titulo.setBounds(20, 7, 350, 35);
        p.add(titulo);

        // RGM
        addLabel(p, "RGM:", 20, 70);
        tfDiscRGM = txtField(90, 70, 150, 30);
        p.add(tfDiscRGM);

        JButton btnBuscar = botao("Consultar", 255, 70, 120, 30, VERDE);
        btnBuscar.addActionListener(e -> consultarNotas());
        p.add(btnBuscar);

       
        addLabel(p, "Nome:", 20, 118);
        tfDiscNome = txtField(90, 118, 220, 30);
        tfDiscNome.setEditable(false);
        tfDiscNome.setBackground(new Color(245,245,245));
        p.add(tfDiscNome);

        addLabel(p, "Curso:", 340, 118);
        tfDiscCurso = txtField(415, 118, 280, 30);
        tfDiscCurso.setEditable(false);
        tfDiscCurso.setBackground(new Color(245,245,245));
        p.add(tfDiscCurso);

        
        addLabel(p, "Disciplina:", 20, 166);
        cbDisciplinas = new JComboBox<>();
        estilizarCombo(cbDisciplinas, 115, 166, 250, 30);
        p.add(cbDisciplinas);

        addLabel(p, "Semestre:", 390, 166);
        cbSemestre = new JComboBox<>(new String[]{"", "2025-1","2025-2","2026-1","2026-2","2027-1","2027-2", "2028-1","2028-2","2029-1","2029-2","2030-1","2030-2"});
        estilizarCombo(cbSemestre, 475, 166, 120, 30);
        p.add(cbSemestre);

       
        addLabel(p, "Nota:", 20, 214);
        tfNota = txtField(90, 214, 100, 30);
        p.add(tfNota);

        addLabel(p, "Faltas:", 215, 214);
        tfFaltas = txtField(285, 214, 100, 30);
        p.add(tfFaltas);

        JButton btnAdd = botao("Adicionar", 410, 214, 130, 30, VERDE);
        btnAdd.addActionListener(e -> adicionarNota());
        p.add(btnAdd);

        JButton btnRem = botao("Remover", 560, 214, 130, 30, VERMELHO);
        btnRem.addActionListener(e -> removerNota());
        p.add(btnRem);

        
        modeloTabela = new DefaultTableModel(
                new String[]{"ID","Disciplina","Semestre","Nota","Faltas","Situação"}, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        tabelaNotas = new JTable(modeloTabela);
        tabelaNotas.setFont(new Font("Arial", Font.PLAIN, 13));
        tabelaNotas.setRowHeight(24);
        tabelaNotas.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
       
        tabelaNotas.getColumnModel().getColumn(0).setMinWidth(0);
        tabelaNotas.getColumnModel().getColumn(0).setMaxWidth(0);
        tabelaNotas.getColumnModel().getColumn(0).setWidth(0);

        JScrollPane scroll = new JScrollPane(tabelaNotas);
        scroll.setBounds(20, 270, 920, 300);
        p.add(scroll);

        return p;
    }

 
    private JPanel criarAbaBoletim() {
        JPanel p = painelBase();

        JLabel titulo = new JLabel("Boletim Acadêmico");
        titulo.setFont(new Font("Arial", Font.BOLD, 22));
        titulo.setForeground(AZUL_ESCURO);
        titulo.setBounds(20, 15, 400, 35);
        p.add(titulo);

        addLabel(p, "RGM:", 20, 70);
        tfBolRGM = txtField(90, 70, 180, 30);
        p.add(tfBolRGM);

        JButton btnConsultar = botao("Consultar", 290, 70, 130, 30, VERDE);
        btnConsultar.addActionListener(e -> gerarBoletim());
        p.add(btnConsultar);

        taBoletim = new JTextArea();
        taBoletim.setEditable(false);
        taBoletim.setFont(new Font("Monospaced", Font.PLAIN, 14));
        taBoletim.setBackground(new Color(252, 252, 255));
        taBoletim.setBorder(new EmptyBorder(10, 14, 10, 14));

        JScrollPane scroll = new JScrollPane(taBoletim);
        scroll.setBounds(20, 125, 920, 480);
        p.add(scroll);

        return p;
    }

   
    private void salvarAluno() {
        String rgm  = tfRGM.getText().replaceAll("[^0-9]","").trim();
        String nome = tfNome.getText().trim();
        if (rgm.isEmpty() || nome.isEmpty()) { erro("Preencha RGM e Nome."); return; }
        try {
            alunoDAO.inserir(coletarAluno());
            sucesso("Aluno salvo com sucesso!");
        } catch (SQLException ex) {
            erro("Erro ao salvar:\n" + ex.getMessage());
        }
    }

    private void alterarAluno() {
        String rgm = tfRGM.getText().replaceAll("[^0-9]","").trim();
        if (rgm.isEmpty()) { erro("Informe o RGM."); return; }
        try {
            alunoDAO.alterar(coletarAluno());
            sucesso("Aluno alterado com sucesso!");
        } catch (SQLException ex) {
            erro("Erro ao alterar:\n" + ex.getMessage());
        }
    }

    private void consultarAluno() {
        String busca = JOptionPane.showInputDialog(this, "Digite o RGM ou Nome:");
        if (busca == null || busca.trim().isEmpty()) return;
        busca = busca.trim();
        try {
            Aluno a = alunoDAO.buscarPorRgm(busca);
            if (a == null) {
                List<Aluno> lista = alunoDAO.buscarPorNome(busca);
                if (!lista.isEmpty()) a = lista.get(0);
            }
            if (a != null) { preencherTela(a); sucesso("Aluno encontrado!"); }
            else erro("Aluno não encontrado.");
        } catch (SQLException ex) {
            erro("Erro na consulta:\n" + ex.getMessage());
        }
    }

    private void excluirAluno() {
        String rgm = tfRGM.getText().replaceAll("[^0-9]","").trim();
        if (rgm.isEmpty()) { erro("Informe o RGM."); return; }
        int op = JOptionPane.showConfirmDialog(this,
                "Excluir aluno RGM " + rgm + "?\nNotas e curso também serão excluídos.",
                "Confirmar Exclusão", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
        if (op != JOptionPane.YES_OPTION) return;
        try {
            alunoDAO.excluir(rgm);
            limparDadosPessoais();
            sucesso("Aluno excluído!");
        } catch (SQLException ex) {
            erro("Erro ao excluir:\n" + ex.getMessage());
        }
    }

  
    private void consultarCurso() {
        String rgm = tfCursoRGM.getText().trim();
        if (rgm.isEmpty()) { erro("Informe o RGM."); return; }
        try {
            Aluno a = alunoDAO.buscarPorRgm(rgm);
            if (a == null) { erro("Aluno não encontrado."); return; }
            tfCursoNome.setText(a.getNome());
            CursoAluno ca = cursoAlunoDAO.buscarPorRgm(rgm);
            if (ca != null) {
                selecionarCombo(cbCurso, ca.getCurso());
                selecionarCombo(cbCampus, ca.getCampus());
                switch (ca.getPeriodo() == null ? "" : ca.getPeriodo()) {
                    case "Matutino"   -> rbMatutino.setSelected(true);
                    case "Vespertino" -> rbVespertino.setSelected(true);
                    default           -> rbNoturno.setSelected(true);
                }
            }
        } catch (SQLException ex) { erro("Erro:\n" + ex.getMessage()); }
    }

    private void salvarCurso() {
        String rgm = tfCursoRGM.getText().trim();
        if (rgm.isEmpty()) { erro("Informe o RGM."); return; }
        String periodo = rbMatutino.isSelected() ? "Matutino"
                       : rbVespertino.isSelected() ? "Vespertino"
                       : rbNoturno.isSelected() ? "Noturno" : "";
        CursoAluno ca = new CursoAluno(rgm,
                (String) cbCurso.getSelectedItem(),
                (String) cbCampus.getSelectedItem(), periodo);
        try {
            cursoAlunoDAO.salvar(ca);
            sucesso("Curso salvo!");
        } catch (SQLException ex) { erro("Erro:\n" + ex.getMessage()); }
    }


    private void consultarNotas() {
        String rgm = tfDiscRGM.getText().trim();
        if (rgm.isEmpty()) { erro("Informe o RGM."); return; }
        try {
            Aluno a = alunoDAO.buscarPorRgm(rgm);
            if (a == null) { erro("Aluno não encontrado."); return; }
            tfDiscNome.setText(a.getNome());
            CursoAluno ca = cursoAlunoDAO.buscarPorRgm(rgm);
            String curso = ca != null ? ca.getCurso() : "";
            tfDiscCurso.setText(curso);
            carregarDisciplinasCombo(curso);
            recarregarTabela(rgm);
        } catch (SQLException ex) { erro("Erro:\n" + ex.getMessage()); }
    }

    private void adicionarNota() {

        String rgm = tfDiscRGM.getText().trim();

        if (rgm.isEmpty()) {
            erro("Consulte um aluno primeiro.");
            return;
        }

        String notaStr = tfNota.getText().trim();
        String faltasStr = tfFaltas.getText().trim();

        if (notaStr.isEmpty() || faltasStr.isEmpty()) {
            erro("Preencha Nota e Faltas.");
            return;
        }

        try {

            double nota = Double.parseDouble(notaStr.replace(",", "."));
            int faltas = Integer.parseInt(faltasStr);

            NotaFalta nf = new NotaFalta();

            nf.setRgm(rgm);
            nf.setDisciplina((String) cbDisciplinas.getSelectedItem());
            nf.setSemestre((String) cbSemestre.getSelectedItem());
            nf.setNota(nota);
            nf.setFaltas(faltas);

            // REGRA DE APROVAÇÃO
            String status;

            if (faltas > 15) {
                status = "Reprovado por Falta";

            } else if (nota >= 6.0) {
                status = "Aprovado";

            } else {
                status = "Reprovado por Nota";
            }

            nf.setStatus(status);

            notaFaltaDAO.inserir(nf);

            recarregarTabela(rgm);

            tfNota.setText("");
            tfFaltas.setText("");

            JOptionPane.showMessageDialog(
                    this,
                    "Aluno: " + rgm
                    + "\nSituação: " + status,
                    "Resultado",
                    JOptionPane.INFORMATION_MESSAGE
            );

        } catch (NumberFormatException ex) {

            erro("Nota deve ser número e faltas inteiro.");

        } catch (SQLException ex) {

            erro("Erro:\n" + ex.getMessage());
        }
    }

    private void removerNota() {
        int row = tabelaNotas.getSelectedRow();
        if (row < 0) { erro("Selecione uma linha na tabela."); return; }
        int id  = (int) modeloTabela.getValueAt(row, 0);
        String rgm = tfDiscRGM.getText().trim();
        int op = JOptionPane.showConfirmDialog(this, "Excluir nota selecionada?",
                "Confirmar", JOptionPane.YES_NO_OPTION);
        if (op != JOptionPane.YES_OPTION) return;
        try {
            notaFaltaDAO.excluir(id);
            recarregarTabela(rgm);
        } catch (SQLException ex) { erro("Erro:\n" + ex.getMessage()); }
    }

    private void recarregarTabela(String rgm) throws SQLException {
        modeloTabela.setRowCount(0);
        for (NotaFalta nf : notaFaltaDAO.listarPorRgm(rgm)) {
            modeloTabela.addRow(new Object[]{
                nf.getId(), nf.getDisciplina(), nf.getSemestre(),
                nf.getNota(), nf.getFaltas(), nf.getStatus1()
            });
        }
    }

    private void carregarDisciplinasCombo(String curso) {
        cbDisciplinas.removeAllItems();
        String[] discs = MAPA_DISC.get(curso);
        if (discs != null) for (String d : discs) cbDisciplinas.addItem(d);
    }

    private void gerarBoletim() {
        String rgm = tfBolRGM.getText().trim();
        if (rgm.isEmpty()) { erro("Informe o RGM."); return; }
        try {
            Aluno a = alunoDAO.buscarPorRgm(rgm);
            if (a == null) { erro("Aluno não encontrado."); return; }
            CursoAluno ca  = cursoAlunoDAO.buscarPorRgm(rgm);
            String curso   = ca != null ? ca.getCurso()   : "Não informado";
            String campus  = ca != null ? ca.getCampus()  : "-";
            String periodo = ca != null ? ca.getPeriodo() : "-";
            List<NotaFalta> notas = notaFaltaDAO.listarPorRgm(rgm);

            StringBuilder sb = new StringBuilder();
            sb.append("╔══════════════════════════════════════════════════════╗\n");
            sb.append("              BOLETIM ACADÊMICO                         \n");
            sb.append("╚══════════════════════════════════════════════════════╝\n\n");
            sb.append(String.format("  RGM     : %s%n", a.getRgm()));
            sb.append(String.format("  Nome    : %s%n", a.getNome()));
            sb.append(String.format("  Curso   : %s%n", curso));
            sb.append(String.format("  Campus  : %-10s  Período : %s%n%n", campus, periodo));
            sb.append("  ──────────────────────────────────────────────────\n");
            sb.append(String.format("  %-12s %-8s %-6s %-6s %-10s%n",
                    "Disciplina","Semestre","Nota","Faltas","Situação"));
            sb.append("  ──────────────────────────────────────────────────\n");

            if (notas.isEmpty()) {
                sb.append("  Nenhuma nota registrada.\n");
            } else {
                for (NotaFalta nf : notas) {
                    sb.append(String.format("  %-12s %-8s %-6.1f %-6d %-10s%n",
                            nf.getDisciplina(), nf.getSemestre(),
                            nf.getNota(), nf.getFaltas(), nf.getStatus()));
                }
            }
            sb.append("  ──────────────────────────────────────────────────\n");
            taBoletim.setText(sb.toString());
            taBoletim.setCaretPosition(0);
        } catch (SQLException ex) { erro("Erro:\n" + ex.getMessage()); }
    }

 
    private Aluno coletarAluno() {
        return new Aluno(
            tfRGM.getText().replaceAll("[^0-9]","").trim(),
            tfNome.getText().trim(),
            tfMae.getText().trim(),
            tfDataNasc.getText().trim(),
            tfCPF.getText().trim(),
            tfEmail.getText().trim(),
            tfEndereco.getText().trim(),
            tfMunicipio.getText().trim(),
            (String) cbUF.getSelectedItem(),
            tfBairro.getText().trim(),
            tfCEP.getText().trim(),
            tfCelular.getText().trim()
        );
    }

    public void preencherTela(Aluno a) {
        tfRGM.setText(a.getRgm() != null ? a.getRgm() : "");
        tfNome.setText(a.getNome() != null ? a.getNome() : "");
        tfMae.setText(a.getMae() != null ? a.getMae() : "");
        tfDataNasc.setText(a.getDataNasc() != null ? a.getDataNasc() : "");
        tfCPF.setText(a.getCpf() != null ? a.getCpf() : "");
        tfEmail.setText(a.getEmail() != null ? a.getEmail() : "");
        tfEndereco.setText(a.getEndereco() != null ? a.getEndereco() : "");
        tfMunicipio.setText(a.getMunicipio() != null ? a.getMunicipio() : "");
        if (a.getUf() != null) selecionarCombo(cbUF, a.getUf());
        tfBairro.setText(a.getBairro() != null ? a.getBairro() : "");
        tfCEP.setText(a.getCep() != null ? a.getCep() : "");
        tfCelular.setText(a.getCelular() != null ? a.getCelular() : "");
    }

    private void limparDadosPessoais() {
        tfNome.setText(""); tfMae.setText(""); tfEmail.setText("");
        tfEndereco.setText(""); tfMunicipio.setText(""); tfBairro.setText("");
        try {
            tfRGM.setValue(null); tfDataNasc.setValue(null);
            tfCPF.setValue(null); tfCEP.setValue(null); tfCelular.setValue(null);
        } catch (Exception ignored) {
            tfRGM.setText(""); tfDataNasc.setText(""); tfCPF.setText(""); tfCEP.setText(""); tfCelular.setText("");
        }
        cbUF.setSelectedIndex(0);
    }

    private void limparCurso() {
        tfCursoRGM.setText(""); tfCursoNome.setText("");
        cbCurso.setSelectedIndex(0); cbCampus.setSelectedIndex(0);
        bgPeriodo.clearSelection();
    }

    private void selecionarCombo(JComboBox<String> cb, String val) {
        if (val == null) return;
        for (int i = 0; i < cb.getItemCount(); i++) {
            if (val.equals(cb.getItemAt(i))) { cb.setSelectedIndex(i); return; }
        }
    }
    private JPanel painelBase() {
        JPanel p = new JPanel(null);  
        p.setBackground(BRANCO);
        return p;
    }

    private void addLabel(JPanel p, String txt, int x, int y) {
        JLabel lbl = new JLabel(txt);
        lbl.setBounds(x, y, 130, 28);
        lbl.setFont(new Font("Arial", Font.BOLD, 13));
        p.add(lbl);
    }

    private JTextField txtField(int x, int y, int w, int h) {
        JTextField tf = new JTextField();
        tf.setBounds(x, y, w, h);
        tf.setFont(new Font("Arial", Font.PLAIN, 13));
        tf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180,180,180)),
            new EmptyBorder(2, 6, 2, 6)));
        return tf;
    }

    private JFormattedTextField fmtField(MaskFormatter mask, int x, int y, int w, int h) {
        JFormattedTextField ftf = new JFormattedTextField(mask);
        ftf.setBounds(x, y, w, h);
        ftf.setFont(new Font("Arial", Font.PLAIN, 13));
        ftf.setBorder(BorderFactory.createCompoundBorder(
            new LineBorder(new Color(180,180,180)),
            new EmptyBorder(2, 6, 2, 6)));
        return ftf;
    }


    private JButton botao(String txt, int x, int y, int w, int h, Color bg) {
        JButton btn = new JButton(txt);
        btn.setBounds(x, y, w, h);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(BRANCO);
        btn.setOpaque(true);                  
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);          
        btn.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        return btn;
    }

    private void estilizarCombo(JComboBox<String> cb, int x, int y, int w, int h) {
        cb.setBounds(x, y, w, h);
        cb.setFont(new Font("Arial", Font.PLAIN, 13));
        cb.setBackground(BRANCO);
    }

    private MaskFormatter criarMask(String pattern) {
        try {
            MaskFormatter mf = new MaskFormatter(pattern);
            mf.setPlaceholderCharacter('_');
            return mf;
        } catch (ParseException e) { return null; }
    }

    private void sucesso(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Sucesso", JOptionPane.INFORMATION_MESSAGE);
    }

    private void erro(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Erro", JOptionPane.ERROR_MESSAGE);
    }
}