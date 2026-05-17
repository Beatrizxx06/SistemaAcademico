package br.SistemaAcademico.modeel;

public class NotaFalta {

    private int id;
    private String rgm;
    private String disciplina;
    private String semestre;
    private double nota;
    private int faltas;
    @SuppressWarnings("unused")
	private String status;

    public NotaFalta() {
    }

    public NotaFalta(String rgm, String disciplina, String semestre,
                     double nota, int faltas) {

        this.rgm = rgm;
        this.disciplina = disciplina;
        this.semestre = semestre;
        this.nota = nota;
        this.faltas = faltas;

 
        if (faltas > 15) {
            this.status = "Reprovado por Falta";
        } else if (nota >= 6.0) {
            this.status = "Aprovado";
        } else {
            this.status = "Reprovado por Nota";
        }
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRgm() {
        return rgm;
    }

    public void setRgm(String rgm) {
        this.rgm = rgm;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public double getNota() {
        return nota;
    }

    public void setNota(double nota) {
        this.nota = nota;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }


    public void setStatus(String status) {
        this.status = status;
    }

	public Object getStatus() {
		// TODO Auto-generated method stub
		return null;
	}

	public Object getStatus1() {
		// TODO Auto-generated method stub
		return null;
	}
}