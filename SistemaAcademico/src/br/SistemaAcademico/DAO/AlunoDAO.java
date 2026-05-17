package br.SistemaAcademico.DAO;

import br.SistemaAcademico.modeel.Aluno;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlunoDAO {

 
    public void inserir(Aluno a) throws SQLException {
      
        if (existeRgm(a.getRgm())) {
            throw new SQLException("Já existe um aluno cadastrado com o RGM " + a.getRgm() + ".");
        }

        String sql = "INSERT INTO aluno "
                   + "(rgm, nome, mae, data_nasc, cpf, email, endereco, municipio, uf, bairro, cep, celular) "
                   + "VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1,  a.getRgm());
            ps.setString(2,  a.getNome());
            ps.setString(3,  a.getMae());
            ps.setString(4,  a.getDataNasc());
            ps.setString(5,  a.getCpf());
            ps.setString(6,  a.getEmail());
            ps.setString(7,  a.getEndereco());
            ps.setString(8,  a.getMunicipio());
            ps.setString(9,  a.getUf());
            ps.setString(10, a.getBairro());
            ps.setString(11, a.getCep());
            ps.setString(12, a.getCelular());
            ps.executeUpdate();
        }
    }

    public void alterar(Aluno a) throws SQLException {
        String sql = "UPDATE aluno SET nome=?, mae=?, data_nasc=?, cpf=?, email=?, "
                   + "endereco=?, municipio=?, uf=?, bairro=?, cep=?, celular=? "
                   + "WHERE rgm=?";

        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1,  a.getNome());
            ps.setString(2,  a.getMae());
            ps.setString(3,  a.getDataNasc());
            ps.setString(4,  a.getCpf());
            ps.setString(5,  a.getEmail());
            ps.setString(6,  a.getEndereco());
            ps.setString(7,  a.getMunicipio());
            ps.setString(8,  a.getUf());
            ps.setString(9,  a.getBairro());
            ps.setString(10, a.getCep());
            ps.setString(11, a.getCelular());
            ps.setString(12, a.getRgm());
            ps.executeUpdate();
        }
    }

    public void excluir(String rgm) throws SQLException {
        String sql = "DELETE FROM aluno WHERE rgm=?";
        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rgm);
            ps.executeUpdate();
        }
    }


    public Aluno buscarPorRgm(String rgm) throws SQLException {
        String sql = "SELECT * FROM aluno WHERE rgm=?";
        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rgm);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return mapear(rs);
            }
        }
        return null;
    }

    public List<Aluno> buscarPorNome(String nome) throws SQLException {
        String sql = "SELECT * FROM aluno WHERE nome LIKE ?";
        List<Aluno> lista = new ArrayList<>();
        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + nome + "%");
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) lista.add(mapear(rs));
            }
        }
        return lista;
    }

   
    public List<Aluno> listarTodos() throws SQLException {
        String sql = "SELECT * FROM aluno ORDER BY nome";
        List<Aluno> lista = new ArrayList<>();
        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) lista.add(mapear(rs));
        }
        return lista;
    }

    
    public boolean existeRgm(String rgm) throws SQLException {
        String sql = "SELECT 1 FROM aluno WHERE rgm=?";
        try (Connection con = Conexao.obterConexao();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, rgm);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        }
    }

    private Aluno mapear(ResultSet rs) throws SQLException {
        return new Aluno(
            rs.getString("rgm"),
            rs.getString("nome"),
            rs.getString("mae"),
            rs.getString("data_nasc"),
            rs.getString("cpf"),
            rs.getString("email"),
            rs.getString("endereco"),
            rs.getString("municipio"),
            rs.getString("uf"),
            rs.getString("bairro"),
            rs.getString("cep"),
            rs.getString("celular")
        );
    }
}