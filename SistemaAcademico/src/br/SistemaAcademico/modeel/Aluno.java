package br.SistemaAcademico.modeel;

public class Aluno {
    private String rgm;
    private String nome;
    private String mae;
    private String dataNasc;
    private String cpf;
    private String email;
    private String endereco;
    private String municipio;
    private String uf;
    private String bairro;
    private String cep;
    private String celular;

    public Aluno() {}

    public Aluno(String rgm, String nome, String mae, String dataNasc,
                 String cpf, String email, String endereco, String municipio,
                 String uf, String bairro, String cep, String celular) {
        this.rgm       = rgm;
        this.nome      = nome;
        this.mae       = mae;
        this.dataNasc  = dataNasc;
        this.cpf       = cpf;
        this.email     = email;
        this.endereco  = endereco;
        this.municipio = municipio;
        this.uf        = uf;
        this.bairro    = bairro;
        this.cep       = cep;
        this.celular   = celular;
    }

    // ===== GETTERS & SETTERS =====
    public String getRgm()                    { return rgm; }
    public void   setRgm(String rgm)          { this.rgm = rgm; }

    public String getNome()                   { return nome; }
    public void   setNome(String nome)        { this.nome = nome; }

    public String getMae()                    { return mae; }
    public void   setMae(String mae)          { this.mae = mae; }

    public String getDataNasc()               { return dataNasc; }
    public void   setDataNasc(String d)       { this.dataNasc = d; }

    public String getCpf()                    { return cpf; }
    public void   setCpf(String cpf)          { this.cpf = cpf; }

    public String getEmail()                  { return email; }
    public void   setEmail(String email)      { this.email = email; }

    public String getEndereco()               { return endereco; }
    public void   setEndereco(String e)       { this.endereco = e; }

    public String getMunicipio()              { return municipio; }
    public void   setMunicipio(String m)      { this.municipio = m; }

    public String getUf()                     { return uf; }
    public void   setUf(String uf)            { this.uf = uf; }

    public String getBairro()                 { return bairro; }
    public void   setBairro(String b)         { this.bairro = b; }

    public String getCep()                    { return cep; }
    public void   setCep(String cep)          { this.cep = cep; }

    public String getCelular()                { return celular; }
    public void   setCelular(String c)        { this.celular = c; }

    @Override
    public String toString() {
        return "RGM: " + rgm + " | Nome: " + nome;
    }
}