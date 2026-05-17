package br.SistemaAcademico.DAO;

import br.SistemaAcademico.modeel.NotaFalta;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class NotaFaltaDAO {

    public void inserir(NotaFalta nf) throws SQLException {

        String sql = "INSERT INTO notas_faltas (rgm, disciplina, semestre, nota, faltas) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, nf.getRgm());
            ps.setString(2, nf.getDisciplina());
            ps.setString(3, nf.getSemestre());
            ps.setDouble(4, nf.getNota());
            ps.setInt(5, nf.getFaltas());

            ps.executeUpdate();
        }
    }

    public List<NotaFalta> listarPorRgm(String rgm) throws SQLException {

        List<NotaFalta> lista = new ArrayList<>();

        String sql = "SELECT * FROM notas_faltas WHERE rgm = ? ORDER BY disciplina";

        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, rgm);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    NotaFalta nf = new NotaFalta();

                    nf.setId(rs.getInt("id"));
                    nf.setRgm(rs.getString("rgm"));
                    nf.setDisciplina(rs.getString("disciplina"));
                    nf.setSemestre(rs.getString("semestre"));
                    nf.setNota(rs.getDouble("nota"));
                    nf.setFaltas(rs.getInt("faltas"));

                    lista.add(nf);
                }
            }
        }

        return lista;
    }

    public void excluir(int id) throws SQLException {

        String sql = "DELETE FROM notas_faltas WHERE id = ?";

        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, id);

            ps.executeUpdate();
        }
    }
}