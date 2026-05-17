package br.SistemaAcademico.DAO;

import br.SistemaAcademico.modeel.CursoAluno;

import java.sql.*;

public class CursoAlunoDAO {


    public void salvar(CursoAluno c) throws SQLException {
        if (buscarPorRgm(c.getRgm()) == null) {
            inserir(c);
        } else {
            atualizar(c);
        }
    }

    private void inserir(CursoAluno c) throws SQLException {
        String sql = "INSERT INTO curso_aluno (rgm, curso, campus, periodo) VALUES (?,?,?,?)";
        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getRgm());
            ps.setString(2, c.getCurso());
            ps.setString(3, c.getCampus());
            ps.setString(4, c.getPeriodo());
            ps.executeUpdate();
        }
    }

    private void atualizar(CursoAluno c) throws SQLException {
        String sql = "UPDATE curso_aluno SET curso=?, campus=?, periodo=? WHERE rgm=?";
        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, c.getCurso());
            ps.setString(2, c.getCampus());
            ps.setString(3, c.getPeriodo());
            ps.setString(4, c.getRgm());
            ps.executeUpdate();
        }
    }

 
    public CursoAluno buscarPorRgm(String rgm) throws SQLException {
        String sql = "SELECT * FROM curso_aluno WHERE rgm=?";
        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rgm);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    CursoAluno ca = new CursoAluno();
                    ca.setId(rs.getInt("id"));
                    ca.setRgm(rs.getString("rgm"));
                    ca.setCurso(rs.getString("curso"));
                    ca.setCampus(rs.getString("campus"));
                    ca.setPeriodo(rs.getString("periodo"));
                    return ca;
                }
            }
        }
        return null;
    }
}
